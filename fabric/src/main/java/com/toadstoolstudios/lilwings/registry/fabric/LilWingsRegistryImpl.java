package com.toadstoolstudios.lilwings.registry.fabric;

import com.toadstoolstudios.lilwings.FabricButterflyElytra;
import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.platform.services.IRegistryHelper;
import com.toadstoolstudios.lilwings.registry.SpawnData;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class LilWingsRegistryImpl {
    public static final Set<String> TEXTURES = new HashSet<>();

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), item.get());
        return () -> registry;
    }

    public static <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> registry;
    }

    public static Supplier<ElytraItem> registerElytra(String id, Identifier texture) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), new FabricButterflyElytra(texture));
        return () -> registry;
    }

    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        var registry = Registry.register(Registry.BLOCK, new Identifier(LilWings.MODID, id), block.get());
        return () -> registry;
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        var registry = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(LilWings.MODID, id), blockEntity.get());
        return () -> registry;
    }

    public static <T extends DefaultParticleType> Supplier<T> registerParticleType(String name, Supplier<T> particle) {
        TEXTURES.add(name);
        var registry = Registry.register(Registry.PARTICLE_TYPE, new Identifier(LilWings.MODID, name), particle.get());
        return () -> registry;
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(IRegistryHelper.BlockEntityFactory<E> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }

    public static  <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        var object = Registry.register(Registry.ENTITY_TYPE, new Identifier(LilWings.MODID, name), FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build());
        return () -> object;
    }

    public static void addEntityToBiome(Biome.Category category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.categories(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    public static void addEntityToBiome(RegistryKey<Biome> biome, SpawnData data) {
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey().equals(biome), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    public static <T extends MobEntity> void setSpawnRules(Supplier<EntityType<T>> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType.get(), location, type, predicate);
    }

    public static ItemGroup registerCreativeTab(Identifier tab, Supplier<ItemStack> supplier) {
        return FabricItemGroupBuilder.build(tab, supplier);
    }
}
