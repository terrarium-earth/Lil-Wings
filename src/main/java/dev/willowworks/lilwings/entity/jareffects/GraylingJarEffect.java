package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GraylingJarEffect implements JarEffect {

    private static final int MAX_TIME = 20 * 20;
    private static final int MAX_GROW_TIME = 20 * 20;
    private int growTime;

    private BlockPos azaleaPos;
    private int checkCooldown;

    private int lastParticle;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel) level;

        if (azaleaPos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                azaleaPos = findNearestAzalea(level, blockEntity.getBlockPos());
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

            if (state.is(Blocks.AZALEA)) {
                if (growTime >= MAX_GROW_TIME) {
                    level.setBlock(azaleaPos, Blocks.FLOWERING_AZALEA.defaultBlockState(), Block.UPDATE_ALL);
                    growTime = 0;
                    azaleaPos = null;
                }
            } else {
                azaleaPos = null;
                growTime = 0;
            }
        }
    }

    public BlockPos findNearestAzalea(Level level, BlockPos jarPos) {
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.offset(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.is(Blocks.AZALEA)) {
                return relativePos;
            }
        }

        return null;
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
