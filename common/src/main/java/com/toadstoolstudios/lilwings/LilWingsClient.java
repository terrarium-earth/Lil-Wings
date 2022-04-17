package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.entity.ButterflyRenderer;
import com.toadstoolstudios.lilwings.client.entity.JarEntityRenderer;
import com.toadstoolstudios.lilwings.client.particle.AmethystGrowProvider;
import com.toadstoolstudios.lilwings.client.particle.BrownParticleProvider;
import com.toadstoolstudios.lilwings.client.particle.GoldAppleHeartsProvider;
import com.toadstoolstudios.lilwings.platform.ClientServices;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

public class LilWingsClient {

    public static void init() {
        /*
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            ClientServices.CLIENT.registerEntityRenderers(butterfly.entityType(), renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        RenderLayers.se(LilWingsBlocks.BUTTERFLY_JAR.get(), RenderLayer.getCutout());
        BlockEntityRendererFactories.register(LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), context -> new JarEntityRenderer());
         */

    }
}
