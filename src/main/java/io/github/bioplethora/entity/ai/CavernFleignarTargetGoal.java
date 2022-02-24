package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BioplethoraEffects;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.math.AxisAlignedBB;

public class CavernFleignarTargetGoal extends TargetGoal {

    private final CavernFleignarEntity fleignar;
    private static final EntityPredicate CONDITION = (new EntityPredicate()).range(8.0D).allowSameTeam();

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
                boolean getTag = EntityTypeTags.getAllTags().getTagOrEmpty(BioplethoraTags.Entities.FLEIGNAR_TARGETS.getName()).contains(targetCandidate.getType());
                if (validCheck(targetCandidate) || getTag) {
                    if (!targetCandidate.hasEffect(BioplethoraEffects.SPIRIT_MANIPULATION.get())) {
                        fleignar.setTarget(targetCandidate);
                    }
                }
            }
        }

        if (target != null) {
            fleignar.getTarget().addEffect(new EffectInstance(Effects.GLOWING, 5, 0));

            double distance = this.fleignar.distanceToSqr(target);
            if (distance >= 4D) {
                fleignar.setTarget(null);
            }

            if (!validCheck(target)) {
                fleignar.setTarget(null);
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
