package earth.terrarium.lilwings.block.jareffects.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SwampHopperJarEffectImpl {

    public static void handleItemInsertion(Level level, BlockEntity container, Direction direction, ItemEntity entity) {
        container.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            ItemStack stack = ItemHandlerHelper.insertItem(iItemHandler, entity.getItem(), false);
            entity.setItem(stack);
        });
    }

    public static boolean isContainer(BlockEntity blockEntity, Direction direction) {
        return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent();
    }
}
