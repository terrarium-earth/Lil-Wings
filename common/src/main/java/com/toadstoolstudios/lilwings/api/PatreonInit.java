package com.toadstoolstudios.lilwings.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PatreonInit {
    private static boolean isUserPatron;

    @SuppressWarnings("ConstantConditions")
    public static void init() {
        CompletableFuture.runAsync(() -> {
            try(var inputStreamReader = new InputStreamReader(new URL("https://raw.githubusercontent.com/terrarium-earth/Lil-Wings/crossplatform/patrons.json").openStream())) {
                Set<String> patrons = new Gson().fromJson(inputStreamReader, new TypeToken<Set<String>>(){}.getType());
                isUserPatron = patrons.contains(MinecraftClient.getInstance().player.getUuid().toString()) || isInDev();
                System.out.println("Got Patreon data. " + (isUserPatron ? "User is patron" : "User is not patron"));
            } catch (IOException ignored) {
                isUserPatron = isInDev();
                System.out.println("Failed to get Patreon data");
            }
        });
    }

    @ExpectPlatform
    public static boolean isInDev() {
        return true;
    }

    public static boolean isUserPatron() {
        return isUserPatron;
    }
}
