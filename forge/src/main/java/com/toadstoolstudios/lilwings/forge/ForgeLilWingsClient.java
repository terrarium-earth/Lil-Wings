package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWingsClient;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.patron.PatreonLayerRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = "lilwings", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeLilWingsClient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        LilWingsClient.init();
        bus.addListener(ForgeLilWingsClient::addLayers);
        bus.addListener(ForgeLilWingsClient::addLayerDefinitons);
        bus.addListener(ForgeLilWingsClient::particleEvent);
    }

    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        PlayerEntityRenderer defaultRenderer = event.getSkin("default");
        PlayerEntityRenderer slimRenderer = event.getSkin("slim");

        if (defaultRenderer != null && slimRenderer != null) {
            defaultRenderer.addFeature(new ButterflyElytraLayer<>(defaultRenderer, event.getEntityModels()));
            slimRenderer.addFeature(new ButterflyElytraLayer<>(slimRenderer, event.getEntityModels()));
            defaultRenderer.addFeature(new PatreonLayerRenderer(defaultRenderer, event.getEntityModels()));
            slimRenderer.addFeature(new PatreonLayerRenderer(slimRenderer, event.getEntityModels()));
        }
    }

    public static void addLayerDefinitons(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PatreonButterflyModel.LAYER, PatreonButterflyModel::getTexturedModelData);
    }


    public static void particleEvent(ParticleFactoryRegisterEvent event) {
        LilWingsClient.initParticleFactories();
    }
}
