package com.toadstoolstudios.lilwings.block.jareffects.forge;

import com.toadstoolstudios.lilwings.forge.ForgeLilWings;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButterGoldJarEffectImpl {

    public static final TagKey<Block> CAULDRON_TAG = BlockTags.create(new Identifier("forge", "milk_cauldron"));

    public static boolean isMilkCauldron(World level, BlockPos bPos) {
        return level.getBlockState(bPos).isIn(CAULDRON_TAG);
    }
}
