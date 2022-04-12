package com.toadstoolstudios.lilwings.entity.jareffects;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ShroomSkipperJarEffect extends AponiJarEffect {

    @Override
    public Block getReplaceBlock() {
        return Blocks.RED_MUSHROOM;
    }

    @Override
    public Block getSourceBlock() {
        return Blocks.BROWN_MUSHROOM;
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.CRIMSON_SPORE;
    }

    public void addParticles(ServerWorld level, BlockPos pos) {
        level.spawnParticles(getParticleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 25, 0, 0, 0, 0.25f);
    }
}
