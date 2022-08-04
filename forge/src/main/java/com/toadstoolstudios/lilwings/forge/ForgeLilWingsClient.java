package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWingsClient;
import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.patron.PatreonLayerRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "lilwings", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeLilWingsClient {

    public ForgeLilWingsClient() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        LilWingsClient.init();
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        PlayerRenderer defaultRenderer = event.getSkin("default");
        PlayerRenderer slimRenderer = event.getSkin("slim");

        if (defaultRenderer != null && slimRenderer != null) {
            defaultRenderer.addLayer(new ButterflyElytraLayer<>(defaultRenderer, event.getEntityModels()));
            slimRenderer.addLayer(new ButterflyElytraLayer<>(slimRenderer, event.getEntityModels()));
            defaultRenderer.addLayer(new PatreonLayerRenderer(defaultRenderer, event.getEntityModels()));
            slimRenderer.addLayer(new PatreonLayerRenderer(slimRenderer, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void addLayerDefinitons(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PatreonButterflyModel.LAYER, PatreonButterflyModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void particleEvent(RegisterParticleProvidersEvent event) {
        LilWingsClient.initParticleFactories();
    }
}
