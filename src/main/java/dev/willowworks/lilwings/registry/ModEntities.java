package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import dev.willowworks.lilwings.entity.effects.EnderflyCatchEffect;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LilWings.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LilWings.MODID);

    public static Butterfly WHITE_FOX_BUTTERFLY = Butterfly.Builder.of("white_fox")
            .setBreedingItem(Items.LILY_OF_THE_VALLEY)
            .addParticles(ParticleTypes.ITEM_SNOWBALL, 0.08f)
            .addSpawnEgg(0x6DC6D7, 0xBEF0F0)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly SWALLOW_TAIL_BUTTERFLY = Butterfly.Builder.of("swallow_tail")
            .setBreedingItem(Items.BLUE_ORCHID)
            .addSpawnEgg(0X1463C5, 0X2FCEFD)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly SHROOM_SKIPPER_BUTTERFLY = Butterfly.Builder.of("shroom_skipper")
            .setBreedingItem(Items.RED_MUSHROOM)
            .addParticles(ParticleTypes.CRIMSON_SPORE, 0.1f)
            .addSpawnEgg(0XBB2225, 0XE0612A)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly PAINTED_PANTHER_BUTTERFLY = Butterfly.Builder.of("painted_panther")
            .setBreedingItem(Items.COCOA_BEANS)
            .addSpawnEgg(0x0A0A0A, 0x271F19)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly ENDER_WING_BUTTERFLY = Butterfly.Builder.of("ender_wing")
            .setBreedingItem(Items.END_STONE)
            .addParticles(ParticleTypes.REVERSE_PORTAL, 0.15f)
            .addSpawnEgg(0x101E29, 0xB5E45A)
            .setSpawnScale(2.5f, 1.75f)
            .setBoundingBoxSize(2.5f, 2.5f)
            .setMaxHealth(9)
            .setCatchAmount(2)
            .setCatchEffect(new EnderflyCatchEffect())
            .setNet(ModItems.ENDERFLY_NET)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly CRYSTAL_PUFF_BUTTERFLY = Butterfly.Builder.of("crystal_puff")
            .setBreedingItem(Items.ALLIUM)
            .addSpawnEgg(0xCC88EF, 0xEBC5FD)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly CLOUDY_PUFF_BUTTERFLY = Butterfly.Builder.of("cloudy_puff")
            .setBreedingItem(Items.AZURE_BLUET)
            .addSpawnEgg(0xF1DD3C, 0xEDDE70)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly BUTTER_GOLD_BUTTERFLY = Butterfly.Builder.of("butter_gold")
            .setBreedingItem(Items.DANDELION)
            .addSpawnEgg(0xAE6D26, 0xFAEA2E)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly APONI_BUTTERFLY = Butterfly.Builder.of("aponi")
            .setBreedingItem(Items.BROWN_MUSHROOM)
            .addSpawnEgg(0x6A4418, 0xBE742D)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static Butterfly RED_APPLEFLY_BUTTERFLY = Butterfly.Builder.of("red_applefly")
            .setBreedingItem(Items.APPLE)
            .addSpawnEgg(0x54090E, 0xFF969D)
            .addWings()
            .build(LilWings.MODID, ENTITIES, ITEMS);

    public static void attributeEvent(EntityAttributeCreationEvent event) {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            event.put(butterfly.entityType(), Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, butterfly.maxHealth())
                    .add(Attributes.FLYING_SPEED, 1.0f)
                    .build()
            );
        }
    }
}
