package com.toadstoolstudios.lilwings.entity.jareffects.fabric;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SwampHopperJarEffectImpl {

    public static void handleItemInsertion(World level, BlockEntity blockEntity, Direction direction, ItemEntity entity) {
        ItemStack toInsert = entity.getStack();
        var storage = ItemStorage.SIDED.find(level, blockEntity.getPos(), blockEntity.getCachedState(), blockEntity, direction.getOpposite());
        var itemVariant = ItemVariant.of(toInsert);
        try (Transaction txn = Transaction.openOuter()) {

            int inserted = (int) storage.insert(itemVariant, toInsert.getCount(), txn);

            txn.commit();

            if (inserted == toInsert.getCount()) {
                entity.setStack(ItemStack.EMPTY);
            } else {
                var ret = toInsert.copy();
                ret.setCount(toInsert.getCount() - inserted);
                entity.setStack(ret);
            }
        }
    }

    public static boolean isContainer(BlockEntity blockEntity, Direction direction) {
        return ItemStorage.SIDED.find(blockEntity.getWorld(), blockEntity.getPos(), blockEntity.getCachedState(), blockEntity, direction) != null;
    }

}
