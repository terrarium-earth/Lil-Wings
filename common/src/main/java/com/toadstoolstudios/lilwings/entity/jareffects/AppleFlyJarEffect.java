package com.toadstoolstudios.lilwings.entity.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class AppleFlyJarEffect implements JarEffect{
    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if(blockEntity.getWorld().getBlockState(blockEntity.getPos().down()).isOf(Blocks.GOLD_BLOCK)) {
            if((int) (Math.random() * 100) == 1) {
                blockEntity.setEntityType(LilWingsEntities.GOLD_APPLEFLY_BUTTERFLY.entityType().get());
            }
        }
    }

    @Override
    public ParticleEffect getParticleType() {
        return null;
    }
}
