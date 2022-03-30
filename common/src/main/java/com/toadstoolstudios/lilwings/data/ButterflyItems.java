package com.toadstoolstudios.lilwings.data;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ButterflyItems extends ItemModelProvider {

    public static ResourceLocation SPAWN_EGG_PARENT = new ResourceLocation("item/template_spawn_egg");
    public static ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");

    public ButterflyItems(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LilWings.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            if (butterfly.spawnEggItem() != null)
                withExistingParent(butterfly.spawnEggItem().get().getRegistryName().toString(), SPAWN_EGG_PARENT);

            if (butterfly.wings().length > 0) {
                for (RegistryObject<Item> wing : butterfly.wings()) {
                    withExistingParent(wing.get().getRegistryName().toString(), ITEM_GENERATED)
                            .texture("layer0", new ResourceLocation(LilWings.MODID, "item/" + wing.get().getRegistryName().getPath()));
                }
            }

            if (butterfly.elytras().length > 0) {
                for (RegistryObject<Item> elytra : butterfly.elytras()) {
                    withExistingParent(elytra.get().getRegistryName().toString(), ITEM_GENERATED)
                            .texture("layer0", new ResourceLocation(LilWings.MODID, "item/" + elytra.get().getRegistryName().getPath()));
                }
            }
        }
    }
}
