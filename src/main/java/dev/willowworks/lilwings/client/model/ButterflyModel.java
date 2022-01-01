package dev.willowworks.lilwings.client.model;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ButterflyModel extends AnimatedGeoModel<ButterflyEntity> {

    private final String butterflyTexture;

    public ButterflyModel(String butterflyTexture) {
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public ResourceLocation getModelLocation(ButterflyEntity object) {
        return new ResourceLocation(LilWings.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity object) {
        return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ButterflyEntity animatable) {
        return new ResourceLocation(LilWings.MODID, "animations/butterfly_idle.animation.json");
    }
}
