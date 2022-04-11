package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.item.ButterflyNetItem;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class LilWingsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static ItemGroup TAB = new ItemGroup("lilwings") {
        @Override
        public ItemStack createIcon() {
            return BUTTERFLY_NET.get().getDefaultStack();
        }
    };

    public static final Map<Item, CauldronBehavior> MILK_INTERACTION = CauldronBehavior.createMap();
    public static final CauldronBehavior FILL_MILK = (level, blockPos, player, hand, stack, state) ->
            CauldronBehavior.fillCauldron(blockPos, player, hand, stack, state, LilWingsBlocks.MILK_CAULDRON.get().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);

    public static final RegistryObject<Item> BUTTERFLY_NET = ITEMS.register("butterfly_net", () -> new ButterflyNetItem(16));
    public static final RegistryObject<Item> ENDERFLY_NET = ITEMS.register("enderfly_net", () -> new ButterflyNetItem(32));

    public static final RegistryObject<Item> BUTTERNITE = ITEMS.register("butternite", () -> new Item(new Item.Settings().group(TAB)));
    public static final RegistryObject<Item> ENDER_STRING = ITEMS.register("ender_string", () -> new Item(new Item.Settings().group(TAB)));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Settings().group(TAB)));
    public static final RegistryObject<Item> GILDED_BUTTER = ITEMS.register("gilded_butter", () -> new Item(new Item.Settings().group(TAB)));

    public static final RegistryObject<Item> LANTERN_ON_A_STICK = ITEMS.register("lantern_on_a_stick", () -> new Item(new Item.Settings().group(TAB)));
    public static final RegistryObject<Item> CRIMSON_COCOA_BEANS = ITEMS.register("crimson_cocoa_beans", () -> new Item(new Item.Settings().group(TAB)));
    public static final RegistryObject<Item> SWAMP_MEAL = ITEMS.register("swamp_meal", () -> new BoneMealItem(new Item.Settings().group(TAB)));
}
