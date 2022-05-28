package com.toadstoolstudios.lilwings.client.entity.patron;

import com.toadstoolstudios.lilwings.LilWings;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PatronButterflyModel extends AnimatedGeoModel<PatronButterfly> {
    @Override
    public Identifier getModelLocation(PatronButterfly object) {
        return new Identifier(LilWings.MODID, "geo/patron_butterfly.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PatronButterfly object) {
        return new Identifier(LilWings.MODID, "textures/patreon/patron_butterfly");
    }

    @Override
    public Identifier getAnimationFileLocation(PatronButterfly animatable) {
        return new Identifier(LilWings.MODID, "animation/patron_butterfly.animation.json");
    }
}
