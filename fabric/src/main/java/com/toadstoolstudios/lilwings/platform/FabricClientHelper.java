package com.toadstoolstudios.lilwings.platform;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.platform.services.IClientHelper;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.DefaultParticleType;

import java.util.function.Supplier;

public class FabricClientHelper implements IClientHelper {
    @Override
    public void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), renderLayer);
    }

    @Override
    public <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererFactory<T> factory) {
        EntityRendererRegistry.register(supplier.get(), factory);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer) {
        BlockEntityRendererRegistry.register(blockEntity.get(), blockEntityRenderer);
    }

    @Override
    public void registerParticleFactory(Supplier<DefaultParticleType> particle, SpriteAwareFactory<DefaultParticleType> factory) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), factory::create);
    }
}
