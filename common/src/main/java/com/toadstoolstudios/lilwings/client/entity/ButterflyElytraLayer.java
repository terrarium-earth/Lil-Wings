package com.toadstoolstudios.lilwings.client.entity;

import com.toadstoolstudios.lilwings.item.IButterflyElytra;
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
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ButterflyElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private final ElytraEntityModel<T> elytraModel;

    public ButterflyElytraLayer(FeatureRendererContext<T, M> renderLayerParent, EntityModelLoader entityModelSet) {
        super(renderLayerParent);
        this.elytraModel = new ElytraEntityModel<>(entityModelSet.getModelPart(EntityModelLayers.ELYTRA));
    }

    @Override
    public void render(MatrixStack pMatrixStack, VertexConsumerProvider pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemstack.getItem() instanceof IButterflyElytra elytraItem) {
            Identifier resourcelocation = elytraItem.getTexture();

            pMatrixStack.push();
            pMatrixStack.translate(0, 0, 0.125);
            this.getContextModel().copyStateTo(this.elytraModel);
            this.elytraModel.setAngles(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorGlintConsumer(pBuffer, RenderLayer.getArmorCutoutNoCull(resourcelocation), false, itemstack.hasGlint());
            this.elytraModel.render(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            pMatrixStack.pop();
        }
    }
}
