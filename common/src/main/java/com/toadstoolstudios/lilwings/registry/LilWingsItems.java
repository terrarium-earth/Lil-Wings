package com.toadstoolstudios.lilwings.registry;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.item.ButterflyNetItem;
import com.toadstoolstudios.lilwings.platform.CommonServices;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Supplier;

import static com.toadstoolstudios.lilwings.LilWings.TAB;

public class LilWingsItems {

    public static final Supplier<Item> BUTTERFLY_NET = CommonServices.REGISTRY.registerItem("butterfly_net", () -> new ButterflyNetItem(16));
    public static final Supplier<Item> ENDERFLY_NET = CommonServices.REGISTRY.registerItem("enderfly_net", () -> new ButterflyNetItem(32));

    public static final Supplier<Item> BUTTERNITE = CommonServices.REGISTRY.registerItem("butternite", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> ENDER_STRING = CommonServices.REGISTRY.registerItem("ender_string", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> BUTTER = CommonServices.REGISTRY.registerItem("butter", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> PAPER_WINGS = CommonServices.REGISTRY.registerItem("paper_wings", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> COTTON_BALL = CommonServices.REGISTRY.registerItem("cotton_ball", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> GILDED_BUTTER = CommonServices.REGISTRY.registerItem("gilded_butter", () -> new Item(new Item.Settings().group(TAB)));

    public static final Supplier<Item> LANTERN_ON_A_STICK = CommonServices.REGISTRY.registerItem("lantern_on_a_stick", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> CRIMSON_COCOA_BEANS = CommonServices.REGISTRY.registerItem("crimson_cocoa_beans", () -> new Item(new Item.Settings().group(TAB)));
    public static final Supplier<Item> SWAMP_MEAL = CommonServices.REGISTRY.registerItem("swamp_meal", () -> new BoneMealItem(new Item.Settings().group(TAB)));

    public static void register() {}
}
