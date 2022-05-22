package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.MyliothanEntity;
import io.github.bioplethora.entity.projectile.AbyssalScalesEntity;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class MyliothanShakeGoal extends Goal {

    public final MyliothanEntity myliothan;
    public int chargeTime;
    public int shootTime;

    public MyliothanShakeGoal(MyliothanEntity myliothanEntity) {
        this.myliothan = myliothanEntity;
    }

    public boolean canUse() {
        return this.myliothan.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
        this.shootTime = 0;
    }

    public void stop() {
        this.myliothan.setShaking(false);
    }

    public void tick() {
        LivingEntity target = this.myliothan.getTarget();
        if (target.distanceToSqr(this.myliothan) < 4096.0D /*&& this.myliothan.canSee(target)*/) {
            ++this.chargeTime;

            if (this.chargeTime >= 200) {
                ++this.shootTime;
                if (this.shootTime >= 7) {
                    this.shootScaleAtXRot(-45.0F);
                    this.shootScaleAtXRot(-22.5F);
                    this.shootScaleAtXRot(-11.25F);
                    this.shootScaleAtXRot(-5.625F);
                    this.shootTime = 0;
                }
            }

            if (this.chargeTime >= 300) {
                this.chargeTime = -myliothan.getRandom().nextInt(100);
                this.shootTime = 0;
            }
        }

        this.myliothan.setShaking(this.chargeTime > 300);
    }

    public void shootScaleAtXRot(float xRot) {
        shootScale(xRot, 0);
        shootScale(xRot, 180);
        shootScale(xRot, -22.5F); shootScale(xRot, 22.5F);
        shootScale(xRot, -45); shootScale(xRot, 45);
        shootScale(xRot, -67.5F); shootScale(xRot, 67.5F);
        shootScale(xRot, -90); shootScale(xRot, 90);
        shootScale(xRot, -112.5F); shootScale(xRot, 112.5F);
        shootScale(xRot, -135); shootScale(xRot, 135);
        shootScale(xRot, -157.5F); shootScale(xRot, 157.5F);
    }

    public void shootScale(float xRot, float yRot) {
        if (!myliothan.level.isClientSide) {
            AbyssalScalesEntity scales = new AbyssalScalesEntity(myliothan.level, myliothan);
            scales.setItem(new ItemStack(BPItems.ABYSSAL_SCALES.get()));
            scales.setOwner(myliothan);
            scales.shootFromRotation(myliothan, xRot, yRot + (new Random().nextFloat() * 22.5F), 0.0F, 1.5F + myliothan.getRandom().nextFloat(), 1.0F);
            myliothan.level.addFreshEntity(scales);
        }
    }
}
