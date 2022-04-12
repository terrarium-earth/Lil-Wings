package com.toadstoolstudios.lilwings.block;

import dev.willowworks.lilwings.registry.LilWingsItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MilkCauldron extends AbstractCauldronBlock {

    public MilkCauldron() {
        super(Settings.copy(Blocks.CAULDRON), LilWingsItems.MILK_INTERACTION);
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
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockView world, BlockPos pos, PlayerEntity player) {
        return Items.CAULDRON.getDefaultStack();
    }


}
