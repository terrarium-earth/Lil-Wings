package com.toadstoolstudios.lilwings.entity.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class ButterGoldJarEffect implements JarEffect {

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if(level.isClient) return;
        if(level.getBlockState(blockEntity.getPos().down()).isOf(LilWingsBlocks.MILK_CAULDRON.get())) {
            if ((int)(Math.random() * 1000) == 1) {
                level.setBlockState(blockEntity.getPos().down(), LilWingsBlocks.BUTTER_CAULDRON.get().getDefaultState(), 2);
            }
        }

    }

    @Override
    public ParticleEffect getParticleType() {
        return null;
    }
}