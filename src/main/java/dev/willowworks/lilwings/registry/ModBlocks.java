package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.block.ButterflyJarBlock;
import dev.willowworks.lilwings.block.ButterflyJarBlockEntity;
import dev.willowworks.lilwings.block.ButterflyJarItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LilWings.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LilWings.MODID);

    public static RegistryObject<Block> BUTTERFLY_JAR = BLOCKS.register("butterfly_jar", ButterflyJarBlock::new);
    public static RegistryObject<Item> BUTTERFLY_JAR_ITEM = ITEMS.register("butterfly_jar", () -> new ButterflyJarItem(BUTTERFLY_JAR.get()));
    public static RegistryObject<BlockEntityType<ButterflyJarBlockEntity>> BUTTERFLY_JAR_ENTITY = TILES.register("butterfly_jar_tile", () ->
            BlockEntityType.Builder.of(ButterflyJarBlockEntity::new, BUTTERFLY_JAR.get()).build(null)
    );
}
