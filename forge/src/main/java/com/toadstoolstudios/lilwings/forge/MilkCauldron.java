package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MilkCauldron extends AbstractCauldronBlock {

    public MilkCauldron() {
        super(Settings.copy(Blocks.CAULDRON), ForgeLilWings.MILK_INTERACTION);
        //this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override
    public double getFluidHeight(BlockState state) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return Items.CAULDRON.getDefaultStack();
    }


}
