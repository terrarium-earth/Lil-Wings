package earth.terrarium.lilwings.entity.effects;

import earth.terrarium.lilwings.entity.ButterflyEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EnderflyCatchEffect implements CatchEffect {

    @Override
    public void onCatch(Player player, ButterflyEntity butterfly, int catchAmount) {
        if (!butterfly.level.isClientSide()) {
            double x = butterfly.getX() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            double y = butterfly.getY() + (double) (butterfly.getRandom().nextInt(64) - 32);
            double z = butterfly.getZ() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            teleport(player, butterfly.getLevel(), butterfly, x, y, z);
        }
    }

    //TODO make crossplatform
    @ExpectPlatform
    private static void teleport(Player player, Level level, ButterflyEntity butterfly, double x, double y, double z) {
        throw new AssertionError();
    }
}
