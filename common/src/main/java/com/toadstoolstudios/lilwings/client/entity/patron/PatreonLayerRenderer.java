package com.toadstoolstudios.lilwings.client.entity.patron;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class PatreonLayerRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final Identifier TEXTURE = new Identifier(LilWings.MODID, "textures/patreon/patreon_butterfly.png");
    private final PatreonButterflyModel model;

    public PatreonLayerRenderer(PlayerEntityRenderer rendererContext, EntityRendererFactory.Context context) {
        super(rendererContext);
        model = new PatreonButterflyModel(context.getPart(PatreonButterflyModel.LAYER));
    }

    public PatreonLayerRenderer(PlayerEntityRenderer rendererContext, EntityModelLoader loader) {
        super(rendererContext);
        model = new PatreonButterflyModel(loader.getModelPart(PatreonButterflyModel.LAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (PatreonManager.isUserPatron(entity.getUuid())) {
            matrices.push();
            model.prepare();
            if(!entity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                matrices.translate(0, -1, -1);
            }
            model.sneaking = entity.isInSneakingPose();
            render(getContextModel(), model, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, 0f, headYaw, headPitch, tickDelta, 1f, 1f, 1f);
            matrices.pop();
        }
    }
}
