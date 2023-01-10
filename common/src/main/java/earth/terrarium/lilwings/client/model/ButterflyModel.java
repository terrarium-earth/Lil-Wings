package earth.terrarium.lilwings.client.model;


import earth.terrarium.lilwings.LilWings;
import earth.terrarium.lilwings.entity.ButterflyEntity;
import earth.terrarium.lilwings.registry.LilWingsEntities;
import earth.terrarium.lilwings.registry.entity.GraylingType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ButterflyModel extends AnimatedGeoModel<ButterflyEntity> {

    public final String butterflyTexture;

    public ButterflyModel(String butterflyTexture) {
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public ResourceLocation getModelResource(ButterflyEntity object) {
        //return object.getButterfly().equals(LilWingsEntities.ENDER_WING_BUTTERFLY) ? new ResourceLocation(LilWings.MODID, "geo/enderwing.geo.json") : new ResourceLocation(LilWings.MODID, "geo/butterfly.geo.json");
        return new ResourceLocation(LilWings.MODID, "geo/butterfly.geo.json");
    }



    @Override
    public ResourceLocation getTextureResource(ButterflyEntity object) {
        if (object.getType() == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get() && object.getColorType() != null) {
            GraylingType type = object.getColorType();
            return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + type.getTextureColor() + ".png");
        }
        return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ButterflyEntity animatable) {
        return new ResourceLocation(LilWings.MODID, "animations/butterfly_idle.animation.json");
    }
}
