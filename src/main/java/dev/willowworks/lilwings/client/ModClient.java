package dev.willowworks.lilwings.client;

import dev.willowworks.lilwings.client.entity.ButterflyRenderer;
import dev.willowworks.lilwings.client.entity.JarEntityRenderer;
import dev.willowworks.lilwings.registry.ModBlocks;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModClient {

    public static void init() {
        for (Butterfly butterfly : Butterfly.BUTTERFLIES.values()) {
            EntityRenderers.register(butterfly.entityType(), renderProvider -> new ButterflyRenderer(renderProvider, butterfly.textureName()));
        }

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BUTTERFLY_JAR.get(), RenderType.translucent());
        BlockEntityRenderers.register(ModBlocks.BUTTERFLY_JAR_ENTITY.get(), context -> new JarEntityRenderer());
    }
}
