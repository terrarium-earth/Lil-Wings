package dev.willowworks.lilwings.client.particle;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class LilSuspendedParticle extends SpriteBillboardParticle {

    public LilSuspendedParticle(ClientWorld pLevel, SpriteProvider pSprites, double pX, double pY, double pZ) {
        super(pLevel, pX, pY - 0.125D, pZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(pSprites);
        this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
        this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = false;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
    }

    public LilSuspendedParticle(ClientWorld pLevel, SpriteProvider pSprites, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY - 0.125D, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(pSprites);
        this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
        this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = false;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }
}
