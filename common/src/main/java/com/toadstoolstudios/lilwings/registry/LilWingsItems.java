package com.toadstoolstudios.lilwings.registry;

import com.toadstoolstudios.lilwings.item.ButterflyNetItem;
import com.toadstoolstudios.lilwings.platform.CommonServices;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

import java.util.Map;
import java.util.function.Supplier;

public class LilWingsItems {

    public static ItemGroup TAB = new ItemGroup(3, "lilwings") {
        //TODO FIX ITEMGROUP
        @Override
        public ItemStack createIcon() {
            return BUTTERFLY_NET.get().getDefaultStack();
        }
    };


    public static final Map<Item, CauldronBehavior> MILK_INTERACTION = CauldronBehavior.createMap();
    public static final CauldronBehavior FILL_MILK = (level, blockPos, player, hand, stack, state) ->
            CauldronBehavior.fillCauldron(blockPos, player, hand, stack, state, LilWingsBlocks.MILK_CAULDRON.get().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);

    public static final Supplier<Item> BUTTERFLY_NET = CommonServices.REGISTRY.registerItem("butterfly_net", () -> new ButterflyNetItem(16));
    public static final Supplier<Item> ENDERFLY_NET = CommonServices.REGISTRY.registerItem("enderfly_net", () -> new ButterflyNetItem(32));

    public static final Supplier<Item> BUTTERNITE = CommonServices.REGISTRY.registerItem("butternite", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> ENDER_STRING = CommonServices.REGISTRY.registerItem("ender_string", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> BUTTER = CommonServices.REGISTRY.registerItem("butter", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> GILDED_BUTTER = CommonServices.REGISTRY.registerItem("gilded_butter", () -> new Item(new Item.Settings().group(TAB)));

    public static final Supplier<Item> LANTERN_ON_A_STICK = CommonServices.REGISTRY.registerItem("lantern_on_a_stick", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> CRIMSON_COCOA_BEANS = CommonServices.REGISTRY.registerItem("crimson_cocoa_beans", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> SWAMP_MEAL = CommonServices.REGISTRY.registerItem("swamp_meal", () -> new BoneMealItem(new Item.Settings().group(TAB)));
}
