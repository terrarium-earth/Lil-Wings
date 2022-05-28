package com.toadstoolstudios.lilwings.api.forge;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.loading.FMLLoader;

public class PatreonInitImpl {
    public static boolean isInDev() {
        return !FMLLoader.isProduction();
    }
}
