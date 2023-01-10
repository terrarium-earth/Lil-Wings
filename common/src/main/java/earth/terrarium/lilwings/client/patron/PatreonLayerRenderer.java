package earth.terrarium.lilwings.client.patron;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.lilwings.LilWings;
import earth.terrarium.lilwings.api.PatreonData;
import earth.terrarium.lilwings.api.PatreonManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class PatreonLayerRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LilWings.MODID, "textures/patreon/butter_gold.png");
    private static final Map<PatreonData.ButterflyType, PatreonFlutteringButterfly> BUTTERFLIES = new EnumMap<>(PatreonData.ButterflyType.class);
    private final PatreonButterflyModel headwearModel;
    private final PatreonFluttererRenderer renderer = new PatreonFluttererRenderer();

    public PatreonLayerRenderer(PlayerRenderer rendererContext, EntityRendererProvider.Context context) {
        super(rendererContext);
        headwearModel = new PatreonButterflyModel(context.bakeLayer(PatreonButterflyModel.LAYER));
    }

    public PatreonLayerRenderer(PlayerRenderer rendererContext, EntityModelSet loader) {
        super(rendererContext);
        headwearModel = new PatreonButterflyModel(loader.bakeLayer(PatreonButterflyModel.LAYER));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (PatreonManager.isUserPatron(entity.getUUID())) {
            matrices.pushPose();
            headwearModel.prepare();
            if (!entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                matrices.translate(0, -1, -1);
            }
            headwearModel.crouching = entity.isCrouching();
            coloredCutoutModelCopyLayerRender(getParentModel(), headwearModel, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, 0f, headYaw, headPitch, tickDelta, 1f, 1f, 1f);
            matrices.popPose();
        }

        PatreonData.ButterflyType butterflyType = PatreonManager.getButterflyType(entity.getUUID());
        if (butterflyType != null) {
            PatreonFlutteringButterfly data = BUTTERFLIES.computeIfAbsent(butterflyType, type -> new PatreonFlutteringButterfly(new ResourceLocation(LilWings.MODID, "textures/patreon/" + type.name().toLowerCase(Locale.ROOT) + ".png")));
            PatreonFlutteringModel modelProvider = data.getModel();
            var model = modelProvider.getModel(data);
            AnimationEvent<PatreonFlutteringButterfly> event = new AnimationEvent<>(data, 0, 0, Minecraft.getInstance().getDeltaFrameTime(), false, Collections.emptyList());
            modelProvider.setLivingAnimations(data, renderer.getUniqueID(data), event);
            RenderType renderLayer = RenderType.entityCutoutNoCull(data.getTexture());
            matrices.pushPose();
            matrices.translate(0, 0.9, 0);
            matrices.scale(1, -1, 1);
            renderer.render(model, data, tickDelta, renderLayer, matrices, vertexConsumers, vertexConsumers.getBuffer(renderLayer), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            matrices.popPose();
        }
    }
}
