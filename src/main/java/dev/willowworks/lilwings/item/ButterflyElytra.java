package dev.willowworks.lilwings.item;

import dev.willowworks.lilwings.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ButterflyElytra extends ElytraItem {

    private final ResourceLocation texture;

    public ButterflyElytra(ResourceLocation texture) {
        super(new Properties().durability(432).rarity(Rarity.RARE).tab(ModItems.TAB));
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

    public ResourceLocation getTexture() {
        return texture;
    }
}
