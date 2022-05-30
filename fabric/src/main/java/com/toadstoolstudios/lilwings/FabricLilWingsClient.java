package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.entity.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.entity.patron.PatreonLayerRenderer;
import com.toadstoolstudios.lilwings.platform.FabricRegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;


import static com.toadstoolstudios.lilwings.LilWings.MODID;

public class FabricLilWingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlas, registry) ->
                FabricRegistryHelper.TEXTURES.stream()
                        .map(id -> new Identifier(MODID, "particle/" + id))
                        .forEachOrdered(registry::register)
        );
        LilWingsClient.init();
        LilWingsClient.initParticleFactories();
        EntityModelLayerRegistry.registerModelLayer(PatreonButterflyModel.LAYER, PatreonButterflyModel::getTexturedModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer.getModel() instanceof PlayerEntityModel || entityRenderer.getModel() instanceof BipedEntityModel || entityRenderer.getModel() instanceof ArmorStandEntityModel) {
                registrationHelper.register(new ButterflyElytraLayer<>(entityRenderer, MinecraftClient.getInstance().getEntityModelLoader()));
            }

            if (entityRenderer instanceof PlayerEntityRenderer playerRenderer) {
                registrationHelper.register(new PatreonLayerRenderer(playerRenderer, context));
            }
        });
    }
}
