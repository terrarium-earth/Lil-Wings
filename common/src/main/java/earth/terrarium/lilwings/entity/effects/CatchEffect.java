package earth.terrarium.lilwings.entity.effects;

import earth.terrarium.lilwings.entity.ButterflyEntity;
import net.minecraft.world.entity.player.Player;

public interface CatchEffect {

    void onCatch(Player player, ButterflyEntity butterfly, int catchAmount);
}
