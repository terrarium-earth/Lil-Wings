package dev.willowworks.lilwings.entity.effects;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface CatchEffect {

    void onCatch(PlayerEntity player, ButterflyEntity butterfly, int catchAmount);
}
