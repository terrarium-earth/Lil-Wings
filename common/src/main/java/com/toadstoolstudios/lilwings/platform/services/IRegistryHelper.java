package com.toadstoolstudios.lilwings.platform.services;

import com.toadstoolstudios.lilwings.registry.SpawnData;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IRegistryHelper {

    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item);

    <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings);

    Supplier<ElytraItem> registerElytra(String id, Identifier texture);

    <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block);

    <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity);

    <T extends DefaultParticleType> Supplier<T> registerParticleType(String name, Supplier<T> particle);

    <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityFactory<E> factory, Block... blocks);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height);

    void addEntityToBiome(Biome.Category category, SpawnData data);

    void addEntityToBiome(RegistryKey<Biome> biome, SpawnData data);

    <T extends MobEntity> void setSpawnRules(Supplier<EntityType<T>> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate);

    @FunctionalInterface
    interface BlockEntityFactory<T extends BlockEntity> {
        @NotNull T create(BlockPos blockPos, BlockState blockState);
    }

    ItemGroup registerCreativeTab(Identifier tab, Supplier<ItemStack> supplier);


}
