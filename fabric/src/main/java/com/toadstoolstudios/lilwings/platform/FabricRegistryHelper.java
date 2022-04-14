package com.toadstoolstudios.lilwings.platform;

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

import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), item.get());
        return () -> registry;
    }

    @Override
    public <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> registry;
    }

    @Override
    public Supplier<ElytraItem> registerElytra(String id, Identifier texture) {
        var registry = Registry.register(Registry.ITEM, new Identifier(LilWings.MODID, id), new FabricButterflyElytra(texture));
        return () -> registry;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        var registry = Registry.register(Registry.BLOCK, new Identifier(LilWings.MODID, id), block.get());
        return () -> registry;
    }

    @Override
    public <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        var registry = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(LilWings.MODID, id), blockEntity.get());
        return () -> registry;
    }

    @Override
    public <T extends DefaultParticleType> Supplier<T> registerParticleType(String name, Supplier<T> particle) {
        var registry = Registry.register(Registry.PARTICLE_TYPE, new Identifier(LilWings.MODID, name), particle.get());
        return () -> registry;
    }

    @Override
    public <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityFactory<E> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        var object = FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build();
        return () -> object;
    }

    @Override
    public void addEntityToBiome(Biome.Category category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.categories(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    @Override
    public void addEntityToBiome(RegistryKey<Biome> biome, SpawnData data) {
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey().equals(biome), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    @Override
    public <T extends MobEntity> void setSpawnRules(Supplier<EntityType<T>> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType.get(), location, type, predicate);
    }

    @Override
    public ItemGroup registerCreativeTab(Identifier tab, Supplier<ItemStack> supplier) {
        return FabricItemGroupBuilder.build(tab, supplier);
    }
}
