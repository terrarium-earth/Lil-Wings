package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AponiJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 10 * 20;
    private int cooldown;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            if (random.nextFloat() <= 0.9) {
                BlockPos randomPos;
                boolean isValid;

                int tries = 0;
                do {
                    randomPos = blockEntity.getPos().add(getRandomPos());
                    isValid = isValidPos(level, randomPos);

                    tries++;
                } while (tries < 3 && !isValid);

                if (isValid) {
                    ServerWorld serverLevel = (ServerWorld) level;
                    addParticles(serverLevel, randomPos);
                    level.setBlockState(randomPos, getReplaceBlock().getDefaultState(), Block.NOTIFY_ALL);
                }
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return LilWingsParticles.BROWN_SPORE.get();
    }

    public void addParticles(ServerWorld level, BlockPos pos) {
        level.spawnParticles(getParticleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 25, 0, 0, 0, 0.05f);
    }

    public Block getReplaceBlock() {
        return Blocks.BROWN_MUSHROOM;
    }

    public Block getSourceBlock() {
        return Blocks.RED_MUSHROOM;
    }

    public boolean isValidPos(World level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isOf(getSourceBlock());
    }
}
