package com.toadstoolstudios.lilwings.item;

import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ButterflyElytra extends ElytraItem {

    private final Identifier texture;

    public ButterflyElytra(Identifier texture) {
        super(new Item.Settings().maxCount(432).rarity(Rarity.RARE).group(LilWingsItems.TAB));
        this.texture = texture;
    }

    public Identifier getTexture() {
        return texture;
    }
}
