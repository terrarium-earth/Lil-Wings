package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.item.ButterflyNetItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static CreativeModeTab TAB = new CreativeModeTab("lilwings") {
        @Override
        public ItemStack makeIcon() {
            return BUTTERFLY_NET.get().getDefaultInstance();
        }
    };

    public static final RegistryObject<Item> BUTTERFLY_NET = ITEMS.register("butterfly_net", () -> new ButterflyNetItem(16));
    public static final RegistryObject<Item> ENDERFLY_NET = ITEMS.register("enderfly_net", () -> new ButterflyNetItem(32));
}
