package com.toadstoolstudios.lilwings.entity.effects.forge;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class EnderflyCatchEffectImpl {
    public static void teleport(Player player, Level level, ButterflyEntity butterfly, double x, double y, double z) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(x, y, z);

        while (blockPos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockPos).getMaterial().blocksMotion()) {
            blockPos.move(Direction.DOWN);
        }

        BlockState blockstate = level.getBlockState(blockPos);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);

        if (flag && !flag1) {
            EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(butterfly, x, y, z);
            if (event.isCanceled()) return;

            boolean flag2 = butterfly.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !butterfly.isSilent()) {
                level.playSound(player, butterfly.xo, butterfly.yo, butterfly.zo, SoundEvents.ENDERMAN_TELEPORT, butterfly.getSoundSource(), 1.0F, 1.0F);
                butterfly.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
