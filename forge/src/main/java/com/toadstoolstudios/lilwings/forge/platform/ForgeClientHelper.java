package com.toadstoolstudios.lilwings.forge.platform;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.platform.services.IClientHelper;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.DefaultParticleType;

import java.util.function.Supplier;

public class ForgeClientHelper implements IClientHelper {
    @Override
    public void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        RenderLayers.setRenderLayer(blockSupplier.get(), renderLayer);
    }

    @Override
    public <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererFactory<T> factory) {
        EntityRenderers.register(supplier.get(), factory);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer) {
        BlockEntityRendererFactories.register(blockEntity.get(), blockEntityRenderer);
    }

    @Override
    public void registerParticleFactory(Supplier<DefaultParticleType> particle, SpriteAwareFactory<DefaultParticleType> factory) {
        MinecraftClient.getInstance().particleManager.registerFactory(particle.get(), factory::create);
    }
}
