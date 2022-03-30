package com.toadstoolstudios.lilwings.client.entity;

import com.toadstoolstudios.lilwings.item.ButterflyElytra;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ButterflyElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private final ElytraEntityModel<T> elytraModel;

    public ButterflyElytraLayer(FeatureRendererContext<T, M> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.elytraModel = new ElytraEntityModel<PlayerEntity>(entityModelSet.bakeLayer(ModelLayers.ELYTRA));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemstack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemstack.getItem() instanceof ButterflyElytra elytraItem) {
            Identifier resourcelocation = elytraItem.getTexture();

            if (entity instanceof AbstractClientPlayerEntity clientPlayer) {
                if (clientPlayer.canRenderElytraTexture() && clientPlayer.getElytraTexture() != null) {
                    resourcelocation = clientPlayer.getElytraTexture();
                } else if (clientPlayer.canRenderCapeTexture() && clientPlayer.getCapeTexture() != null && clientPlayer.isPartVisible(PlayerModelPart.CAPE)) {
                    resourcelocation = clientPlayer.getCapeTexture();
                }
            }

            matrices.push();
            matrices.translate(0, 0, 0.125);
            this.getContextModel().copyStateTo(this.elytraModel);
            this.elytraModel.setAngles(entity, limbAngle, limbDistance, tickDelta, headYaw, headPitch);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(resourcelocation), false, itemstack.hasGlint());
            this.elytraModel.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            matrices.pop();
        }
    }
}
