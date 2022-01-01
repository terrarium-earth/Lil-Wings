package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.ModBlocks;
import dev.willowworks.lilwings.registry.entity.Butterfly;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ButterflyJarBlockEntity extends BlockEntity {

    private EntityType<? extends ButterflyEntity> entityType;
    private CompoundTag butterflyData;

    public ButterflyJarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlocks.BUTTERFLY_JAR_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        super.save(tag);
        saveAdditional(tag);

        return tag;
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
            }
        }

        if (tag.contains("butterfly")) {
            this.butterflyData = tag.getCompound("butterfly");
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ButterflyJarBlockEntity pBlockEntity) {
    }

    public void setEntityType(EntityType<? extends ButterflyEntity> entityType) {
        this.entityType = entityType;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void setButterflyData(CompoundTag tag) {
        this.butterflyData = tag;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public EntityType<? extends ButterflyEntity> getEntityType() {
        return entityType;
    }

    public CompoundTag getButterflyData() {
        return butterflyData;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
