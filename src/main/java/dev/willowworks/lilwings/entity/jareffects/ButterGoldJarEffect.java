package dev.willowworks.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;

public class ButterGoldJarEffect implements JarEffect {

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if(level.isClientSide) return;
        if(level.getBlockState(blockEntity.getBlockPos().below()).is(LilWingsBlocks.MILK_CAULDRON.get())) {
            int b = (int)(Math.random() * 1000);
            System.out.println(b);
            if (b == 1) {
                level.setBlock(blockEntity.getBlockPos().below(), LilWingsBlocks.BUTTER_CAULDRON.get().defaultBlockState(), 2);
            }
        }

    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }
}