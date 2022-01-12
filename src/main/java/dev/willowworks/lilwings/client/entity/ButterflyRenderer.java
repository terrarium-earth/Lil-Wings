package dev.willowworks.lilwings.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.willowworks.lilwings.client.model.ButterflyModel;
import dev.willowworks.lilwings.entity.ButterflyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {

    public ButterflyRenderer(EntityRendererProvider.Context renderManager, String butterflyTexture) {
        super(renderManager, new ButterflyModel(butterflyTexture));
        this.shadowRadius = 0.25f;
    }

    @Override
    public void renderEarly(ButterflyEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float scale = animatable.isBaby() ? animatable.getButterfly().childSpawnScale() : animatable.getButterfly().spawnScale();
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        stackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        stackIn.scale(scale, scale, scale);
    }

    @Override
    public RenderType getRenderType(ButterflyEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutout(textureLocation);
    }
}
