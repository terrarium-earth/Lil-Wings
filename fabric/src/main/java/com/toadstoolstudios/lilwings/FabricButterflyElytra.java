package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.item.IButterflyElytra;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FabricButterflyElytra extends ElytraItem implements FabricElytraItem, IButterflyElytra, EquipmentSlotProvider {
    private final Identifier texture;

    public FabricButterflyElytra(Identifier texture) {
        super(new FabricItemSettings().maxDamage(432).rarity(Rarity.RARE).group(LilWings.TAB));
        this.texture = texture;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ItemStack itemStack2 = user.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack2.isEmpty()) {
            user.equipStack(EquipmentSlot.CHEST, itemStack.copy());
            itemStack.setCount(0);
            return TypedActionResult.success(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }

    @Override
    public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }
}
