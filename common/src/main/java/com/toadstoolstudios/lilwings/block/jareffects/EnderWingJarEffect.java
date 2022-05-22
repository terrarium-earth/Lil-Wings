package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderWingJarEffect implements JarEffect {

    private static final int MAX_COOLDOWN = 20;
    public int cooldown = 0;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        cooldown++;

        if (cooldown >= MAX_COOLDOWN) {
            PlayerEntity player = level.getClosestPlayer(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ(), 5, false);

            ServerWorld serverLevel = (ServerWorld) level;
            if (player != null) {
                BlockPos pos = getRandomPos(level, player);
                ((ServerPlayerEntity) player).networkHandler.sendPacket(new PlaySoundIdS2CPacket(SoundEvents.ENTITY_ENDERMAN_TELEPORT.getId(), SoundCategory.MASTER, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 1, 1));

                player.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
                serverLevel.spawnParticles(getParticleType(), player.getX(), player.getY(), player.getZ(), 100, 0.5, 0.5, 0.5, 0.5);
            }

            cooldown = 0;
        }
    }

    public BlockPos getRandomPos(World level, PlayerEntity player) {
        int x = MathHelper.nextInt(random, -100, 100) + player.getBlockPos().getX();
        int z = MathHelper.nextInt(random, -100, 100) + player.getBlockPos().getZ();
        int y = getSpawnY(level, x, z);

        return new BlockPos(x, y, z);
    }

    public int getSpawnY(World level, int x, int z) {
        for (int i = level.getTopY(); i > level.getBottomY(); i--) {
            BlockPos pos = new BlockPos(x, i, z);
            BlockState state = level.getBlockState(pos);

            if (!state.isAir() && state.getMaterial().isSolid() && state.getMaterial() != Material.LAVA && state.getMaterial() != Material.WATER && state.getMaterial() != Material.LAVA) {
                BlockState upState = level.getBlockState(pos.up());

                if (upState.isAir() || (!upState.getMaterial().isSolid() && !upState.getMaterial().isLiquid() && upState.getMaterial() != Material.LAVA && upState.getMaterial() != Material.WATER && upState.getMaterial() != Material.LAVA)) {
                    return pos.getY() + 1;
                }
            }
        }

        return level.getTopY();
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.REVERSE_PORTAL;
    }
}
