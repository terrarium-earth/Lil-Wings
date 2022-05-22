package com.toadstoolstudios.lilwings.registry.entity;


import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.entity.effects.CatchEffect;
import com.toadstoolstudios.lilwings.block.jareffects.JarEffect;
import com.toadstoolstudios.lilwings.platform.CommonServices;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public record Butterfly(
        Supplier<EntityType<ButterflyEntity>> entityType,
        Supplier<SpawnEggItem> spawnEggItem,
        Supplier<Item>[] wings, Supplier<Item>[] cottonBallsItems,
        Supplier<ElytraItem>[] elytras,
        Item breedingItem,
        Supplier<Item> netItem,
        DefaultParticleType particleType,
        float particleSpawnChance,
        float spawnScale, float childSpawnScale, float maxHealth,
        int catchAmount, CatchEffect catchEffect, Supplier<JarEffect> jarEffect,
        String textureName
) {

    public static final Map<Identifier, Butterfly> BUTTERFLIES = new HashMap<>();

    public static Butterfly getButterfly(EntityType<?> entityType) {
        return BUTTERFLIES.get(EntityType.getId(entityType));
    }

    public static class Builder {

        private final String name;

        private String[] wings;
        private String[] cottonBalls;
        private String[] elytras;

        private boolean spawnEgg = false;
        private int eggBackgroundColor = 0xFFFFFF;
        private int eggOutlineColor = 0xFFFFFF;

        private DefaultParticleType particleType = null;
        private float particleSpawnChance = 0.08f;

        private float spawnScale = 0.65f;
        private float childSpawnScale = 0.4f;

        private float boundingWidth = 0.7f;
        private float boundingHeight = 0.7f;

        private float maxHealth = 6.0f;
        private int catchAmount = 1;

        private Item breedingItem = null;
        private Supplier<Item> netItem = null;
        private CatchEffect catchEffect;
        private Supplier<JarEffect> jarEffect;

        private ItemGroup creativeTab = LilWings.TAB;

        private Builder(String name) {
            this.name = name;
        }

        public Builder addWings(String... wings) {
            if (wings != null) {
                /*
                this.wings = Arrays.copyOf(wings, wings.length + 1);
                this.wings[wings.length] = "";
                this.cottonBalls = Arrays.copyOf(wings, wings.length + 1);
                this.cottonBalls[wings.length] = "";
                 */
                this.wings = new String[wings.length + 1];
                this.wings[0] = "";
                if (wings.length - 1 >= 0) System.arraycopy(wings, 0, this.wings, 1, wings.length);
                this.cottonBalls = new String[wings.length + 1];
                this.cottonBalls[0] = "";
                if (wings.length - 1 >= 0) System.arraycopy(wings, 0, this.cottonBalls, 1, wings.length);

            } else {
                this.wings = new String[]{""};
            }

            return this;
        }

        public Builder addElytra(String... elytras) {
            if (elytras != null) {
                this.elytras = Arrays.copyOf(elytras, elytras.length + 1);
                this.elytras[elytras.length] = "";
            } else {
                this.elytras = new String[]{""};
            }

            return this;
        }

        public Builder addSpawnEgg(int backgroundColor, int outlineColor) {
            this.spawnEgg = true;
            this.eggBackgroundColor = backgroundColor;
            this.eggOutlineColor = outlineColor;
            return this;
        }

        public Builder addParticles(DefaultParticleType particleType, float spawnChance) {
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

        public Builder setJarEffect(Supplier<JarEffect> effectSupplier) {
            this.jarEffect = effectSupplier;
            return this;
        }

        public Builder setCreativeTab(ItemGroup tab) {
            this.creativeTab = tab;
            return this;
        }

        public Builder setNet(Supplier<Item> netItem) {
            this.netItem = netItem;
            return this;
        }

        public Butterfly build(String modid) {

            Supplier<Item>[] wingItems = new Supplier[wings != null ? wings.length : 0];
            Supplier<Item>[] cottonBallsItems = new Supplier[cottonBalls != null ? cottonBalls.length : 0];
            Supplier<ElytraItem>[] elytraItems = new Supplier[elytras != null ? elytras.length : 0];

            Supplier<EntityType<ButterflyEntity>> entityType = CommonServices.REGISTRY.registerEntity(name + "_butterfly", ButterflyEntity::new,
                    SpawnGroup.AMBIENT, boundingWidth, boundingHeight);

            Supplier<SpawnEggItem> spawnEggItem = null;
            if (spawnEgg) {
                spawnEggItem = CommonServices.REGISTRY.registerSpawnEgg(name + "_egg", entityType, eggBackgroundColor, eggOutlineColor, new Item.Settings().group(creativeTab));
            }

            if (wings != null) {
                for (int i = 0; i < wings.length; i++) {
                    String wingName = wings[i];
                    wingItems[i] = CommonServices.REGISTRY.registerItem(name + (wingName.isEmpty() ? "" : "_" + wingName) + "_wings", () ->
                            new Item(new Item.Settings().group(creativeTab)));
                }
            }

            if (cottonBalls != null) {
                for (int i = 0; i < cottonBalls.length; i++) {
                    String cottonBallName = cottonBalls[i];
                    cottonBallsItems[i] = CommonServices.REGISTRY.registerItem(name + (cottonBallName.isEmpty() ? "" : "_" + cottonBallName) + "_cotton_ball", () ->
                            new Item(new Item.Settings().group(creativeTab)));
                }
            }

            if (elytras != null) {
                for (int i = 0; i < elytras.length; i++) {
                    String elytraName = elytras[i];
                    String regName = name + (elytraName.isEmpty() ? "" : "_" + elytraName) + "_elytra";

                    elytraItems[i] = CommonServices.REGISTRY.registerElytra(regName, new Identifier(modid, "textures/elytra/" + regName + ".png"));
                }
            }

            Butterfly butterfly = new Butterfly(
                    entityType, spawnEggItem, wingItems, cottonBallsItems, elytraItems, breedingItem, netItem,
                    particleType, particleSpawnChance, spawnScale, childSpawnScale, maxHealth,
                    catchAmount, catchEffect, jarEffect, name
            );
            BUTTERFLIES.put(new Identifier(modid, name + "_butterfly"), butterfly);
            return butterfly;
        }

        public static Builder of(String name) {
            return new Builder(name);
        }
    }
}
