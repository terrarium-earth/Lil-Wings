package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.item.IButterflyElytra;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class FabricButterflyElytra extends ElytraItem implements FabricElytraItem, IButterflyElytra {
    private final Identifier texture;

    public FabricButterflyElytra(Identifier texture) {
        super(new Settings().maxDamage(432).rarity(Rarity.RARE).group(LilWingsItems.TAB));
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
