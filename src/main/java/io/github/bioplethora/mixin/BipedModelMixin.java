package io.github.bioplethora.mixin;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PiglinModel;
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

    @Inject(method = "Lnet/minecraft/client/renderer/entity/model/BipedModel;poseRightArm(Lnet/minecraft/entity/LivingEntity;)V", at = @At(value = "HEAD"))
    private void poseRight(T living, CallbackInfo ci) {

        if (BPConfig.COMMON.enableCustomModelPositions.get()) {

            boolean IQMainHandR = living.getMainHandItem().getItem() instanceof InfernalQuarterstaffItem;
            boolean IQOffHandL = living.getOffhandItem().getItem() instanceof InfernalQuarterstaffItem;
            boolean IQBothHands = IQMainHandR && IQOffHandL;

            if (living instanceof PlayerEntity) {
                if (!(living.getUseItem().getItem() instanceof ShieldItem)) {

                    if ((IQMainHandR && !IQOffHandL) || IQBothHands) {
                        rightArm.xRot = -1F;
                        rightArm.zRot = 1F;

                        leftArm.xRot = -40F;
                        leftArm.yRot = -22.5F;
                        leftArm.zRot = -22.5F;

                    } else if (IQOffHandL) {
                        leftArm.xRot = -1F;
                        leftArm.zRot = 1F;

                        rightArm.xRot = -40F;
                        rightArm.yRot = -22.5F;
                        rightArm.zRot = -22.5F;
                    }
                }
            }
        }
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/model/BipedModel;setupAttackAnimation(Lnet/minecraft/entity/LivingEntity;F)V", at = @At("HEAD"))
    public void setupAttack(T livingEntity, float a, CallbackInfo ci) {

        if (BPConfig.COMMON.enableCustomModelAnimations.get()) {
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

                        this.body.yRot = -MathHelper.sin(this.attackTime * (float) Math.PI) * 360;
                        this.head.yRot = MathHelper.cos(this.attackTime * (float) Math.PI) * 360;
                        modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2)) * 2;
                        modelrenderer.xRot += MathHelper.sin(this.attackTime * (float) Math.PI) * 0.75F;
                    }
                }
            }
        }
    }
}
