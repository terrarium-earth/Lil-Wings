package com.toadstoolstudios.lilwings.registry.forge;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.forge.ForgeButterflyElytra;
import com.toadstoolstudios.lilwings.platform.services.IRegistryHelper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class LilWingsRegistryImpl {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LilWings.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LilWings.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LilWings.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LilWings.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LilWings.MODID);

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        return ITEMS.register(id, () -> new ForgeSpawnEggItem(entity, primaryColor, secondaryColor, settings));
    }

    public static Supplier<ElytraItem> registerElytra(String id, ResourceLocation texture) {
        return ITEMS.register(id, () -> new ForgeButterflyElytra(texture));
    }

    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        return BLOCK_ENTITIES.register(id, blockEntity);
    }

    public static <T extends SimpleParticleType> Supplier<T> registerParticleType(String name, Supplier<T> particle) {
        return PARTICLE_TYPES.register(name, particle);
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(IRegistryHelper.BlockEntityFactory<E> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory::create, blocks).build(null);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.create(factory, group).setDimensions(width, height).build(name));
    }


    public static <T extends Mob> void setSpawnRules(Supplier<EntityType<T>> entityType, Spawn.Location location, Heightmap.Type type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType.get(), location, type, predicate);
    }

    public static ItemGroup registerCreativeTab(Identifier tab, Supplier<ItemStack> supplier) {
        return new ItemGroup(tab.getNamespace() + "." + tab.getPath()) {
            @Override
            public ItemStack createIcon() {
                return supplier.get();
            }
        };
    }
}
