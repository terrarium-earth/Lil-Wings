package earth.terrarium.lilwings.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(method = "isModelPartShown", at = @At("HEAD"), cancellable = true)
    private void onIsModelPartShown(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (modelPart.equals(PlayerModelPart.CAPE)) {
            cir.setReturnValue(!(this.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem));
        }
    }
}
