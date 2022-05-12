package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.CrephoxlEntity;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

public class CrephoxlChargingGoal extends Goal {

    private final CrephoxlEntity crephoxl;
    public int chargeTime;

    public CrephoxlChargingGoal(CrephoxlEntity crephoxlEntity) {
        this.crephoxl = crephoxlEntity;
    }

    public boolean canUse() {
        return this.crephoxl.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.crephoxl.setCharging(false);
    }

    public void tick() {
        LivingEntity target = this.crephoxl.getTarget();
        
        if (target.distanceToSqr(this.crephoxl) < 4096.0D && this.crephoxl.canSee(target)) {

            this.crephoxl.getLookControl().setLookAt(target, 10.0F, 10.0F);
            BlockPos blockpos = this.crephoxl.blockPosition();

            ++this.chargeTime;
            if (this.chargeTime == 340) {
                if (this.crephoxl.level instanceof ServerWorld) {
                    ((ServerWorld) this.crephoxl.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.crephoxl.getX(), this.crephoxl.getY(), this.crephoxl.getZ(), 75, 0.3, 0.2, 0.2, 0.1);
                }
                this.crephoxl.level.playSound(null, this.crephoxl.getX(), this.crephoxl.getY(), this.crephoxl.getZ(), SoundEvents.SHULKER_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);
            }

            if (this.chargeTime >= 340) {

                if (this.crephoxl.level instanceof ServerWorld) {
                    ((ServerWorld) this.crephoxl.level).sendParticles(ParticleTypes.POOF, this.crephoxl.getX(), this.crephoxl.getY(), this.crephoxl.getZ(), 30, 2, 2, 2, 0);
                }

                for (LivingEntity entityIterator : this.crephoxl.level.getEntitiesOfClass(LivingEntity.class, this.crephoxl.getBoundingBox().inflate(2))) {

                    if (entityIterator != this.crephoxl) {
                        entityIterator.hurt(BPDamageSources.indirectCastration(this.crephoxl, this.crephoxl), 10);
                    }
                }

                double dirX = Math.sin(Math.toRadians(this.crephoxl.yRot + 180));
                double dirY = Math.sin(Math.toRadians(0 - this.crephoxl.xRot));
                double dirZ = Math.cos(Math.toRadians(this.crephoxl.yRot));

                this.crephoxl.lookAt(target, 10.0F, 10.0F);
                this.crephoxl.setDeltaMovement(dirX, dirY, dirZ);

                if (this.crephoxl.getBoundingBox().inflate(2F).intersects(target.getBoundingBox())) {
                    this.crephoxl.doHurtTarget(target);
                    this.crephoxl.level.explode(this.crephoxl, target.getX(), target.getY(), target.getZ(), 1.5F, Explosion.Mode.BREAK);

                    this.chargeTime = 0;
                }
            }

            if (this.chargeTime == 420) {
                this.chargeTime = 0;
            }

            this.crephoxl.setCharging(this.chargeTime > 300);

        } else {
            this.stop();
        }
    }
}
