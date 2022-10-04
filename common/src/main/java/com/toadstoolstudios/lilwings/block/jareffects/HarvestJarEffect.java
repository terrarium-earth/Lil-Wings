package com.toadstoolstudios.lilwings.block.jareffects;

import java.util.ArrayList;
import java.util.List;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HarvestJarEffect implements JarEffect {
    /* Tag of blocks that don't get destroyed */
    public static final TagKey<Block> UNHARVESTABLE_BLOCKS_TAG = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(LilWings.MODID, "unharvestable_blocks__harvest_jar_effect"));

    private final int cooldownTime = 20 * 30; // Time in ticks
    private final int radius = 2; // Test for `getBlockPosInArea`

    private int timeUntilTick = 100; // Initial delay in ticks

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        BlockPos jarPos = blockEntity.getBlockPos();

        if (timeUntilTick == 0) {
            BlockPos wantedBlockPos = jarPos.below(1);
            Block wantedBlock = level.getBlockState(wantedBlockPos).getBlock();

            // Filter blocks and drop them
            getBlockPosInArea(jarPos, radius).stream()
                    .filter(pos -> level.getBlockState(pos).is(wantedBlock)
                            && !pos.equals(wantedBlockPos)
                            && !pos.equals(jarPos)
                            && !level.getBlockState(pos).is(UNHARVESTABLE_BLOCKS_TAG)
                    )
                    .findAny().ifPresent((pos) -> level.destroyBlock(pos, true));

            timeUntilTick = cooldownTime;
        } else {
            timeUntilTick--;
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