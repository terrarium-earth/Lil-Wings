package com.toadstoolstudios.lilwings.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MilkCauldron extends AbstractCauldronBlock {

    public MilkCauldron() {
        super(Properties.copy(Blocks.CAULDRON), ForgeLilWings.MILK_INTERACTION);
        //this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override
    protected double getContentHeight(BlockState arg) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter arg, BlockPos arg2, BlockState arg3) {
        return Items.CAULDRON.getDefaultInstance();
    }
}
