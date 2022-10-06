package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

@SuppressWarnings("FieldCanBeLocal")
public class ConduictJarEffect implements JarEffect {
    double radius = D(16); // Effect radius (radius + Jar + radius)
    private final int cooldownTime = 90; // Time in ticks

    boolean isStructureValid = false;
    private int timeUntilTick = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        BlockPos jarPos = blockEntity.getBlockPos();

        if (timeUntilTick == 0) {
            int validBlocks = 0;

            /* Check Structure? */
            Block[] prismAlt = new Block[]{Blocks.PRISMARINE, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN, Blocks.PRISMARINE_BRICKS};

            if (isSame(0, -1, 1, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(0, 0, 2, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(1, -1, 0, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(2, 0, 0, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(-1, -1, 0, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(-2, 0, 0, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(0, -1, -1, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(0, 0, -2, prismAlt, level, jarPos)) validBlocks++;
            if (isSame(0, 0, 0, new Block[]{LilWingsBlocks.BUTTERFLY_JAR.get()}, level, jarPos)) validBlocks++;
            if (isSame(0, -1, 0, new Block[]{Blocks.WATER}, level, jarPos)) validBlocks++;

            /* Debug audio */
            if (isStructureValid == false && validBlocks == 10) {
                level.playSound(null, jarPos, SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (isStructureValid == true && validBlocks != 10) {
                level.playSound(null, jarPos, SoundEvents.CONDUIT_DEACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            isStructureValid = validBlocks == 10;

            /* Apply effect in area for players */
            if (isStructureValid) {
                double X = D(jarPos.getX());
                double Y = D(jarPos.getY());
                double Z = D(jarPos.getZ());

                AABB aABB = new AABB(X, Y, Z, (X + 1), (Y + 1), (Z + 1))
                        .inflate(radius)
                        .expandTowards(0.0, D(level.getHeight()), 0.0);

                level.getEntitiesOfClass(Player.class, aABB)
                        .forEach(player -> {
                            if (jarPos.closerThan(player.blockPosition(), radius) && player.isInWaterOrRain())
                                player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 120, 0, true, true));
                        });
            }

            timeUntilTick = cooldownTime;
        } else {
            timeUntilTick--;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }

    /**
     * Cast an int to a double
     */
    private double D(int i) {
        return (double) i;
    }

    private boolean isSame(int X, int Y, int Z, Block[] blocks, Level level, BlockPos jarPos) {
        for (Block block : blocks) {
            if (level.getBlockState(jarPos.offset(X, Y, Z)).is(block)) return true;
        }

        return false;
    }

}
