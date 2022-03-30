package com.toadstoolstudios.lilwings.entity.effects;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class EnderflyCatchEffect implements CatchEffect {

    @Override
    public void onCatch(Player player, ButterflyEntity butterfly, int catchAmount) {
        if (!butterfly.level.isClientSide()) {
            double x = butterfly.getX() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            double y = butterfly.getY() + (double) (butterfly.getRandom().nextInt(64) - 32);
            double z = butterfly.getZ() + (butterfly.getRandom().nextDouble() - 0.5D) * 64.0D;
            teleport(player, butterfly.level, butterfly, x, y, z);
        }
    }

    private boolean teleport(Player player, Level level, ButterflyEntity butterfly, double x, double y, double z) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(x, y, z);

        while (blockPos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockPos).getMaterial().blocksMotion()) {
            blockPos.move(Direction.DOWN);
        }

        BlockState blockstate = level.getBlockState(blockPos);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);

        if (flag && !flag1) {
            EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(butterfly, x, y, z);
            if (event.isCanceled()) return false;

            boolean flag2 = butterfly.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !butterfly.isSilent()) {
                level.playSound(player, butterfly.xo, butterfly.yo, butterfly.zo, SoundEvents.ENDERMAN_TELEPORT, butterfly.getSoundSource(), 1.0F, 1.0F);
                butterfly.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }
}
