package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.projectile.WindblazeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class AlphemKingRangedAttackGoal extends Goal {

    private final AlphemKingEntity king;
    public int chargeTime;

    public AlphemKingRangedAttackGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return this.king.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.king.setCharging(false);
    }

    public void tick() {
        LivingEntity target = this.king.getTarget();
        if (target.distanceToSqr(this.king) < 4096.0D && this.king.canSee(target)) {
            World world = this.king.level;

            this.king.getLookControl().setLookAt(king.getTarget(), 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 100 && !this.king.isSilent()) {
                this.king.level.playSound(null, this.king.getX(), this.king.getY(), this.king.getZ(), SoundEvents.BEACON_ACTIVATE, this.king.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
            }

            if (this.chargeTime == 120) {this.shootBlaze(world, target);}

            if (this.chargeTime == 130) {this.shootBlaze(world, target);}

            if (this.chargeTime == 140) {this.shootBlaze(world, target);}

            if (this.chargeTime == 150) {this.shootBlaze(world, target);}

            if (this.chargeTime == 160) {this.shootBlaze(world, target);}

            if (this.chargeTime == 170) {
                this.shootBlaze(world, target);
                this.chargeTime = 0;
            }
        }

        this.king.setCharging(this.chargeTime > 100);
    }

    public void shootBlaze(World world, LivingEntity target) {
        double d1 = target.getX() - this.king.getX();
        double d2 = target.getY(0.5D) - this.king.getY(0.5D);
        double d3 = target.getZ() - this.king.getZ();
        if (!this.king.isSilent()) {
            this.king.level.playSound(null, this.king.getX(), this.king.getY(), this.king.getZ(), SoundEvents.SHULKER_SHOOT, this.king.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
        }

        WindblazeEntity windblazeEntity = new WindblazeEntity(world, this.king, d1, d2, d3);
        windblazeEntity.setPos(this.king.getX(), this.king.getY(0.5D) + 0.5D, windblazeEntity.getZ());
        world.addFreshEntity(windblazeEntity);
    }
}
