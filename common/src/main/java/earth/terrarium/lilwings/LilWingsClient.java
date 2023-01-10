package earth.terrarium.lilwings;

import earth.terrarium.lilwings.client.entity.ButterflyRenderer;
import earth.terrarium.lilwings.client.entity.JarEntityRenderer;
import earth.terrarium.lilwings.client.particle.AmethystGrowProvider;
import earth.terrarium.lilwings.client.particle.BrownParticleProvider;
import earth.terrarium.lilwings.client.particle.GoldAppleHeartsProvider;
import earth.terrarium.lilwings.platform.ClientServices;
import earth.terrarium.lilwings.registry.LilWingsBlocks;
import earth.terrarium.lilwings.registry.LilWingsParticles;
import earth.terrarium.lilwings.registry.entity.Butterfly;
import net.minecraft.client.renderer.RenderType;

public class LilWingsClient {

    public static void init() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            ClientServices.CLIENT.registerEntityRenderers(butterfly.entityType(), renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        ClientServices.CLIENT.renderBlockRenderers(LilWingsBlocks.BUTTERFLY_JAR, RenderType.cutout());
        ClientServices.CLIENT.registerBlockEntityRenderer(LilWingsBlocks.BUTTERFLY_JAR_ENTITY, (ctx) -> new JarEntityRenderer());
    }

    public static void initParticleFactories() {
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.BROWN_SPORE, BrownParticleProvider::new);
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.AMETHYST_GROW, AmethystGrowProvider::new);
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.GOLDAPPLE_HEARTS, GoldAppleHeartsProvider::new);
    }
}
