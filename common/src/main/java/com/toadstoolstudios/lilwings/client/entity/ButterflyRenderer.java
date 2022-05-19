package com.toadstoolstudios.lilwings.client.entity;

import com.toadstoolstudios.lilwings.client.model.ButterflyModel;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {

    public ButterflyRenderer(EntityRendererFactory.Context renderManager, String butterflyTexture) {
        super(renderManager, new ButterflyModel(butterflyTexture));
        this.shadowRadius = 0.25f;
    }

    @Override
    public void renderEarly(ButterflyEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float scale = animatable.isBaby() ? animatable.getButterfly().childSpawnScale() : animatable.getButterfly().spawnScale();
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        stackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        stackIn.scale(scale, scale, scale);
    }

    @Override
    public RenderLayer getRenderType(ButterflyEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityCutout(textureLocation);
    }
}
