package com.toadstoolstudios.lilwings.item;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ButterflyNetItem extends Item {

    public ButterflyNetItem(int durability) {
        super(new Properties().tab(LilWings.TAB).durability(durability));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag tooltipFlag) {
        if (stack.getOrCreateTag().contains("butterfly")) {
            String butterflyName = Util.makeDescriptionId("entity", new ResourceLocation(stack.getOrCreateTag().getString("butterflyId")));
            list.add(Component.translatable("tooltip.butterfly_net.prefix").append(Component.translatable(butterflyName)).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) super.useOn(context);
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack item = context.getItemInHand();
        CompoundTag itemTag = item.getOrCreateTag();
        if (itemTag.contains("butterfly")) {
            if (level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
                if (blockEntity.getButterflyData() == null) {
                    ResourceLocation id = new ResourceLocation(itemTag.getString("butterflyId"));
                    EntityType<?> type = EntityType.byString(itemTag.getString("butterflyId")).get();

                    if (Butterfly.BUTTERFLIES.containsKey(id)) {
                        blockEntity.setEntityType((EntityType<? extends ButterflyEntity>) type);
                        blockEntity.setButterflyData(itemTag.getCompound("butterfly"));
                        level.setBlockAndUpdate(blockPos, blockState);
                        item.removeTagKey("butterfly");
                        item.removeTagKey("butterflyId");
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                EntityType<?> butterflyId = EntityType.byString(itemTag.getString("butterflyId")).get();
                ButterflyEntity butterfly = new ButterflyEntity((EntityType<? extends ButterflyEntity>) butterflyId, level);

                butterfly.load(itemTag.getCompound("butterfly"));
                butterfly.setCatchAmount(0);
                BlockPos pos = context.getClickedPos();
                butterfly.setPos(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f);

                if (butterfly.getButterfly().particleType() != null) {
                    level.addParticle(butterfly.getButterfly().particleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 0.5f, 0.5f, 0.5f);
                }
                level.addFreshEntity(butterfly);
                item.removeTagKey("butterfly");
                item.removeTagKey("butterflyId");
                return InteractionResult.SUCCESS;
            }
        } else if (level.getBlockEntity(blockPos) instanceof ButterflyJarBlockEntity blockEntity) {
            CompoundTag butterflyData = blockEntity.getButterflyData();
            if (butterflyData != null) {
                itemTag.put("butterfly", butterflyData);
                itemTag.putString("butterflyId", EntityType.getKey(blockEntity.getEntityType()).toString());
                blockEntity.setEntityType(null);
                blockEntity.setButterflyData(null);
                blockEntity.removeButterfly();
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }
}
