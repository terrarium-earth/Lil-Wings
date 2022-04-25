package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class FabricLilWings implements ModInitializer {
    @Override
    public void onInitialize() {
        LilWings.init();
        LilWingsEntities.addSpawnPlacements();
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            FabricDefaultAttributeRegistry.register(butterfly.entityType().get(), MobEntity.createMobAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, butterfly.maxHealth())
                    .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f));
        }
    }
}
