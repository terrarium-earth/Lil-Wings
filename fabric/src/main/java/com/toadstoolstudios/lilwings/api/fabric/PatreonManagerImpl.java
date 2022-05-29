package com.toadstoolstudios.lilwings.api.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PatreonManagerImpl {
    public static boolean isInDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
