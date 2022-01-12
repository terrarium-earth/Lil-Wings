package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class MilkCauldron extends AbstractCauldronBlock {

    public MilkCauldron() {
        super(BlockBehaviour.Properties.copy(Blocks.CAULDRON), ModItems.MILK_INTERACTION);
        //this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override
    public double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }
}
