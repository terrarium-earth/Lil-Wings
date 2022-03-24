package dev.willowworks.lilwings;

import dev.willowworks.lilwings.client.ModClient;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.LilWingsEntities;
import dev.willowworks.lilwings.registry.LilWingsItems;
import dev.willowworks.lilwings.registry.LilWingsParticles;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(LilWings.MODID)
public class LilWings {

    public static final String MODID = "lilwings";

    public LilWings() {
        GeckoLib.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        bus.addListener(this::initClient);
        bus.addListener(LilWingsEntities::attributeEvent);
        bus.addListener(ModClient::addLayers);
        bus.addListener(ModClient::particleEvent);
        LilWingsEntities.ENTITIES.register(bus);

        LilWingsEntities.ITEMS.register(bus);
        LilWingsItems.ITEMS.register(bus);

        LilWingsBlocks.BLOCKS.register(bus);
        LilWingsBlocks.ITEMS.register(bus);
        LilWingsBlocks.TILES.register(bus);
        LilWingsParticles.PARTICLES.register(bus);
        ForgeMod.enableMilkFluid();
    }

    public void init(FMLCommonSetupEvent event) {
        CauldronInteraction.addDefaultInteractions(LilWingsItems.MILK_INTERACTION);
        CauldronInteraction.EMPTY.put(Items.MILK_BUCKET, LilWingsItems.FILL_MILK);
        LilWingsItems.MILK_INTERACTION.put(Items.MILK_BUCKET, LilWingsItems.FILL_MILK);
    }

    public void initClient(FMLClientSetupEvent event) {
        ModClient.init();
    }
}
