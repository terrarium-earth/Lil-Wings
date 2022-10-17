package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import com.toadstoolstudios.lilwings.compat.TOPCompat;
import com.toadstoolstudios.lilwings.forge.platform.ForgeRegistryHelper;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.LilWingsRecipes;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import com.toadstoolstudios.lilwings.registry.forge.LilWingsRecipesImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;
import java.util.function.Supplier;

@Mod(LilWings.MODID)
public class ForgeLilWings {
    public static final Supplier<Block> MILK_CAULDRON = ForgeRegistryHelper.BLOCKS.register("milk_cauldron", MilkCauldron::new);
    public static final Map<Item, CauldronInteraction> MILK_INTERACTION = CauldronInteraction.newInteractionMap();
    public static final CauldronInteraction FILL_MILK = (level, blockPos, player, hand, stack, state) ->
            CauldronInteraction.emptyBucket(blockPos, player, hand, stack, state, MILK_CAULDRON.get().defaultBlockState(), SoundEvents.BUCKET_EMPTY);


    public ForgeLilWings() {
        LilWings.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::init);
        bus.addListener(this::onComplete);
        bus.addListener(this::attributeEvent);
        ForgeRegistryHelper.ENTITY_TYPES.register(bus);
        ForgeRegistryHelper.BLOCKS.register(bus);
        ForgeRegistryHelper.BLOCK_ENTITIES.register(bus);
        ForgeRegistryHelper.ITEMS.register(bus);
        ForgeRegistryHelper.SOUNDS.register(bus);
        ForgeRegistryHelper.PARTICLE_TYPES.register(bus);
        LilWingsRecipesImpl.RECIPES.register(bus);
        LilWingsRecipesImpl.SERIALIZERS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::imcEvent);
    }

    public void attributeEvent(EntityAttributeCreationEvent event) {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            event.put(butterfly.entityType().get(), Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, butterfly.maxHealth())
                    .add(Attributes.FLYING_SPEED, 1.0f)
                    .build()
            );
        }
    }

    public void init(FMLCommonSetupEvent event) {
        //TODO Needs spawn rules
        CauldronInteraction.addDefaultInteractions(MILK_INTERACTION);
        CauldronInteraction.EMPTY.put(Items.MILK_BUCKET, FILL_MILK);
        MILK_INTERACTION.put(Items.MILK_BUCKET, FILL_MILK);
        MinecraftForge.EVENT_BUS.addListener((PlayerInteractEvent.RightClickBlock interactEvent) -> {
            Player player = interactEvent.getEntity();
            if (player.isShiftKeyDown()) {
                if (interactEvent.getItemStack().is(Items.GLASS_BOTTLE)) {
                    Direction face = interactEvent.getFace();
                    BlockPos glassBottlePos = interactEvent.getPos();
                    if (face != null) {
                        glassBottlePos = glassBottlePos.relative(face);
                    }
                    if (!player.getLevel().getBlockState(glassBottlePos).isAir()) return;
                    player.getLevel().setBlock(glassBottlePos, LilWingsBlocks.BUTTERFLY_JAR.get().defaultBlockState(), 3);
                    if (!player.isCreative()) interactEvent.getItemStack().shrink(1);
                    interactEvent.setCanceled(true);
                    interactEvent.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        });
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        LilWingsEntities.addSpawnPlacements();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> PatreonManager::init);
    }

    private void imcEvent(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPCompat::new);
        }
    }
}
