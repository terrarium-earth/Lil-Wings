package earth.terrarium.lilwings.api;

import com.google.gson.Gson;
import earth.terrarium.lilwings.LilWings;
import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;

public class PatreonManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static PatreonData patrons;

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static final String URL = "https://raw.githubusercontent.com/terrarium-earth/Patreon-Info/main/lilwings.json";

    public static void init() {
        LOGGER.info("Attempting Patreon fetching");
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(URL))
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .header("User-Agent", "Minecraft Mod (" + LilWings.MODID  + ")")
                    .build();

            HttpResponse<String> send = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            patrons = new Gson().fromJson(send.body(), PatreonData.class);
            LOGGER.info("Got Patreon data.");
        } catch (Exception exception) {
            LOGGER.error("Failed to get Patreon data", exception);
        }
    }

    @ExpectPlatform
    public static boolean isInDev() {
        return true;
    }

    public static boolean isUserPatron(UUID uuid) {
        return patrons != null && patrons.getPatrons().contains(uuid);
    }

    @Nullable
    public static PatreonData.ButterflyType getButterflyType(UUID uuid) {
        if(patrons != null) return patrons.getLeveledPatrons().get(uuid);
        return null;
    }
}
