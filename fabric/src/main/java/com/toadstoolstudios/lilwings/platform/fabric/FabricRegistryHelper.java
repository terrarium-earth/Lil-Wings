package com.toadstoolstudios.lilwings.platform.fabric;

import com.toadstoolstudios.lilwings.fabric.FabricButterflyElytra;
import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.platform.services.IRegistryHelper;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    public static final Set<String> TEXTURES = new HashSet<>();

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(LilWings.MODID, id), item.get());
        return () -> registry;
    }

    @Override
    public <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(LilWings.MODID, id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> registry;
    }

    @Override
    public Supplier<ElytraItem> registerElytra(String id, ResourceLocation texture) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(LilWings.MODID, id), new FabricButterflyElytra(texture));
        return () -> registry;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        var registry = Registry.register(Registry.BLOCK, new ResourceLocation(LilWings.MODID, id), block.get());
        return () -> registry;
    }

    @Override
    public <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        var registry = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(LilWings.MODID, id), blockEntity.get());
        return () -> registry;
    }

    @Override
    public <T extends SimpleParticleType> Supplier<T> registerParticleType(String name, Supplier<T> particle) {
        TEXTURES.add(name);
        var registry = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(LilWings.MODID, name), particle.get());
        return () -> registry;
    }

    @Override
    public <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityFactory<E> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        var object = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(LilWings.MODID, name), FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build());
        return () -> object;
    }

    @Override
    public <T extends Mob> void setSpawnRules(Supplier<EntityType<T>> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType.get(), location, type, predicate);
    }

    @Override
    public CreativeModeTab registerCreativeTab(ResourceLocation tab, Supplier<ItemStack> supplier) {
        return FabricItemGroupBuilder.build(tab, supplier);
    }
}
