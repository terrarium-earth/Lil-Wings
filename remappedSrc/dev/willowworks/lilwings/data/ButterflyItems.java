package dev.willowworks.lilwings.data;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ButterflyItems extends ItemModelProvider {

    public static Identifier SPAWN_EGG_PARENT = new Identifier("item/template_spawn_egg");
    public static Identifier ITEM_GENERATED = new Identifier("item/generated");

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
                            .texture("layer0", new Identifier(LilWings.MODID, "item/" + wing.get().getRegistryName().getPath()));
                }
            }

            if (butterfly.elytras().length > 0) {
                for (RegistryObject<Item> elytra : butterfly.elytras()) {
                    withExistingParent(elytra.get().getRegistryName().toString(), ITEM_GENERATED)
                            .texture("layer0", new Identifier(LilWings.MODID, "item/" + elytra.get().getRegistryName().getPath()));
                }
            }
        }
    }
}
