package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.registry.LilWingsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class PaintedPantherJarEffect implements JarEffect {

    private static final int MAX_TIME = 5 * 20;
    private static final int MAX_GROW_TIME = 8 * 20;
    private int growTime;

    private BlockPos pos;
    private int checkCooldown;

    private int lastParticle;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel) level;

        if (pos == null) {
            checkCooldown++;

            if (checkCooldown >= MAX_TIME) {
                pos = findNearestCocoa(level, blockEntity.getBlockPos());
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
            if (state.is(Blocks.COCOA) && state.getValue(CocoaBlock.AGE) >= 2) {
                if (growTime >= MAX_GROW_TIME) {
                    if (level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)) {
                        ItemEntity item = new ItemEntity(level, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(LilWingsItems.CRIMSON_COCOA_BEANS.get(), Math.max(1, random.nextInt(2))));
                        serverLevel.addFreshEntity(item);
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

    public BlockPos findNearestCocoa(Level level, BlockPos jarPos) {
        for (BlockPos pos : area) {
            BlockPos relativePos = jarPos.offset(pos);
            BlockState state = level.getBlockState(relativePos);
            if (!state.isAir() && state.is(Blocks.COCOA) && state.getValue(CocoaBlock.AGE) >= 2) {
                return relativePos;
            }
        }

        return null;
    }

    public static void addGrowthParticles(ServerLevel pLevel, BlockPos pPos, int pData) {
        if (pData == 0) {
            pData = 2;
        }

        BlockState blockstate = pLevel.getBlockState(pPos);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1 = blockstate.getShape(pLevel, pPos).max(Direction.Axis.Y);
            Random random = pLevel.getRandom();
            pLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, 1, 0, 0, 0, 0);


            for (int i = 0; i < pData; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) pPos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double d7 = (double) pPos.getY() + random.nextDouble() * d1;
                double d8 = (double) pPos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                if (!pLevel.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    pLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 1, d2, d3, d4, 1);
                }
            }

        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return ParticleTypes.COMPOSTER;
    }
}
