package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class PrimordialRingOwnerHurtByTargetGoal extends TargetGoal {
    private final SummonableMonsterEntity tameAnimal;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public PrimordialRingOwnerHurtByTargetGoal(SummonableMonsterEntity p_i1667_1_) {
        super(p_i1667_1_, false);
        this.tameAnimal = p_i1667_1_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT);
        }
    }

    public void start() {
        if (this.ownerLastHurtBy instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) this.ownerLastHurtBy).getOwner() != this.tameAnimal.getOwner()) {
                this.mob.setTarget(this.ownerLastHurtBy);
            }
        } else {
            this.mob.setTarget(this.ownerLastHurtBy);
        }
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
