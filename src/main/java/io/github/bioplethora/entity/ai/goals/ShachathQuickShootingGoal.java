package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ShachathQuickShootingGoal extends Goal {

    private final ShachathEntity shachath;
    public int chargeTime;

    public ShachathQuickShootingGoal(ShachathEntity shachathEntity) {
        this.shachath = shachathEntity;
    }

    public boolean canUse() {
        return this.shachath.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.shachath.setQuickShooting(false);
        this.shachath.setNoGravity(false);
    }

    public void tick() {
        LivingEntity target = this.shachath.getTarget();
        
        if (target.distanceToSqr(this.shachath) < 4096.0D && this.shachath.canSee(target)) {

            this.shachath.getLookControl().setLookAt(target, 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 160) {
                this.shachath.teleportWithEffect(this.shachath.getX(), this.shachath.getY() + 5, this.shachath.getZ());
                this.shachath.setNoGravity(true);
            }
            if (this.chargeTime == 180) {
                this.shachath.level.playSound(null, this.shachath.getX(), this.shachath.getY(), this.shachath.getZ(), SoundEvents.BLAZE_SHOOT, this.shachath.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
                this.shootVermilionBlade();
            }
            if (this.chargeTime == 200) {

                BlockPos blockpos = new BlockPos(this.shachath.getX(), this.shachath.getY() - 3, this.shachath.getZ());

                if (!this.shachath.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                    this.shachath.teleportWithEffect(this.shachath.getX(), this.shachath.getY() - 3, this.shachath.getZ());
                } else {
                    this.shachath.setDeltaMovement(0, -3, 0);
                }

                this.shachath.setNoGravity(false);
                this.chargeTime = 0;
            }
        } else {
            this.chargeTime = 0;
        }
        this.shachath.setQuickShooting(this.chargeTime > 160);
    }
    
    public void shootVermilionBlade() {
        LivingEntity target = this.shachath.getTarget();
        World world = this.shachath.level;
        
        Vector3d vector3d = this.shachath.getViewVector(1.0F);
        double d1 = target.getX() - this.shachath.getX();
        double d2 = target.getY(0.5D) - this.shachath.getY(0.5D);
        double d3 = target.getZ() - this.shachath.getZ();

        if (!this.shachath.isSilent()) {
            this.shachath.level.playSound(null, this.shachath.getX(), this.shachath.getY(), this.shachath.getZ(), SoundEvents.SHULKER_SHOOT, this.shachath.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
        }

        VermilionBladeProjectileEntity vermilionBlade = new VermilionBladeProjectileEntity(world, this.shachath, d1, d2, d3);
        vermilionBlade.setPos(this.shachath.getX() + vector3d.x, this.shachath.getY(0.5D) + 0.5D, vermilionBlade.getZ() + vector3d.z);
        world.addFreshEntity(vermilionBlade);
    }
}
