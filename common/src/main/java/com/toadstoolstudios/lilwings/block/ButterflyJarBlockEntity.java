package com.toadstoolstudios.lilwings.block;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.entity.jareffects.JarEffect;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import com.toadstoolstudios.lilwings.registry.entity.GraylingType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButterflyJarBlockEntity extends BlockEntity {

    private EntityType<? extends ButterflyEntity> entityType;
    private NbtCompound butterflyData;
    private JarEffect jarEffect;
    private GraylingType colorType;

    public ButterflyJarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (entityType != null)
            nbt.putString("entityId", EntityType.getId(entityType).toString());

        if (butterflyData != null)
            nbt.put("butterfly", butterflyData);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.contains("entityId", NbtInt.STRING_TYPE)) {
            Identifier id = new Identifier(nbt.getString("entityId"));

            if (Butterfly.BUTTERFLIES.containsKey(id)) {
                entityType = (EntityType<? extends ButterflyEntity>) EntityType.get(id.toString()).get();

                Butterfly butterfly = Butterfly.BUTTERFLIES.get(EntityType.getId(entityType));
                if (jarEffect == null && butterfly.jarEffect() != null)
                    jarEffect = butterfly.jarEffect().get();
            }
        }

        if (nbt.contains("butterfly")) {
            this.butterflyData = nbt.getCompound("butterfly");
        }
    }

    public static void tick(World level, BlockPos pos, BlockState state, ButterflyJarBlockEntity blockEntity) {
        if (blockEntity.getJarEffect() != null)
            blockEntity.getJarEffect().tickEffect(level, blockEntity);
    }

    public void setEntityType(EntityType<? extends ButterflyEntity> entityType) {
        if(entityType != null) {
            Butterfly butterfly = Butterfly.BUTTERFLIES.get(EntityType.getId(entityType));
            this.entityType = entityType;
            if (butterfly.jarEffect() != null)
                this.jarEffect = butterfly.jarEffect().get();
            markDirty();
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        } else {
            this.entityType = null;
            this.jarEffect = null;
        }
    }

    public void setButterflyData(NbtCompound tag) {
        this.butterflyData = tag;
        if (tag != null && tag.contains("colorType")) {
            colorType = GraylingType.valueOf(tag.getString("colorType"));
        }

        markDirty();
        world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }

    public EntityType<? extends ButterflyEntity> getEntityType() {
        return entityType;
    }

    public NbtCompound getButterflyData() {
        return butterflyData;
    }

    public JarEffect getJarEffect() {
        return jarEffect;
    }

    public GraylingType getColorType() {
        return colorType;
    }



}
