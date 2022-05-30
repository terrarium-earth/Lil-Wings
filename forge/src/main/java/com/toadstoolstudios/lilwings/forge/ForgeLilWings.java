package com.toadstoolstudios.lilwings.forge;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.compat.TOPCompat;
import com.toadstoolstudios.lilwings.forge.platform.ForgeRegistryHelper;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import net.minecraft.block.Block;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Map;
import java.util.function.Supplier;

@Mod(LilWings.MODID)
public class ForgeLilWings {
    public static final Supplier<Block> MILK_CAULDRON = ForgeRegistryHelper.BLOCKS.register("milk_cauldron", MilkCauldron::new);
    public static final Map<Item, CauldronBehavior> MILK_INTERACTION = CauldronBehavior.createMap();
    public static final CauldronBehavior FILL_MILK = (level, blockPos, player, hand, stack, state) ->
            CauldronBehavior.fillCauldron(blockPos, player, hand, stack, state, MILK_CAULDRON.get().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);


    public ForgeLilWings() {
        LilWings.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::init);
        bus.addListener(this::initClient);
        bus.addListener(this::onComplete);
        bus.addListener(this::attributeEvent);
        bus.addListener(ForgeLilWingsClient::addLayers);
        bus.addListener(ForgeLilWingsClient::addLayerDefinitons);
        bus.addListener(ForgeLilWingsClient::particleEvent);

        ForgeRegistryHelper.ENTITY_TYPES.register(bus);
        ForgeRegistryHelper.BLOCKS.register(bus);
        ForgeRegistryHelper.BLOCK_ENTITIES.register(bus);
        ForgeRegistryHelper.ITEMS.register(bus);
        ForgeRegistryHelper.SOUNDS.register(bus);
        ForgeRegistryHelper.PARTICLE_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::imcEvent);
    }

    public void attributeEvent(EntityAttributeCreationEvent event) {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            event.put(butterfly.entityType().get(), MobEntity.createMobAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, butterfly.maxHealth())
                    .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
                    .build()
            );
        }
    }

    public void init(FMLCommonSetupEvent event) {
        //TODO Needs spawn rules
        CauldronBehavior.registerBucketBehavior(MILK_INTERACTION);
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_MILK);
        MILK_INTERACTION.put(Items.MILK_BUCKET, FILL_MILK);
        MinecraftForge.EVENT_BUS.addListener((PlayerInteractEvent.RightClickBlock interactEvent) -> {
            if(interactEvent.getPlayer().isSneaking()) {
                if (interactEvent.getItemStack().isOf(Items.GLASS_BOTTLE)) {
                    Direction face = interactEvent.getFace();
                    BlockPos glassBottlePos = interactEvent.getPos();
                    if(face != null) {
                        glassBottlePos = glassBottlePos.offset(face);
                    }
                    if(!interactEvent.getPlayer().getWorld().getBlockState(glassBottlePos).isAir()) return;
                    interactEvent.getPlayer().getWorld().setBlockState(glassBottlePos, LilWingsBlocks.BUTTERFLY_JAR.get().getDefaultState(), 3);
                    if(!interactEvent.getPlayer().isCreative()) interactEvent.getItemStack().decrement(1);
                    interactEvent.setCanceled(true);
                    interactEvent.setCancellationResult(ActionResult.SUCCESS);
                }
            }
        });
    }

    public void initClient(FMLClientSetupEvent event) {
        ForgeLilWingsClient.init();
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        LilWingsEntities.addSpawnPlacements();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addButterflies(BiomeLoadingEvent event) {

        switch (event.getCategory()) {
            case TAIGA -> addButterfly(event, LilWingsEntities.APONI_BUTTERFLY);
            case JUNGLE -> addButterfly(event, LilWingsEntities.PAINTED_PANTHER_BUTTERFLY);
            case SWAMP -> addButterfly(event, LilWingsEntities.SWAMP_HOPPER_BUTTERFLY);
            case MUSHROOM -> addButterfly(event, LilWingsEntities.SHROOM_SKIPPER_BUTTERFLY);
            case FOREST -> addButterfly(event, LilWingsEntities.RED_APPLEFLY_BUTTERFLY);
            default -> {
                if(event.getName() != null) {
                    switch (event.getName().getPath()) {
                        case "sunflower_plains" -> addButterfly(event, LilWingsEntities.BUTTER_GOLD_BUTTERFLY);
                        case "meadow" -> addButterfly(event, LilWingsEntities.CLOUDY_PUFF_BUTTERFLY);
                        case "flower_forest" -> addButterfly(event, LilWingsEntities.CRYSTAL_PUFF_BUTTERFLY);
                        //case "end_highland" -> addButterfly(event, LilWingsEntities.ENDER_WING_BUTTERFLY);
                        case "lush_caves" -> addButterfly(event, LilWingsEntities.GRAYLING_BUTTERFLY);
                        case "frozen_peaks" -> addButterfly(event, LilWingsEntities.WHITE_FOX_BUTTERFLY);
                    }
                }
            }
        }
    }

    private void addButterfly(BiomeLoadingEvent event, Butterfly butterfly) {
        event.getSpawns().spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(butterfly.entityType().get(),25, 2, 2));
    }

    private void imcEvent(InterModEnqueueEvent event) {
        if(ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPCompat::new);
        }
    }
}
