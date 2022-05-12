package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class PrimordialRingOwnerHurtTargetGoal extends TargetGoal {
    private final SummonableMonsterEntity tameAnimal;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public PrimordialRingOwnerHurtTargetGoal(SummonableMonsterEntity p_i1668_1_) {
        super(p_i1668_1_, false);
        this.tameAnimal = p_i1668_1_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, EntityPredicate.DEFAULT);
        }
    }

    public void start() {
        if (this.ownerLastHurt instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) this.ownerLastHurt).getOwner() != this.tameAnimal.getOwner()) {
                this.mob.setTarget(this.ownerLastHurt);
            }
        } else {
            this.mob.setTarget(this.ownerLastHurt);
        }
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
