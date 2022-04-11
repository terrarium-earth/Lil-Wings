package dev.willowworks.lilwings.client.entity;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.client.item.JarItemRenderer;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class JarEntityRenderer implements BlockEntityRenderer<ButterflyJarBlockEntity> {

    @Override
    public void render(ButterflyJarBlockEntity blockEntity, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getEntityType() != null) {
            MinecraftClient mc = MinecraftClient.getInstance();
            Identifier id = blockEntity.getEntityType().getRegistryName();
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
    public boolean shouldRender(ButterflyJarBlockEntity pBlockEntity, Vec3d pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(ButterflyJarBlockEntity pBlockEntity) {
        return true;
    }
}
