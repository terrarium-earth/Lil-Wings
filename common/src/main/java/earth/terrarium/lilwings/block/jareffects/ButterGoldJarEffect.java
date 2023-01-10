package earth.terrarium.lilwings.block.jareffects;

import earth.terrarium.lilwings.block.ButterflyJarBlockEntity;
import earth.terrarium.lilwings.registry.LilWingsBlocks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ButterGoldJarEffect implements JarEffect {

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (level.getBlockState(blockEntity.getBlockPos().below()).is(getMilkCauldron())) {
            if ((int) (Math.random() * 1000) == 1) {
                level.setBlock(blockEntity.getBlockPos().below(), LilWingsBlocks.BUTTER_CAULDRON.get().defaultBlockState(), 2);
            }
        }

    }

    @ExpectPlatform
    public static Block getMilkCauldron() {
        throw new AssertionError();
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }
}