package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;

public class EmberJarEffect implements JarEffect{
    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {

    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }
}
