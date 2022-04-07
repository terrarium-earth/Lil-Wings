package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.entity.jareffects.JarEffect;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ButterflyJarBlockEntity extends BlockEntity {

    private EntityType<? extends ButterflyEntity> entityType;
    private CompoundTag butterflyData;
    private JarEffect jarEffect;
    private GraylingType colorType;

    public ButterflyJarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (entityType != null)
            tag.putString("entityId", entityType.getRegistryName().toString());

        if (butterflyData != null)
            tag.put("butterfly", butterflyData);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("entityId", Tag.TAG_STRING)) {
            ResourceLocation id = new ResourceLocation(tag.getString("entityId"));

            if (Butterfly.BUTTERFLIES.containsKey(id)) {
                entityType = (EntityType<? extends ButterflyEntity>) ForgeRegistries.ENTITIES.getValue(id);

                Butterfly butterfly = Butterfly.BUTTERFLIES.get(entityType.getRegistryName());
                if (jarEffect == null && butterfly.jarEffect() != null)
                    jarEffect = butterfly.jarEffect().get();
            }
        }

        if (tag.contains("butterfly")) {
            this.butterflyData = tag.getCompound("butterfly");
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ButterflyJarBlockEntity blockEntity) {
        if (blockEntity.getJarEffect() != null)
            blockEntity.getJarEffect().tickEffect(level, blockEntity);
    }

    public void setEntityType(EntityType<? extends ButterflyEntity> entityType) {
        if(entityType != null) {
            Butterfly butterfly = Butterfly.BUTTERFLIES.get(entityType.getRegistryName());
            this.entityType = entityType;
            if (butterfly.jarEffect() != null)
                this.jarEffect = butterfly.jarEffect().get();
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        } else {
            this.entityType = null;
            this.jarEffect = null;
        }
    }

    public void setButterflyData(CompoundTag tag) {
        this.butterflyData = tag;
        if (tag != null && tag.contains("colorType")) {
            colorType = GraylingType.valueOf(tag.getString("colorType"));
        }

        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public EntityType<? extends ButterflyEntity> getEntityType() {
        return entityType;
    }

    public CompoundTag getButterflyData() {
        return butterflyData;
    }

    public JarEffect getJarEffect() {
        return jarEffect;
    }

    public GraylingType getColorType() {
        return colorType;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }
}
