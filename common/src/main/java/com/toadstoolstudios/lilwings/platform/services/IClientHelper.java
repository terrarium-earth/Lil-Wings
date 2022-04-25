package com.toadstoolstudios.lilwings.platform.services;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;

import java.util.function.Supplier;

public interface IClientHelper {

    void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer);

    <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererFactory<T> factory);

    <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer);

    void registerParticleFactory(Supplier<DefaultParticleType> particle, SpriteAwareFactory<DefaultParticleType> factory);

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface SpriteAwareFactory<T extends ParticleEffect> {
        ParticleFactory<T> create(SpriteProvider spriteProvider);
    }
}
