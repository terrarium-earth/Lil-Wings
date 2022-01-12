package dev.willowworks.lilwings.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.Random;

public class BrownParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet sprite;

    public BrownParticleProvider(SpriteSet pSprites) {
        this.sprite = pSprites;
    }

    public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        Random random = pLevel.random;
        double d0 = random.nextGaussian() * (double) 1.0E-6F;
        double d1 = random.nextGaussian() * (double) 1.0E-4F;
        double d2 = random.nextGaussian() * (double) 1.0E-6F;
        LilSuspendedParticle particle = new LilSuspendedParticle(pLevel, this.sprite, pX, pY, pZ, d0, d1, d2);
        particle.setColor(107 / 255.0f, 64 / 255.0f, 12 / 255.0f);

        return particle;
    }
}
