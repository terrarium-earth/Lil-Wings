package com.toadstoolstudios.lilwings.platform;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class CommonServices {

    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        //LilWings.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}