package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BPAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class HostileTamableNearestAttackableGoal<T extends LivingEntity> extends NearestAttackableTargetGoal {

    public HostileTamableNearestAttackableGoal(MobEntity entity, Class<T> aClass, boolean b) {
        super(entity, aClass, b);
    }

    @Override
    public boolean canUse() {
        if (mob instanceof BPAnimalEntity) {
            if (((BPAnimalEntity) mob).getOwner() == null) {
                return super.canUse();
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (mob instanceof BPAnimalEntity) {
            if (((BPAnimalEntity) mob).getOwner() == null) {
                return super.canContinueToUse();
            }
        }
        return false;
    }
}
