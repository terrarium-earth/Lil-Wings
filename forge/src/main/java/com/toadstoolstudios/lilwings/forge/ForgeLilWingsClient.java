package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWingsClient;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.patron.PatreonLayerRenderer;
import com.toadstoolstudios.lilwings.forge.platform.ForgeClientHelper;
import com.toadstoolstudios.lilwings.platform.services.IClientHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.particle.DefaultParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;
import java.util.function.Supplier;

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
        PlayerEntityRenderer defaultRenderer = event.getSkin("default");
        PlayerEntityRenderer slimRenderer = event.getSkin("slim");

        if (defaultRenderer != null && slimRenderer != null) {
            defaultRenderer.addFeature(new ButterflyElytraLayer<>(defaultRenderer, event.getEntityModels()));
            slimRenderer.addFeature(new ButterflyElytraLayer<>(slimRenderer, event.getEntityModels()));
            defaultRenderer.addFeature(new PatreonLayerRenderer(defaultRenderer, event.getEntityModels()));
            slimRenderer.addFeature(new PatreonLayerRenderer(slimRenderer, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void addLayerDefinitons(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PatreonButterflyModel.LAYER, PatreonButterflyModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void particleEvent(ParticleFactoryRegisterEvent event) {
        LilWingsClient.initParticleFactories();
    }
}
