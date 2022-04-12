package com.toadstoolstudios.lilwings.registry;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.platform.CommonServices;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;

import java.util.function.Supplier;

public class LilWingsParticles {

    public static Supplier<DefaultParticleType> BROWN_SPORE = CommonServices.REGISTRY.registerParticleType("brown_spore", () -> new DefaultParticleType(false){});
    public static Supplier<DefaultParticleType> AMETHYST_GROW = CommonServices.REGISTRY.registerParticleType("amethyst_grow", () -> new DefaultParticleType(false){});
    public static Supplier<DefaultParticleType> GOLDAPPLE_HEARTS = CommonServices.REGISTRY.registerParticleType("goldapple_hearts", () -> new DefaultParticleType(false){});

    public static void register() {}
}
