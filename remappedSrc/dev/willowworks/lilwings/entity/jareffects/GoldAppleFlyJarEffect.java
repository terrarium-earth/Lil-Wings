package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.registry.LilWingsParticles;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class GoldAppleFlyJarEffect implements JarEffect {
    private static final int MAX_COOLDOWN = 4 * 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        cooldown++;

        if (random.nextFloat() <= 0.15) {
            ServerWorld serverLevel = (ServerWorld) level;
            serverLevel.spawnParticles(getParticleType(), blockEntity.getPos().getX() + 0.5, blockEntity.getPos().getY() + 0.8, blockEntity.getPos().getZ() + 0.5, 2, 0, 0, 0, 0);
        }

        if (cooldown >= MAX_COOLDOWN) {
            PlayerEntity player = level.getClosestPlayer(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ(), 5, false);

            if(player != null) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, MAX_COOLDOWN * 2, 0));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, MAX_COOLDOWN * 2, 4));

            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return LilWingsParticles.GOLDAPPLE_HEARTS.get();
    }
}
