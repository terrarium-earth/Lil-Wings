package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.platform.FabricRegistryHelper;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;


import static com.toadstoolstudios.lilwings.LilWings.MODID;

public class FabricLilWingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlas, registry) ->
                FabricRegistryHelper.TEXTURES.stream()
                        .map(id -> new Identifier(MODID, "particle/" + id))
                        .forEachOrdered(registry::register)
        );
        LilWingsClient.init();
        LilWingsClient.initParticleFactories();
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(player.isSneaking()) {
                ItemStack bottleItem = player.getStackInHand(hand);
                if (bottleItem.isOf(Items.GLASS_BOTTLE)) {
                    Direction face = hitResult.getSide();
                    BlockPos glassBottlePos = hitResult.getBlockPos();
                    if(!world.getBlockState(glassBottlePos).canReplace(new AutomaticItemPlacementContext(world, hitResult.getBlockPos(), Direction.DOWN, ItemStack.EMPTY, Direction.UP))) {
                        if(face != null) {
                            glassBottlePos = glassBottlePos.offset(face);
                        }
                    }
                    if(!world.getBlockState(glassBottlePos).canReplace(new AutomaticItemPlacementContext(world, glassBottlePos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)) && world.canSetBlock(glassBottlePos)) return ActionResult.PASS;
                    player.getWorld().setBlockState(glassBottlePos, LilWingsBlocks.BUTTERFLY_JAR.get().getDefaultState(), 3);
                    if(!player.isCreative()) bottleItem.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
