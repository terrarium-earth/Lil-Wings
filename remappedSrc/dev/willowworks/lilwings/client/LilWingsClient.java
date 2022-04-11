package dev.willowworks.lilwings.client;

import dev.willowworks.lilwings.client.entity.ButterflyElytraLayer;
import dev.willowworks.lilwings.client.entity.ButterflyRenderer;
import dev.willowworks.lilwings.client.entity.JarEntityRenderer;
import dev.willowworks.lilwings.client.particle.AmethystGrowProvider;
import dev.willowworks.lilwings.client.particle.BrownParticleProvider;
import dev.willowworks.lilwings.client.particle.GoldAppleHeartsProvider;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.LilWingsParticles;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class LilWingsClient {

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
