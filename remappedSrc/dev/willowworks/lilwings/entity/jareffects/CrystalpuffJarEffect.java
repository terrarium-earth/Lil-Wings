package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.registry.LilWingsParticles;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalpuffJarEffect implements JarEffect {

    private static final int MAX_TIME = 20 * 5;
    private static final int MAX_GROW_TIME = 20 * 5;
    private int growTime;
    private int lastParticle;

    List<BlockPos> amethystArea = Util.make(() -> {
        List<BlockPos> list = new ArrayList<>();

        for (BlockPos pos : BlockPos.iterate(-3, -2, -3, 3, 5, 3)) {
            list.add(pos.toImmutable());
        }

        return list;
    });


    private BlockPos crystalPos;
    private int checkCooldown;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        ServerWorld serverLevel = (ServerWorld) level;

        if (crystalPos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                crystalPos = findNearestCrystal(level, blockEntity.getPos());
                checkCooldown = 0;
            }
        } else {
            growTime++;
            lastParticle++;

            if (lastParticle >= 10) {
                spawnParticle(serverLevel, crystalPos, 0.5, 0.2f, 0.5);
                spawnParticle(serverLevel, crystalPos, 0.5, 0.2f, 0.5);
                spawnParticle(serverLevel, crystalPos, 0.5, 0.2f, 0.5);
                lastParticle = 0;
            }

            BlockState state = level.getBlockState(crystalPos);
            if (state.isOf(Blocks.SMALL_AMETHYST_BUD)) {
                checkAndGrow(level, Blocks.MEDIUM_AMETHYST_BUD);
            } else if (state.isOf(Blocks.MEDIUM_AMETHYST_BUD)) {
                checkAndGrow(level, Blocks.LARGE_AMETHYST_BUD);
            } else if (state.isOf(Blocks.LARGE_AMETHYST_BUD)) {
                checkAndGrow(level, Blocks.AMETHYST_CLUSTER);
            } else {
                crystalPos = null;
                growTime = 0;
            }
        }
    }

    public void checkAndGrow(World level, Block targetBlock) {
        if (growTime >= MAX_GROW_TIME) {
            level.setBlockState(crystalPos, targetBlock.getStateWithProperties(level.getBlockState(crystalPos)), Block.NOTIFY_ALL);
            growTime = 0;
            crystalPos = null;
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return LilWingsParticles.AMETHYST_GROW.get();
    }

    public BlockPos findNearestCrystal(World level, BlockPos jarPos) {
        for (BlockPos pos : amethystArea) {
            BlockPos relativePos = jarPos.add(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && (state.isOf(Blocks.SMALL_AMETHYST_BUD) || state.isOf(Blocks.MEDIUM_AMETHYST_BUD) || state.isOf(Blocks.LARGE_AMETHYST_BUD))) {
                return relativePos;
            }
        }

        return null;
    }
}
