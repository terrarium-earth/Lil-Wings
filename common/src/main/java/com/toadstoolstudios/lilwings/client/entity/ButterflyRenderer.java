package com.toadstoolstudios.lilwings.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.client.model.ButterflyModel;
import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.entity.GraylingType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {
    public final String butterflyTexture;

    public ButterflyRenderer(EntityRendererProvider.Context renderManager, String butterflyTexture) {
        super(renderManager, new ButterflyModel(butterflyTexture));
        this.shadowRadius = 0.25f;
        this.butterflyTexture = butterflyTexture;
    }

    @Override
    public void renderEarly(ButterflyEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        float scale = animatable.isBaby() ? animatable.getButterfly().childSpawnScale() : animatable.getButterfly().spawnScale();
        stackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        stackIn.scale(scale, scale, scale);
    }

    @Override
    public RenderType getRenderType(ButterflyEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity object) {
        if (object.getType() == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get() && object.getColorType() != null) {
            GraylingType type = object.getColorType();
            return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + type.getTextureColor() + ".png");
        }
        return new ResourceLocation(LilWings.MODID, "textures/entity/" + butterflyTexture + ".png");
    }
}
