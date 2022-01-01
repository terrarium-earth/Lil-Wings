package dev.willowworks.lilwings;

import dev.willowworks.lilwings.client.ModClient;
import dev.willowworks.lilwings.registry.ModBlocks;
import dev.willowworks.lilwings.registry.ModEntities;
import dev.willowworks.lilwings.registry.ModItems;
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
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModEntities.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public void init(FMLCommonSetupEvent event) {
    }

    public void initClient(FMLClientSetupEvent event) {
        ModClient.init();
    }
}
