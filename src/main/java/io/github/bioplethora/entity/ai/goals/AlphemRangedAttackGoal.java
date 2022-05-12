package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.projectile.WindblazeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class AlphemRangedAttackGoal extends Goal {

    private final AlphemEntity alphem;
    public int chargeTime;

    public AlphemRangedAttackGoal(AlphemEntity alphemEntity) {
        this.alphem = alphemEntity;
    }

    public boolean canUse() {
        return this.alphem.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.alphem.setCharging(false);
    }

    public void tick() {
        LivingEntity livingentity = this.alphem.getTarget();
        if (livingentity.distanceToSqr(this.alphem) < 4096.0D && this.alphem.canSee(livingentity)) {
            World world = this.alphem.level;

            this.alphem.getLookControl().setLookAt(alphem.getTarget(), 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 10 && !this.alphem.isSilent()) {
                this.alphem.level.playSound(null, this.alphem.getX(), this.alphem.getY(), this.alphem.getZ(), SoundEvents.BEACON_ACTIVATE, this.alphem.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
            }
            if (this.chargeTime == 20) {

                double d1 = livingentity.getX() - this.alphem.getX();
                double d2 = livingentity.getY(0.5D) - this.alphem.getY(0.5D);
                double d3 = livingentity.getZ() - this.alphem.getZ();
                if (!this.alphem.isSilent()) {
                    this.alphem.level.playSound(null, this.alphem.getX(), this.alphem.getY(), this.alphem.getZ(), SoundEvents.SHULKER_SHOOT, this.alphem.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
                }

                WindblazeEntity windblazeEntity = new WindblazeEntity(world, this.alphem, d1, d2, d3);
                windblazeEntity.setPos(this.alphem.getX(), this.alphem.getY(0.5D) + 0.5D, windblazeEntity.getZ());
                world.addFreshEntity(windblazeEntity);
                this.chargeTime = -40;
            }
        } else {
            this.chargeTime = 0;
        }
        this.alphem.setCharging(this.chargeTime > 10);
    }
}
