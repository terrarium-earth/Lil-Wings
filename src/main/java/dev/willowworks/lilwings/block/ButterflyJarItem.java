package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.client.item.JarItemRenderer;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.registry.ModItems;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ButterflyJarItem extends BlockItem {

    public ButterflyJarItem(Block pBlock) {
        super(pBlock, new Item.Properties().tab(ModItems.TAB));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean flag = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!level.isClientSide()) {
            CompoundTag tag = stack.getOrCreateTag();

            if (tag.contains("butterflyId", Tag.TAG_STRING) && level.getBlockEntity(pos) instanceof ButterflyJarBlockEntity blockEntity) {
                ResourceLocation id = new ResourceLocation(tag.getString("butterflyId"));
                EntityType<?> type = ForgeRegistries.ENTITIES.getValue(id);

                if (Butterfly.BUTTERFLIES.containsKey(id)) {
                    blockEntity.setEntityType((EntityType<? extends ButterflyEntity>) type);
                    blockEntity.setButterflyData(tag.getCompound("butterfly"));
                }
            }
        }

        return flag;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IItemRenderProperties() {
            final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> JarItemRenderer.RENDERER);

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer.get();
            }
        });
    }
}
