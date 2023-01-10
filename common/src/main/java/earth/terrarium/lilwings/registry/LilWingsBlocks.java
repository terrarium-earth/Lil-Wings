package earth.terrarium.lilwings.registry;

import earth.terrarium.lilwings.LilWings;
import earth.terrarium.lilwings.platform.CommonServices;
import earth.terrarium.lilwings.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class LilWingsBlocks {


    public static final Supplier<Block> BUTTERFLY_JAR = CommonServices.REGISTRY.registerBlock("butterfly_jar", ButterflyJarBlock::new);
    //public static final RegistryObject<Item> BUTTERFLY_JAR_ITEM = CommonServices.REGISTRY.registerItem("butterfly_jar", () -> new ButterflyJarItem(BUTTERFLY_JAR.get()));
    public static final Supplier<BlockEntityType<ButterflyJarBlockEntity>> BUTTERFLY_JAR_ENTITY = CommonServices.REGISTRY.registerBlockEntity("butterfly_jar_tile", () -> CommonServices.REGISTRY.createBlockEntityType(ButterflyJarBlockEntity::new, BUTTERFLY_JAR.get()));

    public static final Supplier<Block> BUTTERNITE_BLOCK = CommonServices.REGISTRY.registerBlock("butternite_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(4.0f)));
    public static final Supplier<Item> BUTTERNITE_BLOCK_ITEM = CommonServices.REGISTRY.registerItem("butternite_block", () -> new BlockItem(BUTTERNITE_BLOCK.get(), new Item.Properties().tab(LilWings.TAB)));

    public static final Supplier<Block> BUTTER_BLOCK = CommonServices.REGISTRY.registerBlock("butter_block", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.HONEY_BLOCK).strength(0.5f).friction(.98f)));
    public static final Supplier<Item> BUTTER_BLOCK_ITEM = CommonServices.REGISTRY.registerItem("butter_block", () -> new BlockItem(BUTTER_BLOCK.get(), new Item.Properties().tab(LilWings.TAB)));
    //TODO make butter slippery

    public static final Supplier<Block> CRIMSON_CAKE = CommonServices.REGISTRY.registerBlock("crimson_cake", () -> new BaseCakeBlock(3, 0.5F) {
    });
    public static final Supplier<Item> CRIMSON_CAKE_ITEM = CommonServices.REGISTRY.registerItem("crimson_cake", () -> new BlockItem(CRIMSON_CAKE.get(), new Item.Properties().tab(LilWings.TAB)));

    public static final Supplier<Block> GOLDEN_CAKE = CommonServices.REGISTRY.registerBlock("golden_cake", () -> new GoldenCakeBlock(4, 0.3F) {
    });
    public static final Supplier<Item> GOLDEN_CAKE_ITEM = CommonServices.REGISTRY.registerItem("golden_cake", () -> new BlockItem(GOLDEN_CAKE.get(), new Item.Properties().tab(LilWings.TAB)));

    public static final Supplier<Block> BUTTER_CAULDRON = CommonServices.REGISTRY.registerBlock("butter_cauldron", ButterCauldron::new);

    public static void register() {
    }
}
