package dev.willowworks.lilwings.entity.goals;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

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
        butterfly.getNavigation().startMovingTo(flowerPos.getX() + 0.5D, flowerPos.getY() + 0.5D, flowerPos.getZ() + 0.5D, 1.2D);

        BlockState state = butterfly.world.getBlockState(flowerPos);
        if (state.isOf(Blocks.SPORE_BLOSSOM)) {
            butterfly.setColorType(GraylingType.SPORE_BLOSSOM);
        } else if (state.isOf(Blocks.FLOWERING_AZALEA) || state.isOf(Blocks.FLOWERING_AZALEA_LEAVES)) {
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
            if (!butterfly.world.isInBuildLimit(butterfly.getBlockPos())) return false;
            BlockState state = butterfly.world.getBlockState(pos);
            if (state.isAir()) return false;

            return state.isOf(Blocks.SPORE_BLOSSOM) || state.isOf(Blocks.FLOWERING_AZALEA) || state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
        };
    }
}
