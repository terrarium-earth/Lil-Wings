package dev.willowworks.lilwings.entity.goals;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class GraylingFlowerGoal extends FindFlowerGoal {

    public GraylingFlowerGoal(ButterflyEntity butterfly) {
        super(butterfly);
    }

    @Override
    public void stop() {
        super.stop();
        butterfly.setColorType(GraylingType.NORMAL);
    }

    @Override
    protected void moveToFlower(BlockPos flowerPos) {
        butterfly.setSavedOtherPos(flowerPos);
        butterfly.getNavigation().moveTo(flowerPos.getX() + 0.5D, flowerPos.getY() + 0.5D, flowerPos.getZ() + 0.5D, 1.2D);

        BlockState state = butterfly.level.getBlockState(flowerPos);
        if (state.is(Blocks.SPORE_BLOSSOM)) {
            butterfly.setColorType(GraylingType.SPORE_BLOSSOM);
        } else if (state.is(Blocks.FLOWERING_AZALEA) || state.is(Blocks.FLOWERING_AZALEA_LEAVES)) {
            butterfly.setColorType(GraylingType.AZALEA);
        } else {
            butterfly.setColorType(GraylingType.NORMAL);
        }
    }

    @Override
    public int getPollinationTime() {
        return 1000;
    }

    @Override
    protected void updateCooldown(int amount) {
        butterfly.setOtherCooldown(amount);
    }

    @Override
    protected BlockPos getSavedPos() {
        return butterfly.getSavedOtherPos();
    }

    @Override
    public Predicate<BlockPos> getFlowerBlockPredicate() {
        return pos -> {
            if (!butterfly.level.isInWorldBounds(butterfly.blockPosition())) return false;
            BlockState state = butterfly.level.getBlockState(pos);
            if (state.isAir()) return false;

            return state.is(Blocks.SPORE_BLOSSOM) || state.is(Blocks.FLOWERING_AZALEA) || state.is(Blocks.FLOWERING_AZALEA_LEAVES);
        };
    }
}
