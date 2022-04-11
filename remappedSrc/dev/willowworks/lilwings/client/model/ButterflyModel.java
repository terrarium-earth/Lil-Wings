package dev.willowworks.lilwings.client.model;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.LilWingsEntities;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ButterflyModel extends AnimatedGeoModel<ButterflyEntity> {

    private final String butterflyTexture;

    public ButterflyModel(String butterflyTexture) {
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public Identifier getModelLocation(ButterflyEntity object) {
        return object.getButterfly().equals(LilWingsEntities.ENDER_WING_BUTTERFLY) ? new Identifier(LilWings.MODID, "geo/enderwing.geo.json") : new Identifier(LilWings.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ButterflyEntity object) {
        if (object.getType() == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get() && object.getColorType() != null) {
            GraylingType type = object.getColorType();
            return new Identifier(LilWings.MODID, "textures/entity/" + butterflyTexture + type.getTextureColor() + ".png");
        }

        return new Identifier(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(ButterflyEntity animatable) {
        return new Identifier(LilWings.MODID, "animations/butterfly_idle.animation.json");
    }
}
