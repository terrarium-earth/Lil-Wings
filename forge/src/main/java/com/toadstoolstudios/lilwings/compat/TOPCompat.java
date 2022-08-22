package com.toadstoolstudios.lilwings.compat;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlock;
import com.toadstoolstudios.lilwings.block.ButterflyJarBlockEntity;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.function.Function;

public class TOPCompat implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(new IProbeInfoProvider() {
            @Override
            public Identifier getID() {
                return new Identifier(LilWings.MODID, "default");
            }

            @Override
            public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData probeHitData) {
                if(blockState.getBlock() instanceof ButterflyJarBlock) {
                    BlockEntity blockEntity = world.getBlockEntity(probeHitData.getPos());
                    if(blockEntity instanceof ButterflyJarBlockEntity jarBlockEntity && jarBlockEntity.getEntityType() != null) {
                        probeInfo.horizontal().text(" ").entity((jarBlockEntity.getOrCreateEntity(world))).vertical().text(" ").horizontal().text(jarBlockEntity.getEntityType().getTranslationKey()).text(" ");
                    }
                }
            }
        });
        return null;
    }
}
