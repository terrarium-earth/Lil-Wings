package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.platform.FabricRegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
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
    }
}
