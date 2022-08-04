package com.toadstoolstudios.lilwings.platform.fabric;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.platform.services.IClientHelper;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FabricClientHelper implements IClientHelper {
    @Override
    public void renderBlockRenderers(Supplier<Block> blockSupplier, RenderType renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), renderLayer);
    }

    @Override
    public <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererProvider<T> factory) {
        EntityRendererRegistry.register(supplier.get(), factory);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererProvider<T> blockEntityRenderer) {
        BlockEntityRendererRegistry.register(blockEntity.get(), blockEntityRenderer);
    }

    @Override
    public void registerParticleFactory(Supplier<SimpleParticleType> particle, SpriteAwareFactory<SimpleParticleType> factory) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), factory::create);
    }
}
