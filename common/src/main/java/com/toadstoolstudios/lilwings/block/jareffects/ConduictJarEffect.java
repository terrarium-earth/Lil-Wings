package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ConduictJarEffect implements JarEffect {
    double radius = D(16); // Effect radius (radius + Jar + radius)

    private final int cooldownTime = 90; // Time in ticks
    private int timeUntilTick = 0; // Initial delay in ticks

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        BlockPos jarPos = blockEntity.getBlockPos();

        if (timeUntilTick == 0) {
            /* Check Structure? */


            /* Apply effect in area for players */
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
}
