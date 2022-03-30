package dev.willowworks.lilwings.item;

import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.LilWingsItems;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ButterflyNetItem extends Item {

    public ButterflyNetItem(int durability) {
        super(new Item.Properties().tab(LilWingsItems.TAB).durability(durability));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide) super.useOn(pContext);
        BlockPos blockPos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        CompoundTag itemTag = pContext.getItemInHand().getOrCreateTag();
        if(itemTag.contains("butterfly")) {
            if(level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
                if(blockEntity.getButterflyData() == null) {
                    ResourceLocation id = new ResourceLocation(itemTag.getString("butterflyId"));
                    EntityType<?> type = ForgeRegistries.ENTITIES.getValue(id);

                    if (Butterfly.BUTTERFLIES.containsKey(id)) {
                        blockEntity.setEntityType((EntityType<? extends ButterflyEntity>) type);
                        blockEntity.setButterflyData(itemTag.getCompound("butterfly"));
                        level.setBlockAndUpdate(blockPos, blockState);
                        pContext.getItemInHand().removeTagKey("butterfly");
                        pContext.getItemInHand().removeTagKey("butterflyId");
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                EntityType<? extends ButterflyEntity> butterflyId = (EntityType<? extends ButterflyEntity>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(pContext.getItemInHand().getTag().getString("butterflyId")));
                ButterflyEntity butterfly = new ButterflyEntity(butterflyId, level);

                butterfly.load(pContext.getItemInHand().getTag().getCompound("butterfly"));
                butterfly.setCatchAmount(0);
                BlockPos pos = pContext.getClickedPos();
                butterfly.setPos(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f);

                if (butterfly.getButterfly().particleType() != null) {
                    level.addParticle(butterfly.getButterfly().particleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 0.5f, 0.5f, 0.5f);
                }
                level.addFreshEntity(butterfly);
                pContext.getItemInHand().removeTagKey("butterfly");
                pContext.getItemInHand().removeTagKey("butterflyId");
                return InteractionResult.SUCCESS;
            }
        } else if(level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
            CompoundTag butterflyData = blockEntity.getButterflyData();
            if(butterflyData != null) {
                itemTag.put("butterfly", butterflyData);
                itemTag.putString("butterflyId", blockEntity.getEntityType().getRegistryName().toString());
                blockEntity.setEntityType(null);
                blockEntity.setButterflyData(null);

                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(pContext);
    }
}
