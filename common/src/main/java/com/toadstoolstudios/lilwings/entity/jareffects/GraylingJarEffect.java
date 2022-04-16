package com.toadstoolstudios.lilwings.entity.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GraylingJarEffect implements JarEffect {

    private static final int MAX_TIME = 20 * 20;
    private static final int MAX_GROW_TIME = 20 * 20;
    private int growTime;

    private BlockPos azaleaPos;
    private int checkCooldown;

    private int lastParticle;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        ServerWorld serverLevel = (ServerWorld) level;

        if (azaleaPos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                azaleaPos = findNearestAzalea(level, blockEntity.getPos());
                checkCooldown = 0;
            }
        } else {
            growTime++;
            lastParticle++;

            if (lastParticle >= 10) {
                spawnOutlineParticles(serverLevel, azaleaPos, 0.5f, 1);
                lastParticle = 0;
            }

            BlockState state = level.getBlockState(azaleaPos);

            if (state.isOf(Blocks.AZALEA)) {
                if (growTime >= MAX_GROW_TIME) {
                    level.setBlockState(azaleaPos, Blocks.FLOWERING_AZALEA.getDefaultState(), Block.NOTIFY_ALL);
                    growTime = 0;
                    azaleaPos = null;
                }
            } else {
                azaleaPos = null;
                growTime = 0;
            }
        }
    }

    public BlockPos findNearestAzalea(World level, BlockPos jarPos) {
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.add(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.isOf(Blocks.AZALEA)) {
                return relativePos;
            }
        }

        return null;
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
