package dev.willowworks.lilwings.client.model;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.LilWingsEntities;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ButterflyModel extends AnimatedGeoModel<ButterflyEntity> {

    private final String butterflyTexture;

    public ButterflyModel(String butterflyTexture) {
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public ResourceLocation getModelLocation(ButterflyEntity object) {
        return object.getButterfly().equals(LilWingsEntities.ENDER_WING_BUTTERFLY) ? new ResourceLocation(LilWings.MODID, "geo/enderwing.geo.json") : new ResourceLocation(LilWings.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity object) {
        if (object.getType() == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get() && object.getColorType() != null) {
            GraylingType type = object.getColorType();
            return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + type.getTextureColor() + ".png");
        }

        return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ButterflyEntity animatable) {
        return new ResourceLocation(LilWings.MODID, "animations/butterfly_idle.animation.json");
    }
}
