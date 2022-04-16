package com.toadstoolstudios.lilwings.entity.jareffects.forge;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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

    public static Direction findNearestContainer(World level, BlockPos jarPos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = jarPos.offset(direction);
            BlockEntity blockEntity = level.getBlockEntity(offsetPos);
            if (blockEntity != null) {
                if (blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                    return direction;
                }
            }
        }
        return null;
    }
}
