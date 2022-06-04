package com.toadstoolstudios.lilwings.client.model;


import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.GraylingType;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ButterflyModel extends AnimatedGeoModel<ButterflyEntity> {

    private final String butterflyTexture;

    public ButterflyModel(String butterflyTexture) {
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public Identifier getModelResource(ButterflyEntity object) {
        return new Identifier(LilWings.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public Identifier getTextureResource(ButterflyEntity object) {
        if (object.getType() == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get() && object.getColorType() != null) {
            GraylingType type = object.getColorType();
            return new Identifier(LilWings.MODID, "textures/entity/" + butterflyTexture + type.getTextureColor() + ".png");
        }

        return new Identifier(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }

    @Override
    public Identifier getAnimationResource(ButterflyEntity animatable) {
        return new Identifier(LilWings.MODID, "animations/butterfly_idle.animation.json");
    }
}
