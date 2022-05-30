package com.toadstoolstudios.lilwings.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PatreonManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Set<UUID> patrons;

    public static void init() {
        CompletableFuture.runAsync(() -> {
            try (var inputStreamReader = new InputStreamReader(new URL("https://raw.githubusercontent.com/terrarium-earth/Lil-Wings/crossplatform/patrons.json").openStream())) {
                patrons = new Gson().fromJson(inputStreamReader, new TypeToken<Set<UUID>>(){}.getType());
                LOGGER.info("Got Patreon data.");
            } catch (IOException exception) {
                LOGGER.error("Failed to get Patreon data", exception);
            }
        });
    }

    @ExpectPlatform
    public static boolean isInDev() {
        return true;
    }

    public static boolean isUserPatron(UUID uuid) {
        return isInDev() || patrons.contains(uuid);
    }
}
