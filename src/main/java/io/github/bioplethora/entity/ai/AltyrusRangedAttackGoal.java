package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.projectile.UltimateBellophiteClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class AltyrusRangedAttackGoal extends Goal {

    private final AltyrusEntity altyrus;
    public boolean goingUp;
    public int chargeTime;

    public AltyrusRangedAttackGoal(AltyrusEntity altyrusEntity) {
        this.altyrus = altyrusEntity;
    }

    public boolean canUse() {
        return (this.altyrus.getTarget() != null);
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.altyrus.setCharging(false);
        this.goingUp = false;
    }

    public boolean canContinueToUse() {
        return this.altyrus.getTarget() != null;
    }

    public void tick() {
        LivingEntity target = this.altyrus.getTarget();

        if (target != null && target.distanceToSqr(this.altyrus) < 4096.0D /*&& this.altyrus.canSee(target)*/) {

            ++this.chargeTime;

            World world = this.altyrus.level;
            BlockPos pos = new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ());

            if (this.goingUp) {
                this.altyrus.setDeltaMovement(this.altyrus.getDeltaMovement().add(0, 0.05, 0));
            }


                if (this.chargeTime == 10) {
                    this.altyrus.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, (float) 1, (float) 1);
                    this.goingUp = true;
                }

                if (this.chargeTime == 30) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 35) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 40) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                    this.goingUp = false;
                }
                if (this.chargeTime == 45) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 50) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);

                    this.chargeTime = -100;
                }
        }

        this.altyrus.setCharging(this.chargeTime > 10);
    }

    public void shootProjectile(World world) {

        int rad = 128;
        AxisAlignedBB aabb = new AxisAlignedBB(altyrus.getX() - (rad / 2d), altyrus.getY() - (rad / 2d), altyrus.getZ() - (rad / 2d), altyrus.getX() + (rad / 2d), altyrus.getY() + (rad / 2d), altyrus.getZ() + (rad / 2d));

        for (LivingEntity targetCandidates : world.getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (isValidTarget(targetCandidates)) {
                Vector3d vector3d = this.altyrus.getViewVector(1.0F);
                double d2 = targetCandidates.getX() - (this.altyrus.getX() + vector3d.x * 4.0D);
                double d3 = targetCandidates.getY(0.5D) - (0.5D + this.altyrus.getY(0.5D));
                double d4 = targetCandidates.getZ() - (this.altyrus.getZ() + vector3d.z * 4.0D);
                UltimateBellophiteClusterEntity ultimateBellophiteClusterEntity = new UltimateBellophiteClusterEntity(world, this.altyrus, d2, d3, d4);
                ultimateBellophiteClusterEntity.setPos(this.altyrus.getX() + vector3d.x * 4.0D, this.altyrus.getY(0.5D) + 0.5D, ultimateBellophiteClusterEntity.getZ() + vector3d.z * 4.0D);

                world.addFreshEntity(ultimateBellophiteClusterEntity);
            }
        }
    }

    public boolean isValidTarget(LivingEntity target) {
        if (target instanceof MobEntity) {
            if (target instanceof AlphemEntity || target instanceof AlphemKingEntity) {
                return true;
            } else {
                return ((MobEntity) target).getTarget() == this.altyrus;
            }
        } else {
            return target instanceof PlayerEntity && EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target);
        }
    }
}
