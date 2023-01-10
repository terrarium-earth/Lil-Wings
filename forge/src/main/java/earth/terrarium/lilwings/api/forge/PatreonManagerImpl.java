package earth.terrarium.lilwings.api.forge;

import net.minecraftforge.fml.loading.FMLLoader;

public class PatreonManagerImpl {
    public static boolean isInDev() {
        return !FMLLoader.isProduction();
    }
}
