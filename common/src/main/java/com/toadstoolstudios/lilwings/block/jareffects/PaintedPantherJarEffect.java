package com.toadstoolstudios.lilwings.block.jareffects;

import java.util.Random;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PaintedPantherJarEffect implements JarEffect {

    private static final int MAX_TIME = 5 * 20;
    private static final int MAX_GROW_TIME = 8 * 20;
    private int growTime;

    private BlockPos pos;
    private int checkCooldown;

    private int lastParticle;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;
        ServerWorld serverLevel = (ServerWorld) level;

        if (pos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                pos = findNearestCocoa(level, blockEntity.getPos());
                checkCooldown = 0;
            }
        } else {
            growTime++;
            lastParticle++;

            if (lastParticle >= 4) {
                spawnParticle(serverLevel, pos, 0.5f, 0.75f, 0.5f);
                addGrowthParticles(serverLevel, pos, 0);
                lastParticle = 0;
            }

            BlockState state = level.getBlockState(pos);
            if (state.isOf(Blocks.COCOA) && state.get(CocoaBlock.AGE) >= 2) {
                if (growTime >= MAX_GROW_TIME) {
                    if (level.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL)) {
                        ItemEntity item = new ItemEntity(level, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(LilWingsItems.CRIMSON_COCOA_BEANS.get(), Math.max(1, random.nextInt(2))));
                        serverLevel.spawnEntity(item);
                    }
                    growTime = 0;
                    pos = null;
                }
            } else {
                pos = null;
                growTime = 0;
            }
        }
    }

    public BlockPos findNearestCocoa(World level, BlockPos jarPos) {
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.add(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.isOf(Blocks.COCOA) && state.get(CocoaBlock.AGE) >= 2) {
                return relativePos;
            }
        }

        return null;
    }

    public static void addGrowthParticles(ServerWorld pLevel, BlockPos pPos, int pData) {
        if (pData == 0) {
            pData = 2;
        }

        BlockState blockstate = pLevel.getBlockState(pPos);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1 = blockstate.getOutlineShape(pLevel, pPos).getMax(Direction.Axis.Y);
            Random random = pLevel.getRandom();
            pLevel.spawnParticles(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, 1, 0, 0, 0, 0);


            for (int i = 0; i < pData; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) pPos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double d7 = (double) pPos.getY() + random.nextDouble() * d1;
                double d8 = (double) pPos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                if (!pLevel.getBlockState((new BlockPos(d6, d7, d8)).down()).isAir()) {
                    pLevel.spawnParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 1, d2, d3, d4, 1);
                }
            }

        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return ParticleTypes.COMPOSTER;
    }
}
