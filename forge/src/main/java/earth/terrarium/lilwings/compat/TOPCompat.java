package earth.terrarium.lilwings.compat;

import earth.terrarium.lilwings.LilWings;
import earth.terrarium.lilwings.block.ButterflyJarBlock;
import earth.terrarium.lilwings.block.ButterflyJarBlockEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class TOPCompat implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(new IProbeInfoProvider() {
            @Override
            public ResourceLocation getID() {
                return new ResourceLocation(LilWings.MODID, "default");
            }

            @Override
            public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player playerEntity, Level world, BlockState blockState, IProbeHitData probeHitData) {
                if (blockState.getBlock() instanceof ButterflyJarBlock) {
                    BlockEntity blockEntity = world.getBlockEntity(probeHitData.getPos());
                    if (blockEntity instanceof ButterflyJarBlockEntity jarBlockEntity && jarBlockEntity.getEntityType() != null) {
                        probeInfo.horizontal().text(" ").entity((jarBlockEntity.getOrCreateEntity(world, jarBlockEntity.getEntityType(), jarBlockEntity.getButterflyData()))).vertical().text(" ").horizontal().text(jarBlockEntity.getEntityType().getDescription()).text(" ");
                    }
                }
            }
        });
        return null;
    }
}
