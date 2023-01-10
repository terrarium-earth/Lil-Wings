package earth.terrarium.lilwings.block.jareffects;

import earth.terrarium.lilwings.block.ButterflyJarBlockEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SwampHopperJarEffect implements JarEffect {

    private int cooldown;
    private int containerTime;
    private BlockPos containerPos;
    private Direction containerDir;

    //TODO Make crossplatform
    @Override
    public void tickEffect(Level level, ButterflyJarBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (containerPos == null) {
            containerTime++;

            if (containerTime >= 4 * 20) {
                containerDir = findNearestContainer(level, blockEntity.getBlockPos());
                containerPos = containerDir != null ? blockEntity.getBlockPos().relative(containerDir) : null;
            }
        } else {
            cooldown++;

            if (cooldown >= 20) {
                ItemEntity entity = findNearestItem(level, blockEntity.getBlockPos());
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
    public static void handleItemInsertion(Level level, BlockEntity containerPos, Direction direction, ItemEntity entity) {
        throw new AssertionError();
    }

    public static ItemEntity findNearestItem(Level level, BlockPos jarPos) {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(jarPos).inflate(3));

        return entities.size() > 0 ? entities.get(0) : null;
    }

    public static Direction findNearestContainer(Level level, BlockPos jarPos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = jarPos.relative(direction);
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
    public ParticleOptions getParticleType() {
        return null;
    }
}
