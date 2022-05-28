package com.toadstoolstudios.lilwings.client.entity.patron;

import com.toadstoolstudios.lilwings.client.entity.patron.PatronButterflyModel;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class PatronButterflyRenderer implements IGeoRenderer<PatronButterflyModel> {

    @Override
    public PatronButterflyModel getGeoModelProvider() {
        return null;
    }

    @Override
    public Identifier getTextureLocation(PatronButterflyModel instance) {
        return instance.getTextureLocation(null);
    }
}
