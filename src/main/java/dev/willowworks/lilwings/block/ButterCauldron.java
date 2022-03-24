package dev.willowworks.lilwings.block;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ButterCauldron extends Block {

    public ButterCauldron() {
        super(BlockBehaviour.Properties.copy(Blocks.CAULDRON));
    }



    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), 2);
        ItemStack butter = new ItemStack(LilWingsBlocks.BUTTER_BLOCK.get(), 1);
        if(!pPlayer.getInventory().add(butter)) {
            pPlayer.drop(butter, false);
        }
        return InteractionResult.SUCCESS;
    }
}
