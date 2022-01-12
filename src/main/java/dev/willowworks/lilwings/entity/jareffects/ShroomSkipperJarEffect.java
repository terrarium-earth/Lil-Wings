package dev.willowworks.lilwings.entity.jareffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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
    public ParticleOptions getParticleType() {
        return ParticleTypes.CRIMSON_SPORE;
    }

    public void addParticles(ServerLevel level, BlockPos pos) {
        level.sendParticles(getParticleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 25, 0, 0, 0, 0.25f);
    }
}
