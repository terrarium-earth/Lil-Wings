package com.toadstoolstudios.lilwings.client.patron;

import com.toadstoolstudios.lilwings.LilWings;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;

public class PatreonButterflyModel extends HumanoidModel<AbstractClientPlayer> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(LilWings.MODID, "patreon_butterfly"), "main");

    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public PatreonButterflyModel(ModelPart root) {
        super(root);

        var head = root.getChild(PartNames.HEAD);
        rightWing = head.getChild("right_wing");
        leftWing = head.getChild("left_wing");
    }

    public void prepare() {
        setAllVisible(false);
        head.visible = true;
        rightWing.visible = true;
        leftWing.visible = true;
    }

    @Override
    public void setupAnim(AbstractClientPlayer livingEntity, float limbAngle, float limbDistance, float h, float i, float j) {
        super.setupAnim(livingEntity, limbAngle, limbDistance, h, i, j);
        if(!livingEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            rightWing.y = rightWing.y - 3;
            leftWing.y = leftWing.y - 3;
        }
        float factor = 0.05f;
        float angle = limbAngle + livingEntity.tickCount * factor;
        float movement = limbDistance * factor + 0.2f;
        float wave = Mth.cos(angle);
        rightWing.yRot = wave * -movement - 0.8036F;
        leftWing.yRot = wave * movement + 0.9854F;
    }


    public static LayerDefinition getTexturedModelData() {
        var modelData = HumanoidModel.createMesh(new CubeDeformation(1), 0);
        var head = modelData.getRoot().getChild(PartNames.HEAD);
        head.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(6, 13).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8, 4.0F, 0.5152F, -0.8036F, -0.387F));
        head.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8, 4.0F, 0.6675F, 0.9854F, 0.5813F));

        return LayerDefinition.create(modelData, 32, 32);
    }


}
