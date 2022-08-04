package com.toadstoolstudios.lilwings.client.entity;


import com.mojang.blaze3d.vertex.PoseStack;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class JarEntityRenderer implements BlockEntityRenderer<ButterflyJarBlockEntity> {

    @Override
    public void render(ButterflyJarBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getEntityType() != null) {
            Minecraft mc = Minecraft.getInstance();
            ButterflyEntity entity = blockEntity.getOrCreateEntity(blockEntity.getLevel(), blockEntity.getEntityType(), blockEntity.getButterflyData());
            float reverseScale = 1.0f / entity.getButterfly().spawnScale();
            float scale = 0.35f;
            stack.pushPose();
            stack.translate(0.5, 0.125, 0.5);
            stack.scale(reverseScale, reverseScale, reverseScale);
            stack.scale(scale, scale, scale);
            Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0, mc.getFrameTime(), stack, bufferSource, packedLight);
            stack.popPose();
            // Renders twice on fabric because Fabric doesn't fix https://bugs.mojang.com/browse/MC-112730 while Forge does
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
