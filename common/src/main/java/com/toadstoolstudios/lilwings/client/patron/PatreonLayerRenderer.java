package com.toadstoolstudios.lilwings.client.patron;

import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.api.PatreonData;
import com.toadstoolstudios.lilwings.api.PatreonManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class PatreonLayerRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final Identifier TEXTURE = new Identifier(LilWings.MODID, "textures/patreon/butter_gold.png");
    private static final Map<PatreonData.ButterflyType, PatreonFlutteringButterfly> BUTTERFLIES = new EnumMap<>(PatreonData.ButterflyType.class);
    private final PatreonButterflyModel headwearModel;
    private final PatreonFluttererRenderer renderer = new PatreonFluttererRenderer();

    public PatreonLayerRenderer(PlayerEntityRenderer rendererContext, EntityRendererFactory.Context context) {
        super(rendererContext);
        headwearModel = new PatreonButterflyModel(context.getPart(PatreonButterflyModel.LAYER));
    }

    public PatreonLayerRenderer(PlayerEntityRenderer rendererContext, EntityModelLoader loader) {
        super(rendererContext);
        headwearModel = new PatreonButterflyModel(loader.getModelPart(PatreonButterflyModel.LAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (PatreonManager.isUserPatron(entity.getUuid())) {
            matrices.push();
            headwearModel.prepare();
            if (!entity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                matrices.translate(0, -1, -1);
            }
            headwearModel.sneaking = entity.isInSneakingPose();
            render(getContextModel(), headwearModel, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, 0f, headYaw, headPitch, tickDelta, 1f, 1f, 1f);
            matrices.pop();
        }

        PatreonData.ButterflyType butterflyType = PatreonManager.getButterflyType(entity.getUuid());
        if (butterflyType != null) {
            PatreonFlutteringButterfly data = BUTTERFLIES.computeIfAbsent(butterflyType, type -> new PatreonFlutteringButterfly(new Identifier(LilWings.MODID, "textures/patreon/" + type.name().toLowerCase(Locale.ROOT) + ".png")));
            PatreonFlutteringModel modelProvider = data.getModel();
            var model = modelProvider.getModel(data);
            AnimationEvent<PatreonFlutteringButterfly> event = new AnimationEvent<>(data, 0, 0, MinecraftClient.getInstance().getLastFrameDuration(), false, Collections.emptyList());
            modelProvider.setLivingAnimations(data, renderer.getUniqueID(data), event);
            RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(data.getTexture());
            matrices.push();
            matrices.translate(0, 0.9, 0);
            matrices.scale(1, -1, 1);
            renderer.render(model, data, tickDelta, renderLayer, matrices, vertexConsumers, vertexConsumers.getBuffer(renderLayer), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
            matrices.pop();
        }
    }
}
