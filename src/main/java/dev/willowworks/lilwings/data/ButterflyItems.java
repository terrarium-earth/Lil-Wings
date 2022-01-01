package dev.willowworks.lilwings.data;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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
                withExistingParent(butterfly.spawnEggItem().getRegistryName().toString(), SPAWN_EGG_PARENT);

            if (butterfly.wings() != null)
                withExistingParent(butterfly.wings().getRegistryName().toString(), ITEM_GENERATED)
                        .texture("layer0", new ResourceLocation(LilWings.MODID, "item/" + butterfly.textureName() + "_wings"));
        }
    }
}
