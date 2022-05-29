package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class FabricLilWings implements ModInitializer {
    @Override
    public void onInitialize() {
        Milk.enableMilkFluid();
        Milk.enableCauldron();
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

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(player.isSneaking()) {
                ItemStack bottleItem = player.getStackInHand(hand);
                if (bottleItem.isOf(Items.GLASS_BOTTLE)) {
                    Direction face = hitResult.getSide();
                    BlockPos glassBottlePos = hitResult.getBlockPos();
                    if(!world.getBlockState(glassBottlePos).canReplace(new AutomaticItemPlacementContext(world, hitResult.getBlockPos(), Direction.DOWN, ItemStack.EMPTY, Direction.UP))) {
                        if(face != null) {
                            glassBottlePos = glassBottlePos.offset(face);
                        }
                    }
                    if(!world.getBlockState(glassBottlePos).canReplace(new AutomaticItemPlacementContext(world, glassBottlePos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)) && world.canSetBlock(glassBottlePos)) return ActionResult.PASS;
                    player.getWorld().setBlockState(glassBottlePos, LilWingsBlocks.BUTTERFLY_JAR.get().getDefaultState(), 3);
                    if(!player.isCreative()) bottleItem.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
