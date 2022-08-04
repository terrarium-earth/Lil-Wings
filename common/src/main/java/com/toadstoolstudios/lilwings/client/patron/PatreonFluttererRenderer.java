package com.toadstoolstudios.lilwings.client.patron;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class PatreonFluttererRenderer implements IGeoRenderer<PatreonFlutteringButterfly> {

    @Override
    public PatreonFlutteringModel getGeoModelProvider() {
        return null;
    }

    @Override
    public ResourceLocation getTextureResource(PatreonFlutteringButterfly instance) {
        return instance.getTexture();
    }
}
