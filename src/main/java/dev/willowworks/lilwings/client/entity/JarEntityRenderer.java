package dev.willowworks.lilwings.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.client.item.JarItemRenderer;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class JarEntityRenderer implements BlockEntityRenderer<ButterflyJarBlockEntity> {

    @Override
    public void render(ButterflyJarBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getEntityType() != null) {
            Minecraft mc = Minecraft.getInstance();
            ResourceLocation id = blockEntity.getEntityType().getRegistryName();
            ButterflyEntity entity = JarItemRenderer.getOrCreateButterfly(id, blockEntity.getEntityType(), blockEntity.getButterflyData());

            if (entity != null) {
                float reverseScale = 1.0f / entity.getButterfly().spawnScale();
                float scale = 0.35f;

                stack.pushPose();
                stack.translate(0.5, 0.125, 0.5);
                stack.scale(reverseScale, reverseScale, reverseScale);
                stack.scale(scale, scale, scale);
                Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0, mc.getFrameTime(), stack, bufferSource, packedLight);
                stack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRender(ButterflyJarBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(ButterflyJarBlockEntity pBlockEntity) {
        return true;
    }
}
