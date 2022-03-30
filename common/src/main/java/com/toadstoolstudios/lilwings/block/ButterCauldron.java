package com.toadstoolstudios.lilwings.block;

import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ButterCauldron extends AbstractCauldronBlock {

    public ButterCauldron() {
        super(AbstractBlock.Settings.copy(Blocks.CAULDRON), LilWingsItems.MILK_INTERACTION);
    }



    @Override
    public boolean isFull(BlockState state) {
        return true;
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return Items.CAULDRON.getDefaultStack();
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        pLevel.setBlockState(pPos, Blocks.CAULDRON.getDefaultState(), 2);
        ItemStack butter = new ItemStack(LilWingsBlocks.BUTTER_BLOCK.get(), 1);
        pPlayer.getInventory().offerOrDrop(butter);
        return ActionResult.SUCCESS;
    }

}

