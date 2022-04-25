package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.item.IButterflyElytra;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class ForgeButterflyElytra extends ElytraItem implements IButterflyElytra {
    private final Identifier texture;

    public ForgeButterflyElytra(Identifier texture) {
        super(new Settings().maxDamage(432).rarity(Rarity.RARE).group(LilWings.TAB));
        this.texture = texture;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
