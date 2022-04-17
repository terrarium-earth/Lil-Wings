package com.toadstoolstudios.lilwings.client.entity;


import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.client.item.JarItemRenderer;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class JarEntityRenderer implements BlockEntityRenderer<ButterflyJarBlockEntity> {

    @Override
    public void render(ButterflyJarBlockEntity blockEntity, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getEntityType() != null) {
            MinecraftClient mc = MinecraftClient.getInstance();
            Identifier id = EntityType.getId(blockEntity.getEntityType());
            ButterflyEntity entity = JarItemRenderer.getOrCreateButterfly(id, blockEntity.getEntityType(), blockEntity.getButterflyData());

            if (entity != null) {
                float reverseScale = 1.0f / entity.getButterfly().spawnScale();
                float scale = 0.35f;

                stack.push();
                stack.translate(0.5, 0.125, 0.5);
                stack.scale(reverseScale, reverseScale, reverseScale);
                stack.scale(scale, scale, scale);
                MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0, mc.getTickDelta(), stack, bufferSource, packedLight);
                stack.pop();
            }
        }
    }

    @Override
    public boolean isInRenderDistance(ButterflyJarBlockEntity pBlockEntity, Vec3d pCameraPos) {
        return true;
    }

    @Override
    public boolean rendersOutsideBoundingBox(ButterflyJarBlockEntity pBlockEntity) {
        return true;
    }
}
