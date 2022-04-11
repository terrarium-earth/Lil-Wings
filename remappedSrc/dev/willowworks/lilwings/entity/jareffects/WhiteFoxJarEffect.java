package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WhiteFoxJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 10 * 20;
    private int cooldown;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            if (random.nextFloat() < 0.1) {
                BlockPos randomPos;
                boolean isValid;

                int tries = 0;
                int areaTries = 0;
                do {
                    randomPos = blockEntity.getPos().add(getRandomPos().down());
                    isValid = isValidPos(level, randomPos);

                    if (level.getBlockState(randomPos.up()).isOf(Blocks.SNOW)) {
                        areaTries++;
                    } else {
                        tries++;
                    }
                } while ((tries < 3 || areaTries < area.size()) && !isValid);

                if (isValid) {
                    ServerWorld serverLevel = (ServerWorld) level;
                    BlockPos snowPos = randomPos.up();
                    serverLevel.spawnParticles(getParticleType(), snowPos.getX() + 0.5, snowPos.getY() + 0.08f, snowPos.getZ() + 0.5, 100, 0, 0, 0, 0.25f);
                    level.setBlockState(snowPos, Blocks.SNOW.getDefaultState(), Block.NOTIFY_ALL);
                }
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    public boolean isValidPos(World level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockPos abovePos = pos.up();

        return !state.getMaterial().isReplaceable() && level.isAir(abovePos) && level.isInBuildLimit(abovePos) &&
                Blocks.SNOW.getDefaultState().canPlaceAt(level, abovePos);
    }
}
