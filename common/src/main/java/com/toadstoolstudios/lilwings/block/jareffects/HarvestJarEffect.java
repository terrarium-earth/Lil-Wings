package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class HarvestJarEffect implements JarEffect {
    private final int cooldownTime = 20 * 30; // Time in ticks
    private int timeUntilTick = 0;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (timeUntilTick == 0) {
            BlockPos wantedBlockPos = blockEntity.getBlockPos().below(1);
            Block wantedBlock = level.getBlockState(wantedBlockPos).getBlock();

            List<BlockPos> destroyableBlockPos = new ArrayList<>();

            /* Get a list of selected block in the area */
            area.forEach((pos) -> {
                Block block = level.getBlockState(pos).getBlock();
                if (block == wantedBlock && pos != wantedBlockPos) destroyableBlockPos.add(pos);

                System.out.println(block.getName());
                System.out.println(wantedBlock.getName());

                System.out.println(block == wantedBlock);
                System.out.println(pos != wantedBlockPos);
                System.out.println(block == wantedBlock && pos != wantedBlockPos);
                System.out.println("----------------------------------");
            });


            timeUntilTick = cooldownTime;

            if (destroyableBlockPos.size() == 0) return;

            /* Remove the first block from world*/
            BlockPos selBlock = destroyableBlockPos.get(0);
            level.removeBlock(selBlock, false);

            /* TODO Implement recipe to drop `selBlock` */

        } else {
            timeUntilTick--;
        }
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }
}