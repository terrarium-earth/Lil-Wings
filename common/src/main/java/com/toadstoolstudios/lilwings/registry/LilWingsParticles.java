package com.toadstoolstudios.lilwings.registry;

import com.toadstoolstudios.lilwings.platform.CommonServices;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class LilWingsParticles {

    public static Supplier<SimpleParticleType> BROWN_SPORE = CommonServices.REGISTRY.registerParticleType("brown_spore", () -> new SimpleParticleType(false) {
    });
    public static Supplier<SimpleParticleType> AMETHYST_GROW = CommonServices.REGISTRY.registerParticleType("amethyst_grow", () -> new SimpleParticleType(false) {
    });
    public static Supplier<SimpleParticleType> GOLDAPPLE_HEARTS = CommonServices.REGISTRY.registerParticleType("goldapple_hearts", () -> new SimpleParticleType(false) {
    });

    public static void register() {
    }
}
