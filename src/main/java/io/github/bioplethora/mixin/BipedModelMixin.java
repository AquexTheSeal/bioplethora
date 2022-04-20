package io.github.bioplethora.mixin;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedModel.class)
public abstract class BipedModelMixin<T extends LivingEntity> extends AgeableModel<T> {

    @Shadow public ModelRenderer rightArm;
    @Shadow public ModelRenderer leftArm;
    @Shadow public ModelRenderer head;
    @Shadow public ModelRenderer body;
    @Shadow public ModelRenderer rightLeg;
    @Shadow public ModelRenderer leftLeg;
    @Shadow public ModelRenderer hat;

    @Shadow protected abstract HandSide getAttackArm(T pEntity);
    @Shadow protected abstract ModelRenderer getArm(HandSide pSide);

    @Shadow protected abstract float rotlerpRad(float pAngle, float pMaxAngle, float pMul);

    @Shadow public float swimAmount;

    @Shadow public boolean crouching;

    @Shadow protected abstract void poseLeftArm(T p_241655_1_);

    @Shadow public BipedModel.ArmPose leftArmPose;

    @Shadow public BipedModel.ArmPose rightArmPose;

    @Shadow protected abstract float quadraticArmUpdate(float pLimbSwing);

    @Shadow protected abstract void poseRightArm(T p_241654_1_);

    @Shadow protected abstract void setupAttackAnimation(T p_230486_1_, float p_230486_2_);

    @Inject(method = "poseRightArm", at = @At(value = "HEAD"))
    private void poseRight(T living, CallbackInfo ci) {

        if (living instanceof PlayerEntity) {
            if (living.getMainHandItem().getItem() instanceof InfernalQuarterstaffItem) {
                if (!(living.getUseItem().getItem() instanceof ShieldItem)) {

                    rightArm.xRot = -1F;
                    rightArm.zRot = 1F;

                    leftArm.xRot = -40F;
                    leftArm.yRot = -22.5F;
                    leftArm.zRot = -22.5F;
                }
            }
        }
    }

    @Inject(method = "setupAttackAnimation", at = @At("TAIL"))
    public void setupAttack(T livingEntity, float a, CallbackInfo ci) {

        if (livingEntity instanceof PlayerEntity) {
            if (!(this.attackTime <= 0.0F)) {
                if (livingEntity.getMainHandItem().getItem() instanceof InfernalQuarterstaffItem) {

                    HandSide handside = this.getAttackArm(livingEntity);
                    ModelRenderer modelrenderer = this.getArm(handside);
                    float f;

                    f = 1.0F - this.attackTime;
                    f = f * f;
                    f = f * f;
                    f = 1.0F - f;

                    float f1 = MathHelper.sin(f * (float) Math.PI);
                    float f2 = MathHelper.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;

                    modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2)) * 2;
                    modelrenderer.xRot += MathHelper.sin(this.attackTime * (float) Math.PI) * 0.75F;
                }
            }
        }
    }

    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.hat);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pEntity.getFallFlyingTicks() > 4;
        boolean flag1 = pEntity.isVisuallySwimming();
        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        if (flag) {
            this.head.xRot = (-(float)Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float)Math.PI / 4F));
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, pHeadPitch * ((float)Math.PI / 180F));
            }
        } else {
            this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
        }

        this.body.yRot = 0.0F;
        this.rightArm.z = 0.0F;
        this.rightArm.x = -5.0F;
        this.leftArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        float f = 1.0F;
        if (flag) {
            f = (float)pEntity.getDeltaMovement().lengthSqr();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 2.0F * pLimbSwingAmount * 0.5F / f;
        this.leftArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 2.0F * pLimbSwingAmount * 0.5F / f;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount / f;
        this.leftLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount / f;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.zRot = 0.0F;
        if (this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        boolean flag2 = pEntity.getMainArm() == HandSide.RIGHT;
        boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
        if (flag2 != flag3) {
            this.poseLeftArm(pEntity);
            this.poseRightArm(pEntity);
        } else {
            this.poseRightArm(pEntity);
            this.poseLeftArm(pEntity);
        }

        this.setupAttackAnimation(pEntity, pAgeInTicks);
        if (this.crouching) {
            this.body.xRot = 0.5F;
            this.rightArm.xRot += 0.4F;
            this.leftArm.xRot += 0.4F;
            this.rightLeg.z = 4.0F;
            this.leftLeg.z = 4.0F;
            this.rightLeg.y = 12.2F;
            this.leftLeg.y = 12.2F;
            this.head.y = 4.2F;
            this.body.y = 3.2F;
            this.leftArm.y = 5.2F;
            this.rightArm.y = 5.2F;
        } else {
            this.body.xRot = 0.0F;
            this.rightLeg.z = 0.1F;
            this.leftLeg.z = 0.1F;
            this.rightLeg.y = 12.0F;
            this.leftLeg.y = 12.0F;
            this.head.y = 0.0F;
            this.body.y = 0.0F;
            this.leftArm.y = 2.0F;
            this.rightArm.y = 2.0F;
        }

        ModelHelper.bobArms(this.rightArm, this.leftArm, pAgeInTicks);
        if (this.swimAmount > 0.0F) {
            float f1 = pLimbSwing % 26.0F;
            HandSide handside = this.getAttackArm(pEntity);
            float f2 = handside == HandSide.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            float f3 = handside == HandSide.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            if (f1 < 14.0F) {
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, 0.0F);
                this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, 0.0F);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
                this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float)Math.PI + 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
                this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float)Math.PI - 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
            } else if (f1 >= 14.0F && f1 < 22.0F) {
                float f6 = (f1 - 14.0F) / 8.0F;
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float)Math.PI / 2F) * f6);
                this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float)Math.PI / 2F) * f6);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
                this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
            } else if (f1 >= 22.0F && f1 < 26.0F) {
                float f4 = (f1 - 22.0F) / 4.0F;
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
                this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
                this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float)Math.PI);
                this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float)Math.PI);
            }

            float f7 = 0.3F;
            float f5 = 0.33333334F;
            this.leftLeg.xRot = MathHelper.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * MathHelper.cos(pLimbSwing * 0.33333334F + (float)Math.PI));
            this.rightLeg.xRot = MathHelper.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * MathHelper.cos(pLimbSwing * 0.33333334F));
        }

        this.hat.copyFrom(this.head);
    }
}
