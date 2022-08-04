package com.toadstoolstudios.lilwings.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class GoldAppleHeartsProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet sprite;

    public GoldAppleHeartsProvider(SpriteSet pSprites) {
        this.sprite = pSprites;
    }

    @Nullable
    @Override
    public Particle createParticle(SimpleParticleType particleOptions, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        LilSuspendedParticle suspendedParticle = new LilSuspendedParticle(pLevel, sprite, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        suspendedParticle.pickSprite(this.sprite);
        suspendedParticle.setColor(1.0F, 1.0F, 1.0F);
        return suspendedParticle;
    }
}
