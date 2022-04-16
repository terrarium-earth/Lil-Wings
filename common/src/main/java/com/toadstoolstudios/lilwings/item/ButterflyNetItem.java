package com.toadstoolstudios.lilwings.item;

import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButterflyNetItem extends Item {

    public ButterflyNetItem(int durability) {
        super(new Settings().group(LilWingsItems.TAB).maxDamage(durability));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext pContext) {
        if(pContext.getWorld().isClient) super.useOnBlock(pContext);
        BlockPos blockPos = pContext.getBlockPos();
        World level = pContext.getWorld();
        BlockState blockState = level.getBlockState(blockPos);
        NbtCompound itemTag = pContext.getStack().getOrCreateNbt();
        if(itemTag.contains("butterfly")) {
            if(level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
                if(blockEntity.getButterflyData() == null) {
                    Identifier id = new Identifier(itemTag.getString("butterflyId"));
                    EntityType<?> type = EntityType.get(itemTag.getString("butterflyId")).get();

                    if (Butterfly.BUTTERFLIES.containsKey(id)) {
                        blockEntity.setEntityType((EntityType<? extends ButterflyEntity>) type);
                        blockEntity.setButterflyData(itemTag.getCompound("butterfly"));
                        level.setBlockState(blockPos, blockState);
                        pContext.getStack().removeSubNbt("butterfly");
                        pContext.getStack().removeSubNbt("butterflyId");
                        return ActionResult.SUCCESS;
                    }
                }
            } else {
                EntityType<? extends ButterflyEntity> butterflyId = (EntityType<? extends ButterflyEntity>) EntityType.get(pContext.getStack().getNbt().getString("butterflyId")).get();
                ButterflyEntity butterfly = new ButterflyEntity(butterflyId, level);

                butterfly.readNbt(pContext.getStack().getNbt().getCompound("butterfly"));
                butterfly.setCatchAmount(0);
                BlockPos pos = pContext.getBlockPos();
                butterfly.setPosition(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f);

                if (butterfly.getButterfly().particleType() != null) {
                    level.addParticle(butterfly.getButterfly().particleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 0.5f, 0.5f, 0.5f);
                }
                level.spawnEntity(butterfly);
                pContext.getStack().removeSubNbt("butterfly");
                pContext.getStack().removeSubNbt("butterflyId");
                return ActionResult.SUCCESS;
            }
        } else if(level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
            NbtCompound butterflyData = blockEntity.getButterflyData();
            if(butterflyData != null) {
                itemTag.put("butterfly", butterflyData);
                itemTag.putString("butterflyId", blockEntity.getEntityType().getLootTableId().toString());
                blockEntity.setEntityType(null);
                blockEntity.setButterflyData(null);

                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(pContext);
    }
}
