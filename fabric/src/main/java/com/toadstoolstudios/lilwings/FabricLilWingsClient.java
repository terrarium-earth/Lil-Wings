package com.toadstoolstudios.lilwings;

import net.fabricmc.api.ClientModInitializer;

public class FabricLilWingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LilWingsClient.init();
    }
}
