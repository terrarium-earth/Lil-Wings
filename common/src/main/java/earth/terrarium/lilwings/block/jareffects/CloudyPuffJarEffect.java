package earth.terrarium.lilwings.block.jareffects;

import earth.terrarium.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CloudyPuffJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 4 * 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (!blockEntity.getLevel().getBlockState(blockEntity.getBlockPos().above()).isAir()) return;
        cooldown++;

        if (random.nextFloat() <= 0.15 && level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(getParticleType(), blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.8, blockEntity.getBlockPos().getZ() + 0.5, 1, 0.5, 0, 0.5, 0.1);
        }

        if (cooldown >= MAX_COOLDOWN) {
            Player player = level.getNearestPlayer(blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ(), 5, false);

            if(player != null) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, MAX_COOLDOWN * 2, 0));
            }

            cooldown = 0;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.CAMPFIRE_COSY_SMOKE;
    }


}
