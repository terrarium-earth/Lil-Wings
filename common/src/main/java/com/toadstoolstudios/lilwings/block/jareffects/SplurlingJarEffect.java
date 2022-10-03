package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplurlingJarEffect implements JarEffect {
    private final int cooldownTime = 20 * 30;// 20 * 5; // Time in ticks
    private int timeUntilTick = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (timeUntilTick == 0) {
            List<BlockPos> valuableBlockPos = new ArrayList<>();

            /* iterate in are to find valuables */
            area.forEach((pos) -> {
                Block selBlock = level.getBlockState(pos).getBlock();
                if (isOre(selBlock) && pos != blockEntity.getBlockPos()) valuableBlockPos.add(pos);
            });

//            System.out.println(valuableBlockPos.size());

            timeUntilTick = cooldownTime;

            if (valuableBlockPos.size() == 0) return;

            /* Randomly pick a block between the valuables */
            Random rand = new Random();
            BlockPos selOre = valuableBlockPos.get(rand.nextInt(valuableBlockPos.size()));

            // ? Remove ore and drop it
            level.removeBlock(selOre, false);

            /* TODO Implement recipe to drop `selOre` */

        } else {
            timeUntilTick--;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }

    private boolean isOre(Block block) {
        /* TODO Implement tag for mod compatibility */
        Block[] ores = {
                // ? Overworld ores
                Blocks.COAL_ORE,
                Blocks.COPPER_ORE,
                Blocks.IRON_ORE,
                Blocks.GOLD_ORE,
                Blocks.DIAMOND_ORE,

                Blocks.LAPIS_ORE,
                Blocks.REDSTONE_ORE,
                Blocks.EMERALD_ORE,

                // ? Deepslate ores
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,

                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,

                // ? Nether ores
                Blocks.NETHER_GOLD_ORE,
                Blocks.NETHER_QUARTZ_ORE
        };

        for (Block ore : ores) {
            if (ore == block) return true;
        }

        return false;
    }

}