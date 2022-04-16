package com.toadstoolstudios.lilwings.entity.effects;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EnderflyCatchEffect implements CatchEffect {

    @Override
    public void onCatch(PlayerEntity player, ButterflyEntity butterfly, int catchAmount) {
        if (!butterfly.world.isClient()) {
            double x = butterfly.getX() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            double y = butterfly.getY() + (double) (butterfly.getRandom().nextInt(64) - 32);
            double z = butterfly.getZ() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            teleport(player, butterfly.world, butterfly, x, y, z);
        }
    }

    //TODO make crossplatform
    @ExpectPlatform
    private static void teleport(PlayerEntity player, World level, ButterflyEntity butterfly, double x, double y, double z) {
        throw new AssertionError();
    }
}
