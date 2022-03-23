package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BPEffects;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.math.AxisAlignedBB;

public class CavernFleignarTargetGoal extends TargetGoal {

    private final CavernFleignarEntity fleignar;
    private static final EntityPredicate CONDITION = (new EntityPredicate()).range(16.0D).allowSameTeam();

    public CavernFleignarTargetGoal(CavernFleignarEntity fleignar, boolean mustSee) {
        super(fleignar, mustSee, false);
        this.fleignar = fleignar;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        LivingEntity target = this.fleignar.getTarget();

        if (target == null) {
            AxisAlignedBB searchRadius = this.fleignar.getBoundingBox().inflate(12.0D, 4.0D, 12.0D);
            LivingEntity targetCandidate = fleignar.level.getNearestEntity(LivingEntity.class, CONDITION, fleignar, fleignar.getX(), fleignar.getY(), fleignar.getZ(), searchRadius);

            if (targetCandidate != null) {
                boolean getTag = EntityTypeTags.getAllTags().getTagOrEmpty(BPTags.Entities.FLEIGNAR_TARGETS.getName()).contains(targetCandidate.getType());
                if (validCheck(targetCandidate) && getTag) {
                    if (!targetCandidate.hasEffect(BPEffects.SPIRIT_MANIPULATION.get())) {
                        fleignar.setTarget(targetCandidate);
                    }
                }
            }
        }
    }

    public boolean validCheck(LivingEntity target) {
        if (target.isAlive() && !target.isSpectator()) {
            return !(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative());
        } else {
            return false;
        }
    }
}
