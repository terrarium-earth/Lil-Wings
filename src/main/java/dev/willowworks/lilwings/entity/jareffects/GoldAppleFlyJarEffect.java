package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GoldAppleFlyJarEffect implements JarEffect {
    private static final int MAX_COOLDOWN = 4 * 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        cooldown++;

        if (random.nextFloat() <= 0.15) {
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(getParticleType(), blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.8, blockEntity.getBlockPos().getZ() + 0.5, 1, 0.5, 0.5, 0.5, 0.1);
        }

        if (cooldown >= MAX_COOLDOWN) {
            Player player = level.getNearestPlayer(blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ(), 5, false);

            if(player != null) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, MAX_COOLDOWN * 2, 0));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, MAX_COOLDOWN * 2, 4));

            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.HEART;
    }
}
