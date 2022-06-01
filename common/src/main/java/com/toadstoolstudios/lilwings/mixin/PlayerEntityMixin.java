package com.toadstoolstudios.lilwings.mixin;

import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "isPartVisible", at = @At("HEAD"), cancellable = true)
    private void onIsModelPartShown(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if(modelPart.equals(PlayerModelPart.CAPE)) {
            cir.setReturnValue(!(this.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem));
        }
    }
}
