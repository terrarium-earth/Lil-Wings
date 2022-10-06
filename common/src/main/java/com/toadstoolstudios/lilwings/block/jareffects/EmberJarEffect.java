package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class EmberJarEffect implements JarEffect {
    private final int cooldownTime = 100;//20 * 40; // Time in ticks
    private final int radius = 2; // Temp for `getBlockPosInArea`

    private int timeUntilTick = 100; // Initial delay in ticks

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        BlockPos jarPos = blockEntity.getBlockPos();
        boolean found = false;

        if (timeUntilTick == 0) {

            for (BlockPos pos : getBlockPosInArea(jarPos, radius)) {
                //* Get drops
                ServerLevel server = (ServerLevel) level;
                List<ItemStack> drops = Block.getDrops(level.getBlockState(pos), server, pos, level.getBlockEntity(pos));

                //* Iterate
                for (ItemStack drop : drops) {
                    for (SmeltingRecipe smeltingRecipe : server.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING)) {
                        for (Ingredient ingredient : smeltingRecipe.getIngredients()) {
                            for (ItemStack item : ingredient.getItems()) {
                                if (item.is(drop.getItem())) {
                                    //* drop cooked item
                                    ItemStack cookedItem = smeltingRecipe.getResultItem();
                                    level.destroyBlock(pos, false);
                                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), cookedItem));

                                    found = true;
                                }

                                if (found) break;
                            }

                            if (found) break;
                        }

                        if (found) break;
                    }

                    if (found) break;
                }

                if (found) break;
            }

            timeUntilTick = cooldownTime;
        } else {
            timeUntilTick--;
        }

    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }

    //    Temporary Method until area get fixed
    private List<BlockPos> getBlockPosInArea(BlockPos startingPos, int radius) {
        List<BlockPos> BlockPos = new ArrayList<>();

        BlockPos cornerPos = startingPos.relative(Direction.UP, radius).relative(Direction.SOUTH, radius).relative(Direction.WEST, radius);

        for (var X = 0; X < radius * 2 + 1; X++) {
            for (var Z = 0; Z < radius * 2 + 1; Z++) {
                for (var Y = 0; Y < radius * 2 + 1; Y++) {
                    BlockPos.add(cornerPos.relative(Direction.EAST, X).relative(Direction.DOWN, Y).relative(Direction.NORTH, Z));
                }
            }
        }

        return BlockPos;
    }
}
