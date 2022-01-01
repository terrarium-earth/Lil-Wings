package dev.willowworks.lilwings.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.client.item.JarItemRenderer;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class JarEntityRenderer implements BlockEntityRenderer<ButterflyJarBlockEntity> {

    @Override
    public void render(ButterflyJarBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getEntityType() != null) {
            Minecraft mc = Minecraft.getInstance();
            ResourceLocation id = blockEntity.getEntityType().getRegistryName();
            ButterflyEntity entity = JarItemRenderer.FAKES.computeIfAbsent(id, resourceLocation -> blockEntity.getEntityType().create(mc.level));
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
}
