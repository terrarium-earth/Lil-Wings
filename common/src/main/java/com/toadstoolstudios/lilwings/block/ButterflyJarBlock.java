package com.toadstoolstudios.lilwings.block;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ButterflyJarBlock extends BlockWithEntity {

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(3, 12, 3, 13, 14, 13),
            Block.createCuboidShape(2, 0, 2, 14, 10, 14),
            Block.createCuboidShape(4, 10, 4, 12, 12, 12),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public ButterflyJarBlock() {
        super(Settings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(0.5f).nonOpaque());
    }

    @Override
    public void afterBreak(World level, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntityIn, ItemStack tool) {
        if (!level.isClient() && blockEntityIn instanceof ButterflyJarBlockEntity blockEntity) {
            ButterflyEntity butterfly = blockEntity.getEntityType().create(level);
            if (butterfly != null) {
                ServerWorld serverLevel = (ServerWorld) level;

                butterfly.readNbt(blockEntity.getButterflyData());
                butterfly.setCatchAmount(0);
                butterfly.setPosition(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);

                if (butterfly.getButterfly().particleType() != null) {
                    serverLevel.spawnParticles(butterfly.getButterfly().particleType(), pos.getX() + 0.5, pos.getY() + 0.08f, pos.getZ() + 0.5, 25, 0, 0, 0, 0.5f);
                }
                level.spawnEntity(butterfly);
            }
        }

        super.afterBreak(level, player, pos, state, blockEntityIn, tool);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return checkType(pBlockEntityType, LilWingsBlocks.BUTTERFLY_JAR_ENTITY.get(), ButterflyJarBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState pState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState pState, BlockView pLevel, BlockPos pPos, ShapeContext pContext) {
        return SHAPE;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return Items.GLASS_BOTTLE.getDefaultStack();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new ButterflyJarBlockEntity(pPos, pState);
    }
}
