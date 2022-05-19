package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.MyliothanEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.entity.PartEntity;

public class MyliothanChargeAttackGoal extends Goal {

    public final MyliothanEntity myliothan;
    public int chargeTime;

    public MyliothanChargeAttackGoal(MyliothanEntity myliothanEntity) {
        this.myliothan = myliothanEntity;
    }

    public boolean canUse() {
        return this.myliothan.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.myliothan.setCharging(false);
    }

    public void tick() {
        LivingEntity target = this.myliothan.getTarget();
        if (target.distanceToSqr(this.myliothan) < 4096.0D /*&& this.myliothan.canSee(target)*/) {
            World world = this.myliothan.level;

            ++this.chargeTime;

            if (this.chargeTime >= 300) {

                this.myliothan.lookAt(target, 30.0F, 30.0F);

                this.myliothan.moveTargetPoint = new Vector3d(target.getX(), target.getY(0.5D), target.getZ());
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 3));

                if (this.intersectionCheck(target)) {
                    if (!this.myliothan.isSilent()) {
                        this.myliothan.level.levelEvent(1039, this.myliothan.blockPosition(), 0);
                    }
                    for (LivingEntity targets : myliothan.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(8, 3, 8))) {

                        if (targets != this.myliothan) {
                            BPEffectEntity effect = BPEntities.MYLIOTHAN_ROAR.get().create(myliothan.level);
                            effect.setOwner(myliothan);
                            effect.moveTo(target.blockPosition().offset(0, 2, 0), target.yBodyRot, 0.0F);
                            myliothan.level.addFreshEntity(effect);

                            this.myliothan.doHurtTarget(targets);
                        }
                    }
                    world.explode(this.myliothan, target.getX(), target.getY(), target.getZ(), 3F, Explosion.Mode.BREAK);

                    this.chargeTime = 0;
                }
            }

            if (this.chargeTime >= 600) {
                this.chargeTime = 0;
            }
        }

        this.myliothan.setCharging(this.chargeTime > 300);
    }

    public boolean intersectionCheck(LivingEntity target) {

        for (PartEntity part : myliothan.subEntities) {
            return part.getBoundingBox().inflate(2F).intersects(target.getBoundingBox());
        }

        return myliothan.getBoundingBox().inflate(2F).intersects(target.getBoundingBox());
    }
}
