package com.toadstoolstudios.lilwings.entity.effects;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface CatchEffect {

    void onCatch(PlayerEntity player, ButterflyEntity butterfly, int catchAmount);
}
