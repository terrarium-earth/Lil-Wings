package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

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
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.TAIGA), SpawnGroup.AMBIENT, LilWingsEntities.APONI_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.JUNGLE), SpawnGroup.AMBIENT, LilWingsEntities.PAINTED_PANTHER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.SWAMP), SpawnGroup.AMBIENT, LilWingsEntities.SWAMP_HOPPER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.MUSHROOM), SpawnGroup.AMBIENT, LilWingsEntities.SHROOM_SKIPPER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST), SpawnGroup.AMBIENT, LilWingsEntities.RED_APPLEFLY_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE), SpawnGroup.AMBIENT, LilWingsEntities.SWALLOW_TAIL_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey() == BiomeKeys.SUNFLOWER_PLAINS, SpawnGroup.AMBIENT, LilWingsEntities.BUTTER_GOLD_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey() == BiomeKeys.MEADOW, SpawnGroup.AMBIENT, LilWingsEntities.CLOUDY_PUFF_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey() == BiomeKeys.FLOWER_FOREST, SpawnGroup.AMBIENT, LilWingsEntities.CRYSTAL_PUFF_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey() == BiomeKeys.LUSH_CAVES, SpawnGroup.AMBIENT, LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey() == BiomeKeys.FROZEN_PEAKS, SpawnGroup.AMBIENT, LilWingsEntities.WHITE_FOX_BUTTERFLY.entityType().get(), 25, 2, 2);
    }
}
