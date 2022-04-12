package com.toadstoolstudios.lilwings.entity.jareffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface JarEffect {

    Random random = new Random();
    List<BlockPos> area = Util.make(() -> {
        List<BlockPos> list = new ArrayList<>();

        for (BlockPos pos : BlockPos.iterate(-2, 0, -2, 2, 2, 2)) {
            list.add(pos.toImmutable());
        }

        return list;
    });

    void tickEffect(World level, ButterflyJarBlockEntity blockEntity);

    ParticleEffect getParticleType();

    default BlockPos getRandomPos() {
        return area.get(random.nextInt(area.size()));
    }

    default void spawnOutlineParticles(ServerWorld serverLevel, BlockPos pos) {
        spawnOutlineParticles(serverLevel, pos, 0, 1);
    }

    default void spawnOutlineParticles(ServerWorld serverLevel, BlockPos pos, float minY, float maxY) {
        spawnParticle(serverLevel, pos, 0, maxY, 0);
        spawnParticle(serverLevel, pos, 1, maxY, 1);
        spawnParticle(serverLevel, pos, 1, maxY, 0);
        spawnParticle(serverLevel, pos, 0, maxY, 1);

        spawnParticle(serverLevel, pos, 0, minY, 0);
        spawnParticle(serverLevel, pos, 1, minY, 1);
        spawnParticle(serverLevel, pos, 1, minY, 0);
        spawnParticle(serverLevel, pos, 0, minY, 1);
    }

    default void spawnParticle(ServerWorld level, BlockPos pos, double x, double y, double z) {
        level.spawnParticles(getParticleType(), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 1, 0, 0, 0, 0.5f);
    }
}
