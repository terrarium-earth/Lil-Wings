package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SwallowTailJarEffect implements JarEffect {

    private static final int MAX_TIME = 8 * 20;

    private int checkCooldown;


    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        ServerWorld serverLevel = (ServerWorld) level;

        if(checkCooldown >= MAX_TIME) {
            BlockPos crop = findCrop(level, blockEntity.getPos());
            if(crop != null) {
                Block block = level.getBlockState(crop).getBlock();
                if(block instanceof CropBlock cropBlock) {
                    cropBlock.grow(serverLevel, level.random, crop, level.getBlockState(crop));
                    serverLevel.spawnParticles(ParticleTypes.HAPPY_VILLAGER, crop.getX() + 0.5, crop.getY() + 0.5, crop.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.2);
                }
                checkCooldown = 0;
            }
        }
        checkCooldown = (checkCooldown + 1) % Integer.MAX_VALUE;
    }

    @Nullable
    public BlockPos findCrop(World level, BlockPos jarPos) {
        ArrayList<BlockPos> crops = new ArrayList<>();
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.add(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.getBlock() instanceof CropBlock) {
                crops.add(relativePos);
            }
        }

        if(crops.isEmpty()) return null;

        return crops.get(level.random.nextInt(crops.size()));
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
