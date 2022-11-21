package com.toadstoolstudios.lilwings.client.patron;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class PatreonFluttererRenderer implements IGeoRenderer<PatreonFlutteringButterfly> {
    VertexConsumerProvider vertexConsumerProvider;

    @Override
    public void setCurrentRTB(VertexConsumerProvider rtb) {
        this.vertexConsumerProvider = rtb;
    }

    @Override
    public VertexConsumerProvider getCurrentRTB() {
        return vertexConsumerProvider;
    }

    @Override
    public PatreonFlutteringModel getGeoModelProvider() {
        return null;
    }

    @Override
    public Identifier getTextureLocation(PatreonFlutteringButterfly instance) {
        return instance.getTexture();
    }
}
