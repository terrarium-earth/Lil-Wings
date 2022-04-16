package com.toadstoolstudios.lilwings.client.item;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import com.toadstoolstudios.lilwings.registry.entity.GraylingType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.HashMap;
import java.util.Map;

public class JarItemRenderer extends BuiltinModelItemRenderer {

    public static final JarItemRenderer RENDERER = new JarItemRenderer();
    public static final Map<Identifier, ButterflyEntity> FAKES = new HashMap<>();

    public JarItemRenderer() {
        super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
    }

    @Override
    public void render(ItemStack itemStack, ModelTransformation.Mode transformType, MatrixStack stack, VertexConsumerProvider buffer, int pPackedLight, int pPackedOverlay) {
        MinecraftClient mc = MinecraftClient.getInstance();
        BlockState blockState = LilWingsBlocks.BUTTERFLY_JAR.get().getDefaultState();
        BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(blockState);

        stack.push();
        stack.translate(0.5, 0.5, 0.5);
        if (transformType.isFirstPerson())
            stack.translate(0, 0.20, 0);

        if (transformType == ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND || transformType == ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND) {
            stack.scale(1.25f, 1.25f, 1.25f);
        } else if (!transformType.isFirstPerson() && transformType != ModelTransformation.Mode.GUI) {
            stack.scale(1.75f, 1.75f, 1.75f);
        }

        mc.getItemRenderer().renderItem(itemStack, transformType, transformType == ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, stack, buffer, pPackedLight, pPackedOverlay, model);

        NbtCompound tag = itemStack.getNbt();
        if (tag != null && tag.contains("butterflyId")) {
            Identifier id = new Identifier(tag.getString("butterflyId"));
            if (!Butterfly.BUTTERFLIES.containsKey(id)) {
                stack.pop();
                return;
            }

            EntityType<?> type = EntityType.get(id.toString()).get();
            ButterflyEntity entity = getOrCreateButterfly(id, (EntityType<? extends ButterflyEntity>) type, tag.getCompound("butterfly"));
            if (entity == null) {
                stack.pop();
                return;
            }

            float reverseScale = 1.0f / entity.getButterfly().spawnScale();
            float scale = 0.10f;
            stack.push();

            switch (transformType) {
                case GROUND -> stack.translate(0, 0.08, 0);
                case FIXED, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> stack.translate(0, -0.15, 0);
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                    stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                    stack.translate(0, -0.12, -0.15);
                }
            }

            stack.scale(reverseScale, reverseScale, reverseScale);
            stack.scale(scale, scale, scale);
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0, mc.getTickDelta(), stack, buffer, pPackedLight);
            stack.pop();
        }

        stack.pop();
    }

    public static ButterflyEntity getOrCreateButterfly(Identifier id, EntityType<? extends ButterflyEntity> entityType, NbtCompound butterflyData) {
        ButterflyEntity entity = JarItemRenderer.FAKES.computeIfAbsent(id, resourceLocation -> JarItemRenderer.createFakeEntity(entityType, butterflyData));
        if (entity != null && butterflyData != null && butterflyData.contains("colorType")) {
            GraylingType color = GraylingType.valueOf(butterflyData.getString("colorType"));

            if (color != entity.getColorType(false)) {
                entity.setColorType(color);
            }
        }

        return entity;
    }

    public static ButterflyEntity createFakeEntity(EntityType<? extends ButterflyEntity> entityType, NbtCompound butterflyData) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null) return null;
        return entityType.create(mc.world);
    }
}
