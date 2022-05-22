package com.toadstoolstudios.lilwings.block.jareffects;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class SwampHopperJarEffect implements JarEffect {

    private int cooldown;
    private int containerTime;
    private BlockPos containerPos;
    private Direction containerDir;

    //TODO Make crossplatform
    @Override
    public void tickEffect(World level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClient()) return;

        if (containerPos == null) {
            containerTime++;

            if (containerTime >= 4 * 20) {
                containerDir = findNearestContainer(level, blockEntity.getPos());
                containerPos = containerDir != null ? blockEntity.getPos().offset(containerDir) : null;
            }
        } else {
            cooldown++;

            if (cooldown >= 20) {
                ItemEntity entity = findNearestItem(level, blockEntity.getPos());
                if (entity != null) {
                    BlockEntity containerEntity = level.getBlockEntity(containerPos);
                    if (containerEntity != null) {
                        handleItemInsertion(level, containerEntity, containerDir, entity);
                    } else {
                        containerPos = null;
                    }
                }

                cooldown = 0;
            }
        }
    }

    @ExpectPlatform
    public static void handleItemInsertion(World level, BlockEntity containerPos, Direction direction, ItemEntity entity) {
        throw new AssertionError();
    }

    public static ItemEntity findNearestItem(World level, BlockPos jarPos) {
        List<ItemEntity> entities = level.getNonSpectatingEntities(ItemEntity.class, new Box(jarPos).expand(3));

        return entities.size() > 0 ? entities.get(0) : null;
    }

    public static Direction findNearestContainer(World level, BlockPos jarPos) {
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = jarPos.offset(direction);
                BlockEntity blockEntity = level.getBlockEntity(offsetPos);
                if (blockEntity != null) {
                    if (isContainer(blockEntity, direction.getOpposite())) {
                        return direction;
                    }
                }
            }
            return null;
    }

    @ExpectPlatform
    public static boolean isContainer(BlockEntity blockEntity, Direction direction) {
        throw new AssertionError();
    }

    @Override
    public ParticleEffect getParticleType() {
        return null;
    }
}
