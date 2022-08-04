package com.toadstoolstudios.lilwings.block.jareffects.fabric;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;


@SuppressWarnings("UnstableApiUsage")
public class SwampHopperJarEffectImpl {

    public static void handleItemInsertion(Level level, BlockEntity blockEntity, Direction direction, ItemEntity entity) {
        ItemStack toInsert = entity.getItem();
        var storage = ItemStorage.SIDED.find(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, direction.getOpposite());
        var itemVariant = ItemVariant.of(toInsert);
        try (Transaction txn = Transaction.openOuter()) {
            if (storage != null) {
                int inserted = (int) storage.insert(itemVariant, toInsert.getCount(), txn);

                txn.commit();

                if (inserted == toInsert.getCount()) {
                    entity.setItem(ItemStack.EMPTY);
                } else {
                    var ret = toInsert.copy();
                    ret.setCount(toInsert.getCount() - inserted);
                    entity.setItem(ret);
                }
            }
        }
    }

    public static boolean isContainer(BlockEntity blockEntity, Direction direction) {
        return ItemStorage.SIDED.find(blockEntity.getLevel(), blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, direction) != null;
    }

}
