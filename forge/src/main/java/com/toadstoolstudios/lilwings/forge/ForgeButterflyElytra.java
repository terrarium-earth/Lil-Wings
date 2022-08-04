package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.item.IButterflyElytra;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ForgeButterflyElytra extends ElytraItem implements IButterflyElytra {
    private final ResourceLocation texture;

    public ForgeButterflyElytra(ResourceLocation texture) {
        super(new Properties().durability(432).rarity(Rarity.RARE).tab(LilWings.TAB));
        this.texture = texture;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
}
