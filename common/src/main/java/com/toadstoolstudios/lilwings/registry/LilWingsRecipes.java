package com.toadstoolstudios.lilwings.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.toadstoolstudios.lilwings.recipe.SplurlingRecipe;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class LilWingsRecipes {

    public static final Supplier<RecipeType<SplurlingRecipe>> SPLURLING_RECIPE = registerRecipeType("splurling",
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "splurling";
                }
            });

    public static final Supplier<RecipeSerializer<SplurlingRecipe>> SPLURLING_SERIALIZER = registerRecipeSerializer("splurling",
            () -> new CodecRecipeSerializer<>(SPLURLING_RECIPE.get(), SplurlingRecipe::codec));

    public static void register() {}

    @ExpectPlatform
    public static <R extends Recipe<?>, T extends RecipeType<R>> Supplier<T> registerRecipeType(String name, Supplier<T> recipe) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <R extends Recipe<?>, T extends RecipeSerializer<R>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> recipe) {
        throw new NotImplementedException();
    }
}
