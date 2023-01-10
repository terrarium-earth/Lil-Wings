package earth.terrarium.lilwings.block.jareffects;

import earth.terrarium.lilwings.block.ButterflyJarBlockEntity;
import earth.terrarium.lilwings.registry.LilWingsParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AponiJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 10 * 20;
    private int cooldown;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            if (random.nextFloat() <= 0.9) {
                BlockPos randomPos;
                boolean isValid;

                int tries = 0;
                do {
                    randomPos = blockEntity.getBlockPos().offset(getRandomPos());
                    isValid = isValidPos(level, randomPos);

                    tries++;
                } while (tries < 3 && !isValid);

                if (isValid && level instanceof ServerLevel serverLevel) {
                    addParticles(serverLevel, randomPos);
                    level.setBlock(randomPos, getReplaceBlock().defaultBlockState(), Block.UPDATE_ALL);
                }
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return LilWingsParticles.BROWN_SPORE.get();
    }

    public void addParticles(ServerLevel level, BlockPos pos) {
        level.sendParticles(getParticleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 25, 0, 0, 0, 0.05f);
    }

    public Block getReplaceBlock() {
        return Blocks.BROWN_MUSHROOM;
    }

    public Block getSourceBlock() {
        return Blocks.RED_MUSHROOM;
    }

    public boolean isValidPos(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(getSourceBlock());
    }
}
