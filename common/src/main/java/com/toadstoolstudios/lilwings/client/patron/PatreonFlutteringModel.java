package com.toadstoolstudios.lilwings.client.patron;

import com.toadstoolstudios.lilwings.LilWings;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PatreonFlutteringModel extends AnimatedGeoModel<PatreonFlutteringButterfly> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(new Identifier(LilWings.MODID, "higher_patreon_butterfly"), "main");

    public GeoModel getModel(PatreonFlutteringButterfly butterfly) {
        return this.getModel(this.getModelLocation(butterfly));
    }

    @Override
    public Identifier getModelLocation(PatreonFlutteringButterfly object) {
        return new Identifier(LilWings.MODID, "geo/patreon_butterfly.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PatreonFlutteringButterfly object) {
        return object.getTexture();
    }

    @Override
    public Identifier getAnimationFileLocation(PatreonFlutteringButterfly animatable) {
        return new Identifier(LilWings.MODID, "animations/patreon_butterfly.animation.json");
    }

}
