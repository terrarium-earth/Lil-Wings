package earth.terrarium.lilwings.client.patron;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class PatreonFluttererRenderer implements IGeoRenderer<PatreonFlutteringButterfly> {
    private MultiBufferSource bufferSource;

    @Override
    public MultiBufferSource getCurrentRTB() {
        return bufferSource;
    }

    @Override
    public void setCurrentRTB(MultiBufferSource bufferSource) {
        this.bufferSource = bufferSource;
    }

    @Override
    public PatreonFlutteringModel getGeoModelProvider() {
        return new PatreonFlutteringModel();
    }

    @Override
    public ResourceLocation getTextureLocation(PatreonFlutteringButterfly animatable) {
        return animatable.getTexture();
    }

    @Override
    public ResourceLocation getTextureResource(PatreonFlutteringButterfly instance) {
        return instance.getTexture();
    }
}
