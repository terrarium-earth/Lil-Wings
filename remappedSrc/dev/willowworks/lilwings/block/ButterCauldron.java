package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ButterCauldron extends Block {

    public ButterCauldron() {
        super(AbstractBlock.Settings.copy(Blocks.CAULDRON));
    }



    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockView world, BlockPos pos, PlayerEntity player) {
        return Items.CAULDRON.getDefaultStack();
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        pLevel.setBlockState(pPos, Blocks.CAULDRON.getDefaultState(), 2);
        ItemStack butter = new ItemStack(LilWingsBlocks.BUTTER_BLOCK.get(), 1);
        if(!pPlayer.getInventory().insertStack(butter)) {
            pPlayer.dropItem(butter, false);
        }
        return ActionResult.SUCCESS;
    }
}
