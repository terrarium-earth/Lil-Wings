package com.toadstoolstudios.lilwings.fabric;

import com.toadstoolstudios.lilwings.LilWingsClient;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.client.patron.PatreonButterflyModel;
import com.toadstoolstudios.lilwings.client.patron.PatreonLayerRenderer;
import com.toadstoolstudios.lilwings.platform.fabric.FabricRegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import static com.toadstoolstudios.lilwings.LilWings.MODID;

public class FabricLilWingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) ->
                FabricRegistryHelper.TEXTURES.stream()
                        .map(id -> new ResourceLocation(MODID, "particle/" + id))
                        .forEachOrdered(registry::register)
        );
        LilWingsClient.init();
        LilWingsClient.initParticleFactories();
        EntityModelLayerRegistry.registerModelLayer(PatreonButterflyModel.LAYER, PatreonButterflyModel::getTexturedModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer.getModel() instanceof PlayerModel || entityRenderer.getModel() instanceof HumanoidModel || entityRenderer.getModel() instanceof ArmorStandModel) {
                registrationHelper.register(new ButterflyElytraLayer<>(entityRenderer, Minecraft.getInstance().getEntityModels()));
            }

            if (entityRenderer instanceof PlayerRenderer playerRenderer) {
                registrationHelper.register(new PatreonLayerRenderer(playerRenderer, context));
            }
        });
        PatreonManager.init();
    }
}
