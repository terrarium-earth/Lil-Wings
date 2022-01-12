package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class EnderWingJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            Player player = level.getNearestPlayer(blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ(), 5, false);

            ServerLevel serverLevel = (ServerLevel) level;
            if (player != null) {
                BlockPos pos = getRandomPos(level, player);
                ((ServerPlayer) player).connection.send(new ClientboundCustomSoundPacket(SoundEvents.ENDERMAN_TELEPORT.getLocation(), SoundSource.MASTER, new Vec3(pos.getX(), pos.getY(), pos.getZ()), 1, 1));

                player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                serverLevel.sendParticles(getParticleType(), player.getX(), player.getY(), player.getZ(), 100, 0.5, 0.5, 0.5, 0.5);
            }

            cooldown = 0;
        }
    }

    public BlockPos getRandomPos(Level level, Player player) {
        int x = Mth.nextInt(random, -100, 100) + player.blockPosition().getX();
        int z = Mth.nextInt(random, -100, 100) + player.blockPosition().getZ();
        int y = getSpawnY(level, x, z);

        return new BlockPos(x, y, z);
    }

    public int getSpawnY(Level level, int x, int z) {
        for (int i = level.getMaxBuildHeight(); i > level.getMinBuildHeight(); i--) {
            BlockPos pos = new BlockPos(x, i, z);
            BlockState state = level.getBlockState(pos);

            if (!state.isAir() && state.getMaterial().isSolid() && state.getMaterial() != Material.LAVA && state.getMaterial() != Material.WATER && state.getMaterial() != Material.LAVA) {
                BlockState upState = level.getBlockState(pos.above());

                if (upState.isAir() || (!upState.getMaterial().isSolid() && !upState.getMaterial().isLiquid() && upState.getMaterial() != Material.LAVA && upState.getMaterial() != Material.WATER && upState.getMaterial() != Material.LAVA)) {
                    return pos.getY() + 1;
                }
            }
        }

        return level.getMaxBuildHeight();
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.REVERSE_PORTAL;
    }
}
