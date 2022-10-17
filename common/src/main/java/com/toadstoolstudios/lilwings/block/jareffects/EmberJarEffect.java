package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class EmberJarEffect implements JarEffect {
    private final int cooldownTime = 20 * 40; // Time in ticks
    private final int radius = 2; // Temp for `getBlockPosInArea`

    private int timeUntilTick = 100; // Initial delay in ticks

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        ServerLevel server = (ServerLevel) level;
        BlockPos jarPos = blockEntity.getBlockPos();
        final boolean[] found = {false};

        if (timeUntilTick == 0) {
            for (BlockPos pos : getBlockPosInArea(jarPos, radius)) {
                List<ItemStack> drops = Block.getDrops(level.getBlockState(pos), server, pos, level.getBlockEntity(pos));

                for (ItemStack drop : drops) {
                    server.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING).stream().filter(smeltingRecipe ->
                            smeltingRecipe.getIngredients().stream().anyMatch(ingredient ->
                                    ingredient.test(drop))).findAny().ifPresent(ingredient -> {

                        ItemStack cookedItem = ingredient.getResultItem();
                        cookedItem.setCount(drop.getCount());

                        level.destroyBlock(pos, false);
                        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), cookedItem));

                        found[0] = true;
                    });

                    if (found[0]) break;
                }

                if (found[0]) break;
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
