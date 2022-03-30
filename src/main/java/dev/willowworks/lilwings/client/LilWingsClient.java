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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class LilWingsClient {

    public static void init() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            EntityRenderers.register(butterfly.entityType().get(),
                    renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        ItemBlockRenderTypes.setRenderLayer(LilWingsBlocks.BUTTERFLY_JAR.get(), RenderType.cutout());
        BlockEntityRenderers.register(LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), context -> new JarEntityRenderer());
    }

    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        PlayerRenderer defaultRenderer = event.getSkin("default");
        PlayerRenderer slimRenderer = event.getSkin("slim");
        if (defaultRenderer != null && slimRenderer != null) {
            defaultRenderer.addLayer(new ButterflyElytraLayer<>(defaultRenderer, event.getEntityModels()));
            slimRenderer.addLayer(new ButterflyElytraLayer<>(slimRenderer, event.getEntityModels()));
        }
    }

    public static void particleEvent(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(LilWingsParticles.BROWN_SPORE.get(), BrownParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(LilWingsParticles.AMETHYST_GROW.get(), AmethystGrowProvider::new);
        Minecraft.getInstance().particleEngine.register(LilWingsParticles.GOLDAPPLE_HEARTS.get(), GoldAppleHeartsProvider::new);
    }
}
