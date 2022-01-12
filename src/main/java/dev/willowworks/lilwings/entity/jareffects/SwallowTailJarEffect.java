package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SwallowTailJarEffect implements JarEffect {

    private static final int MAX_TIME = 4 * 20;
    private static final int MAX_GROW_TIME = 5 * 20;
    private int growTime;

    private BlockPos cropPos;
    private int checkCooldown;

    private int lastParticle;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel) level;

        if (cropPos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                cropPos = findNearestCrop(level, blockEntity.getBlockPos());
                checkCooldown = 0;
            }
        } else {
            growTime++;
            lastParticle++;

            if (lastParticle >= 10) {
                spawnOutlineParticles(serverLevel, cropPos, 0, 1);
                lastParticle = 0;
            }

            BlockState state = level.getBlockState(cropPos);

            if (state.getBlock() instanceof CropBlock cropBlock) {
                if (growTime >= MAX_GROW_TIME) {
                    cropBlock.performBonemeal(serverLevel, random, cropPos, state);
                    growTime = 0;
                    cropPos = null;
                }
            } else {
                cropPos = null;
                growTime = 0;
            }
        }
    }

    public BlockPos findNearestCrop(Level level, BlockPos jarPos) {
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.offset(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.getBlock() instanceof CropBlock) {
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
