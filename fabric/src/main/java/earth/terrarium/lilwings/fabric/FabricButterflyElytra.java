package earth.terrarium.lilwings.fabric;

import earth.terrarium.lilwings.LilWings;
import earth.terrarium.lilwings.item.IButterflyElytra;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class FabricButterflyElytra extends ElytraItem implements FabricElytraItem, IButterflyElytra, EquipmentSlotProvider {
    private final ResourceLocation texture;

    public FabricButterflyElytra(ResourceLocation texture) {
        super(new FabricItemSettings().maxDamage(432).rarity(Rarity.RARE).group(LilWings.TAB));
        this.texture = texture;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        ItemStack itemStack2 = user.getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack2.isEmpty()) {
            user.setItemSlot(EquipmentSlot.CHEST, itemStack.copy());
            itemStack.setCount(0);
            return InteractionResultHolder.success(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }
}
