package earth.terrarium.lilwings.client.patron;

import earth.terrarium.lilwings.LilWings;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PatreonFlutteringModel extends AnimatedGeoModel<PatreonFlutteringButterfly> {

    public GeoModel getModel(PatreonFlutteringButterfly butterfly) {
        return this.getModel(this.getModelResource(butterfly));
    }

    @Override
    public ResourceLocation getModelResource(PatreonFlutteringButterfly object) {
        return new ResourceLocation(LilWings.MODID, "geo/patreon_butterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PatreonFlutteringButterfly object) {
        return object.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(PatreonFlutteringButterfly animatable) {
        return new ResourceLocation(LilWings.MODID, "geo/patreon_butterfly.geo.json");
    }
}
