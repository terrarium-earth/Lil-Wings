package com.toadstoolstudios.lilwings.entity.effects.fabric;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EnderflyCatchEffectImpl {
    public static void teleport(PlayerEntity player, World level, ButterflyEntity butterfly, double x, double y, double z) {
        BlockPos.Mutable blockPos = new BlockPos.Mutable(x, y, z);

        while (blockPos.getY() > level.getBottomY() && !level.getBlockState(blockPos).getMaterial().blocksMovement()) {
            blockPos.move(Direction.DOWN);
        }

        BlockState blockstate = level.getBlockState(blockPos);
        boolean flag = blockstate.getMaterial().blocksMovement();
        boolean flag1 = blockstate.getFluidState().isIn(FluidTags.WATER);

        if (flag && !flag1) {

            boolean flag2 = butterfly.teleport(x, y, z, true);
            if (flag2 && !butterfly.isSilent()) {
                level.playSound(player, butterfly.prevX, butterfly.prevY, butterfly.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, butterfly.getSoundCategory(), 1.0F, 1.0F);
                butterfly.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
