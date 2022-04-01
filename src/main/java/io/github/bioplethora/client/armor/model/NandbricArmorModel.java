package io.github.bioplethora.client.armor.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class NandbricArmorModel<T extends LivingEntity> extends BipedModel<T> {
    public final ModelRenderer helmet;
    public final ModelRenderer rightscales;
    public final ModelRenderer scale0;
    public final ModelRenderer scale0_r1;
    public final ModelRenderer scale1;
    public final ModelRenderer scale1_r1;
    public final ModelRenderer scale2;
    public final ModelRenderer scale2_r1;
    public final ModelRenderer leftscales;
    public final ModelRenderer scale3;
    public final ModelRenderer scale3_r1;
    public final ModelRenderer scale4;
    public final ModelRenderer scale4_r1;
    public final ModelRenderer scale5;
    public final ModelRenderer scale5_r1;
    public final ModelRenderer chestplate;
    public final ModelRenderer rightarm;
    public final ModelRenderer leftarm;
    public final ModelRenderer rightboot;
    public final ModelRenderer leftboot;

    public NandbricArmorModel() {
        super(1);
        texWidth = 80;
        texHeight = 32;

        helmet = new ModelRenderer(this);
        helmet.setPos(0.0F, 0.0F, 0.0F);
        helmet.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);

        rightscales = new ModelRenderer(this);
        rightscales.setPos(-5.0F, -9.0F, 0.0F);
        helmet.addChild(rightscales);


        scale0 = new ModelRenderer(this);
        scale0.setPos(6.0F, 4.0F, 2.0F);
        rightscales.addChild(scale0);


        scale0_r1 = new ModelRenderer(this);
        scale0_r1.setPos(-4.9486F, -3.4895F, -6.1643F);
        scale0.addChild(scale0_r1);
        setRotationAngle(scale0_r1, 0.5404F, 1.0769F, 0.8892F);
        scale0_r1.texOffs(56, 16).addBox(-6.0F, -0.5F, 0.1643F, 6.0F, 3.0F, 1.0F, 0.25F, false);

        scale1 = new ModelRenderer(this);
        scale1.setPos(2.35F, 6.0F, -3.0F);
        rightscales.addChild(scale1);


        scale1_r1 = new ModelRenderer(this);
        scale1_r1.setPos(-2.25F, -2.75F, -1.5F);
        scale1.addChild(scale1_r1);
        setRotationAngle(scale1_r1, 0.0F, 1.1345F, 0.0F);
        scale1_r1.texOffs(56, 20).addBox(-4.0685F, -1.5F, 0.0328F, 4.0F, 3.0F, 1.0F, 0.25F, false);

        scale2 = new ModelRenderer(this);
        scale2.setPos(2.35F, 5.0F, 1.0F);
        rightscales.addChild(scale2);


        scale2_r1 = new ModelRenderer(this);
        scale2_r1.setPos(-3.0F, -1.5F, 0.5F);
        scale2.addChild(scale2_r1);
        setRotationAngle(scale2_r1, 0.0F, 1.1345F, 0.0F);
        scale2_r1.texOffs(56, 20).addBox(-2.0F, -2.75F, -0.5F, 4.0F, 3.0F, 1.0F, 0.25F, false);

        leftscales = new ModelRenderer(this);
        leftscales.setPos(5.0F, -9.0F, 0.0F);
        helmet.addChild(leftscales);


        scale3 = new ModelRenderer(this);
        scale3.setPos(-6.0F, 4.0F, 2.0F);
        leftscales.addChild(scale3);


        scale3_r1 = new ModelRenderer(this);
        scale3_r1.setPos(4.9486F, -3.4895F, -6.1643F);
        scale3.addChild(scale3_r1);
        setRotationAngle(scale3_r1, 0.5404F, -1.0769F, -0.8892F);
        scale3_r1.texOffs(56, 16).addBox(0.0F, -0.5F, 0.1643F, 6.0F, 3.0F, 1.0F, 0.25F, true);

        scale4 = new ModelRenderer(this);
        scale4.setPos(-2.35F, 6.0F, -3.0F);
        leftscales.addChild(scale4);


        scale4_r1 = new ModelRenderer(this);
        scale4_r1.setPos(2.25F, -2.75F, -1.5F);
        scale4.addChild(scale4_r1);
        setRotationAngle(scale4_r1, 0.0F, -1.1345F, 0.0F);
        scale4_r1.texOffs(56, 20).addBox(0.0685F, -1.5F, 0.0328F, 4.0F, 3.0F, 1.0F, 0.25F, true);

        scale5 = new ModelRenderer(this);
        scale5.setPos(-2.35F, 5.0F, 1.0F);
        leftscales.addChild(scale5);


        scale5_r1 = new ModelRenderer(this);
        scale5_r1.setPos(3.0F, -1.5F, 0.5F);
        scale5.addChild(scale5_r1);
        setRotationAngle(scale5_r1, 0.0F, -1.1345F, 0.0F);
        scale5_r1.texOffs(56, 20).addBox(-2.0F, -2.75F, -0.5F, 4.0F, 3.0F, 1.0F, 0.25F, true);

        chestplate = new ModelRenderer(this);
        chestplate.setPos(0.0F, 0.0F, 0.0F);
        chestplate.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.01F, false);

        rightarm = new ModelRenderer(this);
        rightarm.setPos(-5.0F, 2.0F, 0.0F);
        rightarm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F, false);
        rightarm.texOffs(56, 24).addBox(-4.0F, 0.3F, -2.0F, 5.0F, 3.0F, 4.0F, 1.002F, false);

        leftarm = new ModelRenderer(this);
        leftarm.setPos(5.0F, 2.0F, 0.0F);
        leftarm.texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F, true);
        leftarm.texOffs(56, 24).addBox(-1.0F, 0.3F, -2.0F, 5.0F, 3.0F, 4.0F, 1.002F, true);

        rightboot = new ModelRenderer(this);
        rightboot.setPos(-1.9F, 12.0F, 0.0F);
        rightboot.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F, false);

        leftboot = new ModelRenderer(this);
        leftboot.setPos(1.9F, 12.0F, 0.0F);
        leftboot.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F, true);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        helmet.render(matrixStack, buffer, packedLight, packedOverlay);
        chestplate.render(matrixStack, buffer, packedLight, packedOverlay);
        rightarm.render(matrixStack, buffer, packedLight, packedOverlay);
        leftarm.render(matrixStack, buffer, packedLight, packedOverlay);
        rightboot.render(matrixStack, buffer, packedLight, packedOverlay);
        leftboot.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
