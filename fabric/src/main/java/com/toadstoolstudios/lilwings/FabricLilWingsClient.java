package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.client.entity.ButterflyElytraLayer;
import com.toadstoolstudios.lilwings.platform.FabricRegistryHelper;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer.getModel() instanceof PlayerEntityModel || entityRenderer.getModel() instanceof BipedEntityModel || entityRenderer.getModel() instanceof ArmorStandEntityModel) {
                registrationHelper.register(new ButterflyElytraLayer<>(entityRenderer, MinecraftClient.getInstance().getEntityModelLoader()));
            }
        });
    }
}
