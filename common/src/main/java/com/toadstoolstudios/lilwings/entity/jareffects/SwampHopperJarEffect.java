package com.toadstoolstudios.lilwings.entity.jareffects;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class SwampHopperJarEffect implements JarEffect {

    private int cooldown;
    private int containerTime;
    private BlockPos containerPos;

    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (containerPos == null) {
            containerTime++;

            if (containerTime >= 4 * 20) {
                containerPos = findNearestContainer(level, blockEntity.getBlockPos());
            }
        } else {
            cooldown++;

            if (cooldown >= 20) {
                ItemEntity entity = findNearestItem(level, blockEntity.getBlockPos());
                if (entity != null) {
                    BlockEntity containerEntity = level.getBlockEntity(containerPos);
                    if (containerEntity != null) {
                        containerEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                            ItemStack stack = ItemHandlerHelper.insertItem(iItemHandler, entity.getItem(), false);
                            entity.setItem(stack);
                        });
                    } else {
                        containerPos = null;
                    }
                }

                cooldown = 0;
            }
        }
    }

    public ItemEntity findNearestItem(Level level, BlockPos jarPos) {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(jarPos).inflate(3));

        return entities.size() > 0 ? entities.get(0) : null;
    }

    public BlockPos findNearestContainer(Level level, BlockPos jarPos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = jarPos.relative(direction);
            if (level.getBlockEntity(offsetPos) != null) {
                BlockEntity blockEntity = level.getBlockEntity(offsetPos);

                if (blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                    return offsetPos;
                }
            }
        }

        return null;
    }

    @Override
    public ParticleOptions getParticleType() {
        return null;
    }
}
