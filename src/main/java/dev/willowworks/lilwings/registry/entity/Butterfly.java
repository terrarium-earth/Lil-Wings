package dev.willowworks.lilwings.registry.entity;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import dev.willowworks.lilwings.entity.effects.CatchEffect;
import dev.willowworks.lilwings.registry.ModItems;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public record Butterfly(
        EntityType<ButterflyEntity> entityType,
        ForgeSpawnEggItem spawnEggItem,
        Item wings, Item breedingItem, RegistryObject<Item> netItem,
        SimpleParticleType particleType, float particleSpawnChance,
        float spawnScale, float childSpawnScale, float maxHealth,
        int catchAmount, CatchEffect catchEffect,
        String textureName
) {

    public static final Map<ResourceLocation, Butterfly> BUTTERFLIES = new HashMap<>();

    public static class Builder {

        private final String name;

        private boolean wings = false;
        private boolean spawnEgg = false;
        private int eggBackgroundColor = 0xFFFFFF;
        private int eggOutlineColor = 0xFFFFFF;

        private SimpleParticleType particleType = null;
        private float particleSpawnChance = 0.08f;

        private float spawnScale = 0.65f;
        private float childSpawnScale = 0.4f;

        private float boundingWidth = 0.7f;
        private float boundingHeight = 0.7f;

        private float maxHealth = 6.0f;
        private int catchAmount = 1;

        private Item breedingItem = null;
        private RegistryObject<Item> netItem = null;
        private CatchEffect catchEffect;
        private CreativeModeTab creativeTab = ModItems.TAB;

        public Builder(String name) {
            this.name = name;
        }

        public Builder addWings() {
            this.wings = true;
            return this;
        }

        public Builder addSpawnEgg(int backgroundColor, int outlineColor) {
            this.spawnEgg = true;
            this.eggBackgroundColor = backgroundColor;
            this.eggOutlineColor = outlineColor;
            return this;
        }

        public Builder addParticles(SimpleParticleType particleType, float spawnChance) {
            this.particleType = particleType;
            this.particleSpawnChance = spawnChance;
            return this;
        }

        public Builder setSpawnScale(float scale, float childScale) {
            this.spawnScale = scale;
            this.childSpawnScale = childScale;
            return this;
        }

        public Builder setBoundingBoxSize(float width, float height) {
            this.boundingWidth = width;
            this.boundingHeight = height;
            return this;
        }

        public Builder setBreedingItem(Item item) {
            this.breedingItem = item;
            return this;
        }

        public Builder setMaxHealth(float maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public Builder setCatchAmount(int catchAmount) {
            this.catchAmount = catchAmount;
            return this;
        }

        public Builder setCatchEffect(CatchEffect effect) {
            this.catchEffect = effect;
            return this;
        }

        public Builder setCreativeTab(CreativeModeTab tab) {
            this.creativeTab = tab;
            return this;
        }

        public Builder setNet(RegistryObject<Item> netItem) {
            this.netItem = netItem;
            return this;
        }

        public Butterfly build(String modid, DeferredRegister<EntityType<?>> entityRegister, DeferredRegister<Item> itemRegister) {
            if (entityRegister == null || itemRegister == null)
                throw new NullPointerException("Butterfly builder cannot have an invalid DeferredRegister for items or entities!");

            EntityType<ButterflyEntity> entityType = EntityType.Builder.of(ButterflyEntity::new, MobCategory.MISC)
                    .sized(boundingWidth, boundingHeight).clientTrackingRange(8).build(name + "_butterfly");
            ForgeSpawnEggItem spawnEggItem = null;
            Item wingsItem = null;

            entityRegister.register(name + "_butterfly", () -> entityType);
            if (spawnEgg) {
                spawnEggItem = new ForgeSpawnEggItem(() -> entityType, eggBackgroundColor, eggOutlineColor, new Item.Properties().tab(creativeTab));
                ForgeSpawnEggItem finalEggItem = spawnEggItem;
                itemRegister.register(name + "_egg", () -> finalEggItem);
            }

            if (wings) {
                wingsItem = new Item(new Item.Properties().tab(creativeTab));
                Item finalWingsItem = wingsItem;
                itemRegister.register(name + "_wings", () -> finalWingsItem);
            }

            Butterfly butterfly = new Butterfly(entityType, spawnEggItem, wingsItem, breedingItem, netItem, particleType, particleSpawnChance, spawnScale, childSpawnScale, maxHealth, catchAmount, catchEffect, name);
            BUTTERFLIES.put(new ResourceLocation(modid, name + "_butterfly"), butterfly);
            return butterfly;
        }

        public static Builder of(String name) {
            return new Builder(name);
        }
    }
}
