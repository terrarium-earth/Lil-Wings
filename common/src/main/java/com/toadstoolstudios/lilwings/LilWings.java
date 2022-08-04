package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.platform.CommonServices;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.GeckoLib;

public class LilWings {

    public static final String MODID = "lilwings";
    public static CreativeModeTab TAB = CommonServices.REGISTRY.registerCreativeTab(new ResourceLocation(LilWings.MODID, "itemgroup"), () -> new ItemStack(LilWingsItems.BUTTERFLY_NET.get()));

    public static void init() {
        GeckoLib.initialize();
        LilWingsEntities.register();
        LilWingsItems.register();
        LilWingsBlocks.register();
        LilWingsParticles.register();
    }
}
