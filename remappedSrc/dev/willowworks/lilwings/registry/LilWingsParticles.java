package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LilWingsParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LilWings.MODID);

    public static RegistryObject<DefaultParticleType> BROWN_SPORE = PARTICLES.register("brown_spore", () -> new DefaultParticleType(false));
    public static RegistryObject<DefaultParticleType> AMETHYST_GROW = PARTICLES.register("amethyst_grow", () -> new DefaultParticleType(false));
    public static RegistryObject<DefaultParticleType> GOLDAPPLE_HEARTS = PARTICLES.register("goldapple_hearts", () -> new DefaultParticleType(false));
}
