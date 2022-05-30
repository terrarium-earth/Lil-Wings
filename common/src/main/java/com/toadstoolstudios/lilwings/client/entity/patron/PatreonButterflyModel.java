package com.toadstoolstudios.lilwings.client.entity.patron;

import com.mojang.blaze3d.systems.RenderSystem;
import com.toadstoolstudios.lilwings.LilWings;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PatreonButterflyModel extends BipedEntityModel<AbstractClientPlayerEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(new Identifier(LilWings.MODID, "patreon_butterfly"), "main");

    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public PatreonButterflyModel(ModelPart root) {
        super(root);

        var head = root.getChild(EntityModelPartNames.HEAD);
        rightWing = head.getChild("right_wing");
        leftWing = head.getChild("left_wing");
    }

    public void prepare() {
        setVisible(false);
        head.visible = true;
        rightWing.visible = true;
        leftWing.visible = true;
    }

    @Override
    public void setAngles(AbstractClientPlayerEntity livingEntity, float limbAngle, float limbDistance, float h, float i, float j) {
        super.setAngles(livingEntity, limbAngle, limbDistance, h, i, j);
        if(!livingEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            rightWing.pivotY = rightWing.pivotY - 3;
            leftWing.pivotY = leftWing.pivotY - 3;
        }
        float factor = 0.05f;
        float angle = limbAngle + livingEntity.age * factor;
        float movement = limbDistance * factor + 0.2f;
        float wave = MathHelper.cos(angle);
        rightWing.yaw = wave * -movement - 0.8036F;
        leftWing.yaw = wave * movement + 0.9854F;
    }

    public static TexturedModelData getTexturedModelData() {
        var modelData = BipedEntityModel.getModelData(new Dilation(1), 0);
        var head = modelData.getRoot().getChild(EntityModelPartNames.HEAD);
        head.addChild("right_wing", ModelPartBuilder.create().uv(6, 13).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8, 4.0F, 0.5152F, -0.8036F, -0.387F));
        head.addChild("left_wing", ModelPartBuilder.create().uv(0, 13).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8, 4.0F, 0.6675F, 0.9854F, 0.5813F));

        return TexturedModelData.of(modelData, 32, 32);
    }


}
