package com.toadstoolstudios.lilwings.fabric;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

public class FabricLilWings implements ModInitializer {
    @Override
    public void onInitialize() {
        Milk.enableMilkFluid();
        Milk.enableCauldron();
        LilWings.init();
        LilWingsEntities.addSpawnPlacements();
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            FabricDefaultAttributeRegistry.register(butterfly.entityType().get(), Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, butterfly.maxHealth())
                    .add(Attributes.FLYING_SPEED, 1.0f));
        }

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_TAIGA), MobCategory.AMBIENT, LilWingsEntities.APONI_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), MobCategory.AMBIENT, LilWingsEntities.PAINTED_PANTHER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.SWAMP), MobCategory.AMBIENT, LilWingsEntities.SWAMP_HOPPER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.MUSHROOM), MobCategory.AMBIENT, LilWingsEntities.SHROOM_SKIPPER_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_FOREST), MobCategory.AMBIENT, LilWingsEntities.RED_APPLEFLY_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.HAS_VILLAGE_PLAINS), MobCategory.AMBIENT, LilWingsEntities.SWALLOW_TAIL_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SUNFLOWER_PLAINS), MobCategory.AMBIENT, LilWingsEntities.BUTTER_GOLD_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MEADOW), MobCategory.AMBIENT, LilWingsEntities.CLOUDY_PUFF_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST), MobCategory.AMBIENT, LilWingsEntities.CRYSTAL_PUFF_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.AMBIENT, LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get(), 25, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_PEAKS), MobCategory.AMBIENT, LilWingsEntities.WHITE_FOX_BUTTERFLY.entityType().get(), 25, 2, 2);

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.isShiftKeyDown()) {
                ItemStack bottleItem = player.getItemInHand(hand);
                if (bottleItem.is(Items.GLASS_BOTTLE)) {
                    Direction face = hitResult.getDirection();
                    BlockPos glassBottlePos = hitResult.getBlockPos();
                    if (!world.getBlockState(glassBottlePos).canBeReplaced(new DirectionalPlaceContext(world, hitResult.getBlockPos(), Direction.DOWN, ItemStack.EMPTY, Direction.UP))) {
                        if (face != null) {
                            glassBottlePos = glassBottlePos.relative(face);
                        }
                    }
                    if (!world.getBlockState(glassBottlePos).canBeReplaced(new DirectionalPlaceContext(world, glassBottlePos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)) && world.isLoaded(glassBottlePos))
                        return InteractionResult.PASS;
                    player.getLevel().setBlock(glassBottlePos, LilWingsBlocks.BUTTERFLY_JAR.get().defaultBlockState(), Block.UPDATE_ALL);
                    if (!player.isCreative()) bottleItem.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }
}
