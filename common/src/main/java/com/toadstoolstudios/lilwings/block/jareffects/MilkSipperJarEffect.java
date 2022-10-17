package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "SameParameterValue"})
public class MilkSipperJarEffect implements JarEffect {
    private final double timePmb = 1.2; // Fill time per mb (x * 1000) in ticks
    private final int cauldronSearchRadius = 2;
    private final int cowSearchRadius = 4;

    private int timeUntilTick = 100; // initial delay in ticks

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        BlockPos jarPos = blockEntity.getBlockPos();
        List<Cow> cows = level.getEntitiesOfClass(Cow.class, new AABB(jarPos).inflate(cowSearchRadius)).stream().filter(cow -> !cow.isBaby()).toList();

        if (timeUntilTick == 0) {
                if (cows.size() != 0) getBlockPosInArea(jarPos, cauldronSearchRadius).stream().filter(pos ->
                        level.getBlockState(pos).is(Blocks.CAULDRON)).findAny().ifPresent(pos ->
                        level.setBlock(pos, ButterGoldJarEffect.getMilkCauldron().defaultBlockState(), 1));

            if (!level.isClientSide()) timeUntilTick = Math.max((int) (timePmb * 1000 - cows.size() * 16), 300);
        } else {
            if (!level.isClientSide()) timeUntilTick--;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }

    //    Temporary Method until area get fixed
    private List<BlockPos> getBlockPosInArea(BlockPos startingPos, int radius) {
        List<BlockPos> BlockPos = new ArrayList<>();

        BlockPos cornerPos = startingPos.relative(Direction.UP, radius).relative(Direction.SOUTH, radius).relative(Direction.WEST, radius);

        for (var X = 0; X < radius * 2 + 1; X++) {
            for (var Z = 0; Z < radius * 2 + 1; Z++) {
                for (var Y = 0; Y < radius * 2 + 1; Y++) {
                    BlockPos.add(cornerPos.relative(Direction.EAST, X).relative(Direction.DOWN, Y).relative(Direction.NORTH, Z));
                }
            }
        }

        return BlockPos;
    }

}
