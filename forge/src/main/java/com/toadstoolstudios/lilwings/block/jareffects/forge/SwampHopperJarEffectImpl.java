package com.toadstoolstudios.lilwings.block.jareffects.forge;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SwampHopperJarEffectImpl {

    public static void handleItemInsertion(World level, BlockEntity container, Direction direction, ItemEntity entity) {
        container.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            ItemStack stack = ItemHandlerHelper.insertItem(iItemHandler, entity.getStack(), false);
            entity.setStack(stack);
        });
    }

    public static boolean isContainer(BlockEntity blockEntity, Direction direction) {
        return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent();
    }
}
