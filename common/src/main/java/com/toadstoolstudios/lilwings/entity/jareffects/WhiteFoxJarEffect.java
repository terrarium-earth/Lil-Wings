package com.toadstoolstudios.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WhiteFoxJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 10 * 20;
    private int cooldown;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            if (random.nextFloat() < 0.1) {
                BlockPos randomPos;
                boolean isValid;

                int tries = 0;
                int areaTries = 0;
                do {
                    randomPos = blockEntity.getBlockPos().offset(getRandomPos().below());
                    isValid = isValidPos(level, randomPos);

                    if (level.getBlockState(randomPos.above()).is(Blocks.SNOW)) {
                        areaTries++;
                    } else {
                        tries++;
                    }
                } while ((tries < 3 || areaTries < area.size()) && !isValid);

                if (isValid) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    BlockPos snowPos = randomPos.above();
                    serverLevel.sendParticles(getParticleType(), snowPos.getX() + 0.5, snowPos.getY() + 0.08f, snowPos.getZ() + 0.5, 100, 0, 0, 0, 0.25f);
                    level.setBlock(snowPos, Blocks.SNOW.defaultBlockState(), Block.UPDATE_ALL);
                }
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    public boolean isValidPos(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockPos abovePos = pos.above();

        return !state.getMaterial().isReplaceable() && level.isEmptyBlock(abovePos) && level.isInWorldBounds(abovePos) &&
                Blocks.SNOW.defaultBlockState().canSurvive(level, abovePos);
    }
}
