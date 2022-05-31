package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWingsClient;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.patron.PatreonLayerRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class ForgeLilWingsClient {
    public static void init() {
        LilWingsClient.init();
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
