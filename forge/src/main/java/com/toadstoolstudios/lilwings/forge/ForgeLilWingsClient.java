package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.entity.ButterflyRenderer;
import com.toadstoolstudios.lilwings.client.entity.JarEntityRenderer;
import com.toadstoolstudios.lilwings.client.particle.AmethystGrowProvider;
import com.toadstoolstudios.lilwings.client.particle.BrownParticleProvider;
import com.toadstoolstudios.lilwings.client.particle.GoldAppleHeartsProvider;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class ForgeLilWingsClient {
    public static void init() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            EntityRenderers.register(butterfly.entityType().get(),
                    renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        RenderLayers.setRenderLayer(LilWingsBlocks.BUTTERFLY_JAR.get(), RenderLayer.getCutout());
        BlockEntityRendererFactories.register(LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), context -> new JarEntityRenderer());
    }

    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        PlayerEntityRenderer defaultRenderer = event.getSkin("default");
        PlayerEntityRenderer slimRenderer = event.getSkin("slim");
        if (defaultRenderer != null && slimRenderer != null) {
            defaultRenderer.addFeature(new ButterflyElytraLayer<>(defaultRenderer, event.getEntityModels()));
            slimRenderer.addFeature(new ButterflyElytraLayer<>(slimRenderer, event.getEntityModels()));
        }
    }

    public static void particleEvent(ParticleFactoryRegisterEvent event) {
        MinecraftClient.getInstance().particleManager.registerFactory(LilWingsParticles.BROWN_SPORE.get(), BrownParticleProvider::new);
        MinecraftClient.getInstance().particleManager.registerFactory(LilWingsParticles.AMETHYST_GROW.get(), AmethystGrowProvider::new);
        MinecraftClient.getInstance().particleManager.registerFactory(LilWingsParticles.GOLDAPPLE_HEARTS.get(), GoldAppleHeartsProvider::new);
    }
}
