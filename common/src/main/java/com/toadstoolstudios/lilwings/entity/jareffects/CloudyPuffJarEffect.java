package com.toadstoolstudios.lilwings.entity.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class CloudyPuffJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 4 * 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        if (!blockEntity.getWorld().getBlockState(blockEntity.getPos().up()).isAir()) return;
        cooldown++;

        if (random.nextFloat() <= 0.15) {
            ServerWorld serverLevel = (ServerWorld) level;
            serverLevel.spawnParticles(getParticleType(), blockEntity.getPos().getX() + 0.5, blockEntity.getPos().getY() + 0.8, blockEntity.getPos().getZ() + 0.5, 1, 0.5, 0, 0.5, 0.1);
        }

        if (cooldown >= MAX_COOLDOWN) {
            PlayerEntity player = level.getClosestPlayer(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ(), 5, false);

            if(player != null) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, MAX_COOLDOWN * 2, 0));
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.CAMPFIRE_COSY_SMOKE;
    }


}
