package com.toadstoolstudios.lilwings.client.entity;

import com.toadstoolstudios.lilwings.api.PatreonInit;
import com.toadstoolstudios.lilwings.client.entity.patron.PatronButterfly;
import com.toadstoolstudios.lilwings.client.entity.patron.PatronButterflyModel;
import com.toadstoolstudios.lilwings.client.entity.patron.PatronButterflyRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PatreonRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final PatronButterflyRenderer renderer = new PatronButterflyRenderer();

    public PatreonRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        if(PatreonInit.isUserPatron()) {
            PatronButterfly butterfly = new PatronButterfly();
            renderer.render(new PatronButterflyModel().getModel(), butterfly, tickDelta, RenderLayer.getCutout(), matrices, vertexConsumers, vertexConsumers.getBuffer(RenderLayer.getCutout()), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);

        }
    }
}
