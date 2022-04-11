package dev.willowworks.lilwings.item;

import dev.willowworks.lilwings.registry.LilWingsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class ButterflyElytra extends ElytraItem {

    private final Identifier texture;

    public ButterflyElytra(Identifier texture) {
        super(new Settings().maxDamage(432).rarity(Rarity.RARE).group(LilWingsItems.TAB));
        this.texture = texture;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return super.getArmorTexture(stack, entity, slot, type);
    }

    public Identifier getTexture() {
        return texture;
    }
}
