package com.toadstoolstudios.lilwings.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.toadstoolstudios.lilwings.recipe.ExampleRecipe;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class LilWingsRecipes {

    public static final Supplier<RecipeType<ExampleRecipe>> EXAMPLE_RECIPE = registerRecipeType("example", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "example";
        }
    });

    public static final Supplier<RecipeSerializer<ExampleRecipe>> EXAMPLE_SERIALIZER = registerRecipeSerializer("example", () -> new CodecRecipeSerializer<>(EXAMPLE_RECIPE.get(), ExampleRecipe::codec));

    @ExpectPlatform
    public static <R extends Recipe<?>, T extends RecipeType<R>> Supplier<T> registerRecipeType(String name, Supplier<T> recipe) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <R extends Recipe<?>, T extends RecipeSerializer<R>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> recipe) {
        throw new NotImplementedException();
    }
}
