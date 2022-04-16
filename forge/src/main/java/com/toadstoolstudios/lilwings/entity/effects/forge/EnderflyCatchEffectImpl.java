package com.toadstoolstudios.lilwings.entity.effects.forge;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityTeleportEvent;

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
            EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(butterfly, x, y, z);
            if (event.isCanceled()) return;

            boolean flag2 = butterfly.teleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !butterfly.isSilent()) {
                level.playSound(player, butterfly.prevX, butterfly.prevY, butterfly.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, butterfly.getSoundCategory(), 1.0F, 1.0F);
                butterfly.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
