package dev.willowworks.lilwings.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class AmethystGrowProvider implements ParticleFactory<DefaultParticleType> {

    private final SpriteProvider sprite;

    public AmethystGrowProvider(SpriteProvider pSprites) {
        this.sprite = pSprites;
    }

    @Nullable
    @Override
    public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        LilSuspendedParticle suspendedParticle = new LilSuspendedParticle(pLevel, sprite, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        suspendedParticle.setSprite(this.sprite);
        suspendedParticle.setColor(1.0F, 1.0F, 1.0F);
        return suspendedParticle;
    }
}
