package com.toadstoolstudios.lilwings.block.jareffects.fabric;

import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.impl.transfer.fluid.CauldronStorage;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButterGoldJarEffectImpl {
    public static boolean isMilkCauldron(World level, BlockPos bPos) {
        CauldronFluidContent content = CauldronFluidContent.getForBlock(level.getBlockState(bPos).getBlock());
        if (content == null)
            return false;
        return content.fluid.isIn(Milk.MILK_FLUID_TAG);
    }
}
