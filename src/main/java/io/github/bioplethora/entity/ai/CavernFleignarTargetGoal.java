package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.math.AxisAlignedBB;

public class CavernFleignarTargetGoal extends TargetGoal {

    private final CavernFleignarEntity fleignar;
    private static final EntityPredicate CONDITION = EntityPredicate.DEFAULT;

    public CavernFleignarTargetGoal(CavernFleignarEntity fleignar, boolean mustSee) {
        super(fleignar, mustSee, false);
        this.fleignar = fleignar;
    }

    @Override
    public boolean canUse() {
        return fleignar.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return fleignar.getTarget() == null;
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
                if ((targetCandidate instanceof PlayerEntity && !((PlayerEntity) targetCandidate).isCreative() && !targetCandidate.isSpectator()) || getTag) {
                    fleignar.setTarget(targetCandidate);
                }
            }
        }
    }
}
