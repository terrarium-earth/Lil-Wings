package com.toadstoolstudios.lilwings.entity.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class SwampHopperJarEffect implements JarEffect {

    private int cooldown;
    private int containerTime;
    private BlockPos containerPos;

    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;

        if (containerPos == null) {
            containerTime++;

            if (containerTime >= 4 * 20) {
                containerPos = findNearestContainer(level, blockEntity.getPos());
            }
        } else {
            cooldown++;

            if (cooldown >= 20) {
                ItemEntity entity = findNearestItem(level, blockEntity.getPos());
                if (entity != null) {
                    BlockEntity containerEntity = level.getBlockEntity(containerPos);
                    if (containerEntity != null) {
                        containerEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                            ItemStack stack = ItemHandlerHelper.insertItem(iItemHandler, entity.getStack(), false);
                            entity.setStack(stack);
                        });
                    } else {
                        containerPos = null;
                    }
                }

                cooldown = 0;
            }
        }
    }

    public ItemEntity findNearestItem(World level, BlockPos jarPos) {
        List<ItemEntity> entities = level.getNonSpectatingEntities(ItemEntity.class, new Box(jarPos).expand(3));

        return entities.size() > 0 ? entities.get(0) : null;
    }

    public BlockPos findNearestContainer(World level, BlockPos jarPos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = jarPos.offset(direction);
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
    public ParticleEffect getParticleType() {
        return null;
    }
}
