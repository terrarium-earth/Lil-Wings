package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.item.ButterflyNetItem;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static CreativeModeTab TAB = new CreativeModeTab("lilwings") {
        @Override
        public ItemStack makeIcon() {
            return BUTTERFLY_NET.get().getDefaultInstance();
        }
    };

    public static final Map<Item, CauldronInteraction> MILK_INTERACTION = CauldronInteraction.newInteractionMap();
    public static final CauldronInteraction FILL_MILK = (level, blockPos, player, hand, stack, state) ->
            CauldronInteraction.emptyBucket(blockPos, player, hand, stack, state, ModBlocks.MILK_CAULDRON.get().defaultBlockState(), SoundEvents.BUCKET_EMPTY);

    public static final RegistryObject<Item> BUTTERFLY_NET = ITEMS.register("butterfly_net", () -> new ButterflyNetItem(16));
    public static final RegistryObject<Item> ENDERFLY_NET = ITEMS.register("enderfly_net", () -> new ButterflyNetItem(32));

    public static final RegistryObject<Item> BUTTERNITE = ITEMS.register("butternite", () -> new Item(new Item.Properties().tab(TAB)));
    public static final RegistryObject<Item> ENDER_STRING = ITEMS.register("ender_string", () -> new Item(new Item.Properties().tab(TAB)));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().tab(TAB)));
    public static final RegistryObject<Item> GILDED_BUTTER = ITEMS.register("gilded_butter", () -> new Item(new Item.Properties().tab(TAB)));

    public static final RegistryObject<Item> LANTERN_ON_A_STICK = ITEMS.register("lantern_on_a_stick", () -> new Item(new Item.Properties().tab(TAB)));
    public static final RegistryObject<Item> CRIMSON_COCOA_BEANS = ITEMS.register("crimson_cocoa_beans", () -> new Item(new Item.Properties().tab(TAB)));
    public static final RegistryObject<Item> SWAMP_MEAL = ITEMS.register("swamp_meal", () -> new BoneMealItem(new Item.Properties().tab(TAB)));
}
