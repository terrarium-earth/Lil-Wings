package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LilWingsBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LilWings.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LilWings.MODID);

    public static final RegistryObject<Block> BUTTERFLY_JAR = BLOCKS.register("butterfly_jar", ButterflyJarBlock::new);
    //public static final RegistryObject<Item> BUTTERFLY_JAR_ITEM = ITEMS.register("butterfly_jar", () -> new ButterflyJarItem(BUTTERFLY_JAR.get()));
    public static final RegistryObject<BlockEntityType<ButterflyJarBlockEntity>> BUTTERFLY_JAR_ENTITY = TILES.register("butterfly_jar_tile", () ->
            BlockEntityType.Builder.create(ButterflyJarBlockEntity::new, BUTTERFLY_JAR.get()).build(null)
    );

    public static final RegistryObject<Block> BUTTERNITE_BLOCK = BLOCKS.register("butternite_block", () -> new Block(AbstractBlock.Settings.of(Material.STONE).strength(1.0f)));
    public static final RegistryObject<Item> BUTTERNITE_BLOCK_ITEM = ITEMS.register("butternite_block", () -> new BlockItem(BUTTERNITE_BLOCK.get(), new Item.Settings().group(LilWingsItems.TAB)));

    public static final RegistryObject<Block> BUTTER_BLOCK = BLOCKS.register("butter_block", () -> new Block(AbstractBlock.Settings.of(Material.STONE).strength(0.8f)));
    public static final RegistryObject<Item> BUTTER_BLOCK_ITEM = ITEMS.register("butter_block", () -> new BlockItem(BUTTER_BLOCK.get(), new Item.Settings().group(LilWingsItems.TAB)));
    //TODO make butter slippery

    public static final RegistryObject<Block> CRIMSON_CAKE = BLOCKS.register("crimson_cake", () -> new CakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
    public static final RegistryObject<Item> CRIMSON_CAKE_ITEM = ITEMS.register("crimson_cake", () -> new BlockItem(CRIMSON_CAKE.get(), new Item.Settings().group(LilWingsItems.TAB)));

    public static final RegistryObject<Block> GOLDEN_CAKE = BLOCKS.register("golden_cake", () -> new CakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
    public static final RegistryObject<Item> GOLDEN_CAKE_ITEM = ITEMS.register("golden_cake", () -> new BlockItem(GOLDEN_CAKE.get(), new Item.Settings().group(LilWingsItems.TAB)));

    public static final RegistryObject<Block> MILK_CAULDRON = BLOCKS.register("milk_cauldron", MilkCauldron::new);
    public static final RegistryObject<Block> BUTTER_CAULDRON = BLOCKS.register("butter_cauldron", ButterCauldron::new);
}
