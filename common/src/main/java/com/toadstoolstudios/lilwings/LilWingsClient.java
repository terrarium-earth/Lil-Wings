package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.client.entity.ButterflyRenderer;
import com.toadstoolstudios.lilwings.client.entity.JarEntityRenderer;
import com.toadstoolstudios.lilwings.client.particle.AmethystGrowProvider;
import com.toadstoolstudios.lilwings.client.particle.BrownParticleProvider;
import com.toadstoolstudios.lilwings.client.particle.GoldAppleHeartsProvider;
import com.toadstoolstudios.lilwings.platform.ClientServices;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.client.render.RenderLayer;

public class LilWingsClient {

    public static void init() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            ClientServices.CLIENT.registerEntityRenderers(butterfly.entityType(), renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        ClientServices.CLIENT.renderBlockRenderers(LilWingsBlocks.BUTTERFLY_JAR, RenderLayer.getCutout());
        ClientServices.CLIENT.registerBlockEntityRenderer(LilWingsBlocks.BUTTERFLY_JAR_ENTITY, (ctx) -> new JarEntityRenderer());
    }

    public static void initParticleFactories() {
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.BROWN_SPORE, BrownParticleProvider::new);
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.AMETHYST_GROW, AmethystGrowProvider::new);
        ClientServices.CLIENT.registerParticleFactory(LilWingsParticles.GOLDAPPLE_HEARTS, GoldAppleHeartsProvider::new);
    }
}
