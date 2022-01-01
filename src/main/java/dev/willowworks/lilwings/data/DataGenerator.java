package dev.willowworks.lilwings.data;

import dev.willowworks.lilwings.LilWings;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LilWings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            event.getGenerator().addProvider(new ButterflyItems(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
