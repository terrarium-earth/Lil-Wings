package dev.willowworks.lilwings;

import dev.willowworks.lilwings.client.ModClient;
import dev.willowworks.lilwings.registry.ModBlocks;
import dev.willowworks.lilwings.registry.ModEntities;
import dev.willowworks.lilwings.registry.ModItems;
import dev.willowworks.lilwings.registry.ModParticles;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LilWings.MODID)
public class LilWings {

    public static final String MODID = "lilwings";

    public LilWings() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEntities::attributeEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModClient::addLayers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModClient::particleEvent);
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModEntities.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModParticles.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ForgeMod.enableMilkFluid();
    }

    public void init(FMLCommonSetupEvent event) {
        CauldronInteraction.addDefaultInteractions(ModItems.MILK_INTERACTION);
        CauldronInteraction.EMPTY.put(Items.MILK_BUCKET, ModItems.FILL_MILK);
        ModItems.MILK_INTERACTION.put(Items.MILK_BUCKET, ModItems.FILL_MILK);
    }

    public void initClient(FMLClientSetupEvent event) {
        ModClient.init();
    }
}
