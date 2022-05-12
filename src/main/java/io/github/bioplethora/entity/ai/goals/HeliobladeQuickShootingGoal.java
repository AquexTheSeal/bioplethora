package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.HeliobladeEntity;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class HeliobladeQuickShootingGoal extends Goal {

    private final HeliobladeEntity helioblade;
    public int chargeTime;

    public HeliobladeQuickShootingGoal(HeliobladeEntity heliobladeEntity) {
        this.helioblade = heliobladeEntity;
    }

    public boolean canUse() {
        return this.helioblade.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.helioblade.setQuickShooting(false);
        this.helioblade.setNoGravity(false);
    }

    public void tick() {
        LivingEntity target = this.helioblade.getTarget();
        
        if (target.distanceToSqr(this.helioblade) < 4096.0D && this.helioblade.canSee(target)) {

            this.helioblade.getLookControl().setLookAt(target, 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 160) {
                this.helioblade.teleportWithEffect(this.helioblade.getX(), this.helioblade.getY() + 5, this.helioblade.getZ());
                this.helioblade.setNoGravity(true);
            }
            if (this.chargeTime == 180) {
                this.helioblade.level.playSound(null, this.helioblade.getX(), this.helioblade.getY(), this.helioblade.getZ(), SoundEvents.BLAZE_SHOOT, this.helioblade.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
                this.shootVermilionBlade();
            }
            if (this.chargeTime == 200) {

                BlockPos blockpos = new BlockPos(this.helioblade.getX(), this.helioblade.getY() - 3, this.helioblade.getZ());

                if (!this.helioblade.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                    this.helioblade.teleportWithEffect(this.helioblade.getX(), this.helioblade.getY() - 3, this.helioblade.getZ());
                } else {
                    this.helioblade.setDeltaMovement(0, -3, 0);
                }

                this.helioblade.setNoGravity(false);
                this.chargeTime = 0;
            }
        } else {
            this.chargeTime = 0;
        }
        this.helioblade.setQuickShooting(this.chargeTime > 160);
    }
    
    public void shootVermilionBlade() {
        LivingEntity target = this.helioblade.getTarget();
        World world = this.helioblade.level;
        
        Vector3d vector3d = this.helioblade.getViewVector(1.0F);
        double d1 = target.getX() - this.helioblade.getX();
        double d2 = target.getY(0.5D) - this.helioblade.getY(0.5D);
        double d3 = target.getZ() - this.helioblade.getZ();

        if (!this.helioblade.isSilent()) {
            this.helioblade.level.playSound(null, this.helioblade.getX(), this.helioblade.getY(), this.helioblade.getZ(), SoundEvents.SHULKER_SHOOT, this.helioblade.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
        }

        VermilionBladeProjectileEntity vermilionBlade = new VermilionBladeProjectileEntity(world, this.helioblade, d1, d2, d3);
        vermilionBlade.setPos(this.helioblade.getX() + vector3d.x, this.helioblade.getY(0.5D) + 0.5D, vermilionBlade.getZ() + vector3d.z);
        world.addFreshEntity(vermilionBlade);
    }
}
