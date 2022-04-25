package com.toadstoolstudios.lilwings;

import com.toadstoolstudios.lilwings.platform.CommonServices;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import com.toadstoolstudios.lilwings.registry.LilWingsParticles;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;

public class LilWings {

    public static final String MODID = "lilwings";
    public static ItemGroup TAB = CommonServices.REGISTRY.registerCreativeTab(new Identifier(LilWings.MODID, "itemgroup"), () -> new ItemStack(LilWingsItems.BUTTERFLY_NET.get()));

    public static void init() {
        //TODO Finish Item Entries for book
        //TODO Add recipes for the stuff
        //TODO Add achievements (I can't believe its not butter!) - make butter (Social butterfly) - catch all butterflies
        GeckoLib.initialize();
        LilWingsEntities.register();
        LilWingsItems.register();
        LilWingsBlocks.register();
        LilWingsParticles.register();
    }
}
