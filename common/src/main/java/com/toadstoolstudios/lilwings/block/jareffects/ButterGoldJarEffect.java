package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButterGoldJarEffect implements JarEffect {

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if(level.isClient) return;
        if(isMilkCauldron(level, blockEntity.getPos().down())) {
            if ((int)(Math.random() * 1000) == 1) {
                level.setBlockState(blockEntity.getPos().down(), LilWingsBlocks.BUTTER_CAULDRON.get().getDefaultState(), 2);
            }
        }
    }

    @ExpectPlatform
    public static boolean isMilkCauldron(World level, BlockPos bPos) {
        throw new AssertionError();
    }

    @Override
    public ParticleEffect getParticleType() {
        return null;
    }
}