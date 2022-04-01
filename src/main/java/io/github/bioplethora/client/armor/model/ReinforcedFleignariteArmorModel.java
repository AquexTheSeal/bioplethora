package io.github.bioplethora.client.armor.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ReinforcedFleignariteArmorModel<T extends LivingEntity> extends BipedModel<T> {
	public final ModelRenderer head;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	public final ModelRenderer body;
	public final ModelRenderer left_shoe;
	private final ModelRenderer cube_r3;
	public final ModelRenderer right_shoe;
	private final ModelRenderer cube_r4;
	public final ModelRenderer left_arm;
	public final ModelRenderer right_arm;

	public ReinforcedFleignariteArmorModel() {
		super(1);
		texWidth = 64;
		texHeight = 32;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);
		head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -1.0F, 0.0F);
		head.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.6863F, -0.1396F, -0.1682F);
		cube_r1.texOffs(56, 18).addBox(-4.5F, -13.0F, -5.0F, 1.0F, 11.0F, 3.0F, 0.0F, true);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, -1.0F, 0.0F);
		head.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.6863F, 0.1396F, 0.1682F);
		cube_r2.texOffs(56, 18).addBox(3.5F, -13.0F, -5.0F, 1.0F, 11.0F, 3.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.75F, false);

		left_shoe = new ModelRenderer(this);
		left_shoe.setPos(2.0F, 12.0F, 0.0F);
		left_shoe.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, true);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 0.0F, 0.0F);
		left_shoe.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -1.5708F, 0.0F);
		cube_r3.texOffs(56, 0).addBox(-1.5F, 5.5F, -3.5F, 3.0F, 7.0F, 1.0F, 0.0F, false);

		right_shoe = new ModelRenderer(this);
		right_shoe.setPos(-2.0F, 12.0F, 0.0F);
		right_shoe.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(4.0F, 0.0F, 0.0F);
		right_shoe.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, -1.5708F, 0.0F);
		cube_r4.texOffs(56, 0).addBox(-1.5F, 5.5F, 6.5F, 3.0F, 7.0F, 1.0F, 0.0F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setPos(5.0F, 2.0F, 0.0F);
		left_arm.texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, true);
		left_arm.texOffs(56, 18).addBox(3.5F, -5.0F, -1.5F, 1.0F, 11.0F, 3.0F, 0.0F, true);

		right_arm = new ModelRenderer(this);
		right_arm.setPos(-5.0F, 2.0F, 0.0F);
		right_arm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);
		right_arm.texOffs(56, 18).addBox(-4.5F, -5.0F, -1.5F, 1.0F, 11.0F, 3.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		left_shoe.render(matrixStack, buffer, packedLight, packedOverlay);
		right_shoe.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}