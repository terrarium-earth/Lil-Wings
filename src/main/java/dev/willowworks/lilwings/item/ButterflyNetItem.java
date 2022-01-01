package dev.willowworks.lilwings.item;

import dev.willowworks.lilwings.registry.ModItems;
import net.minecraft.world.item.Item;

public class ButterflyNetItem extends Item {

    public ButterflyNetItem(int durability) {
        super(new Item.Properties().tab(ModItems.TAB).durability(durability));
    }
}
