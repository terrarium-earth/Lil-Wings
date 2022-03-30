package com.toadstoolstudios.lilwings.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class LilSuspendedParticle extends TextureSheetParticle {

    public LilSuspendedParticle(ClientLevel pLevel, SpriteSet pSprites, double pX, double pY, double pZ) {
        super(pLevel, pX, pY - 0.125D, pZ);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(pSprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    public LilSuspendedParticle(ClientLevel pLevel, SpriteSet pSprites, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY - 0.125D, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(pSprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}
