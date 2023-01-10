package earth.terrarium.lilwings.forge.platform;

import earth.terrarium.lilwings.entity.ButterflyEntity;
import earth.terrarium.lilwings.platform.services.IClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ForgeClientHelper implements IClientHelper {
    @Override
    public void renderBlockRenderers(Supplier<Block> blockSupplier, RenderType renderLayer) {
        ItemBlockRenderTypes.setRenderLayer(blockSupplier.get(), renderLayer);
    }

    @Override
    public <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererProvider<T> factory) {
        EntityRenderers.register(supplier.get(), factory);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererProvider<T> blockEntityRenderer) {
        BlockEntityRenderers.register(blockEntity.get(), blockEntityRenderer);
    }

    @Override
    public void registerParticleFactory(Supplier<SimpleParticleType> particle, SpriteAwareFactory<SimpleParticleType> factory) {
        Minecraft.getInstance().particleEngine.register(particle.get(), factory::create);
    }
}
