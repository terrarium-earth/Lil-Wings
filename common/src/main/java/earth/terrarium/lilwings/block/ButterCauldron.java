package earth.terrarium.lilwings.block;

import earth.terrarium.lilwings.registry.LilWingsBlocks;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ButterCauldron extends Block {

    public ButterCauldron() {
        super(Properties.copy(Blocks.CAULDRON));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        level.setBlock(blockPos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_CLIENTS);
        ItemStack butter = new ItemStack(LilWingsBlocks.BUTTER_BLOCK.get(), 1);
        player.getInventory().placeItemBackInInventory(butter, false);
        return InteractionResult.SUCCESS;
    }
}
