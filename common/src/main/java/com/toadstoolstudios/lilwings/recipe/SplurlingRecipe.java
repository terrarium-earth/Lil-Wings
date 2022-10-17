package com.toadstoolstudios.lilwings.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.toadstoolstudios.lilwings.registry.LilWingsRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record SplurlingRecipe(ResourceLocation id, Ingredient input, ItemStack output) implements CodecRecipe<Container> {

    public static Codec<SplurlingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("input").forGetter(SplurlingRecipe::input),
                ItemStackCodec.CODEC.fieldOf("result").forGetter(SplurlingRecipe::output)
        ).apply(instance, SplurlingRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LilWingsRecipes.SPLURLING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return LilWingsRecipes.SPLURLING_RECIPE.get();
    }
}
