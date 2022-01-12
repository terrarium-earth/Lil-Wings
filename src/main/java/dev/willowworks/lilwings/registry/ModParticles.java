package dev.willowworks.lilwings.registry;

import dev.willowworks.lilwings.LilWings;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LilWings.MODID);

    public static RegistryObject<SimpleParticleType> BROWN_SPORE = PARTICLES.register("brown_spore", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> AMETHYST_GROW = PARTICLES.register("amethyst_grow", () -> new SimpleParticleType(false));
}
