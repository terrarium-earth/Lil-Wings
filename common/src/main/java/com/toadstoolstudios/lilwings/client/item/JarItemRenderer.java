package com.toadstoolstudios.lilwings.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class JarItemRenderer extends BlockEntityWithoutLevelRenderer {

    public static final JarItemRenderer RENDERER = new JarItemRenderer();
    public static final Map<ResourceLocation, ButterflyEntity> FAKES = new HashMap<>();

    public JarItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack stack, MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        BlockState blockState = LilWingsBlocks.BUTTERFLY_JAR.get().defaultBlockState();
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState);

        stack.pushPose();
        stack.translate(0.5, 0.5, 0.5);
        if (transformType.firstPerson())
            stack.translate(0, 0.20, 0);

        if (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
            stack.scale(1.25f, 1.25f, 1.25f);
        } else if (!transformType.firstPerson() && transformType != ItemTransforms.TransformType.GUI) {
            stack.scale(1.75f, 1.75f, 1.75f);
        }

        mc.getItemRenderer().render(itemStack, transformType, transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, stack, buffer, pPackedLight, pPackedOverlay, model);

        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("butterflyId")) {
            ResourceLocation id = new ResourceLocation(tag.getString("butterflyId"));
            if (!Butterfly.BUTTERFLIES.containsKey(id)) {
                stack.popPose();
                return;
            }

            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(id);
            ButterflyEntity entity = getOrCreateButterfly(id, (EntityType<? extends ButterflyEntity>) type, tag.getCompound("butterfly"));
            if (entity == null) {
                stack.popPose();
                return;
            }

            float reverseScale = 1.0f / entity.getButterfly().spawnScale();
            float scale = 0.10f;
            stack.pushPose();

            switch (transformType) {
                case GROUND -> stack.translate(0, 0.08, 0);
                case FIXED, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> stack.translate(0, -0.15, 0);
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                    stack.mulPose(Vector3f.XP.rotationDegrees(90));
                    stack.translate(0, -0.12, -0.15);
                }
            }

            stack.scale(reverseScale, reverseScale, reverseScale);
            stack.scale(scale, scale, scale);
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0, mc.getFrameTime(), stack, buffer, pPackedLight);
            stack.popPose();
        }

        stack.popPose();
    }

    public static ButterflyEntity getOrCreateButterfly(ResourceLocation id, EntityType<? extends ButterflyEntity> entityType, CompoundTag butterflyData) {
        ButterflyEntity entity = JarItemRenderer.FAKES.computeIfAbsent(id, resourceLocation -> JarItemRenderer.createFakeEntity(entityType, butterflyData));
        if (entity != null && butterflyData != null && butterflyData.contains("colorType")) {
            GraylingType color = GraylingType.valueOf(butterflyData.getString("colorType"));

            if (color != entity.getColorType(false)) {
                entity.setColorType(color);
            }
        }

        return entity;
    }

    public static ButterflyEntity createFakeEntity(EntityType<? extends ButterflyEntity> entityType, CompoundTag butterflyData) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return null;
        return entityType.create(mc.level);
    }
}
