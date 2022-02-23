package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AlphemKingJumpGoal extends Goal {

    private final AlphemKingEntity king;
    public int jumpTime;

    public AlphemKingJumpGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return this.king.getTarget() != null;
    }

    public void start() {
        this.jumpTime = 0;
    }

    public void stop() {
        this.king.setJumping(false);
    }

    public boolean canContinueToUse() {
        return this.king.getTarget() != null;
    }

    public void tick() {
        LivingEntity target = this.king.getTarget();

        if (target != null && target.distanceToSqr(this.king) < 4096.0D && this.king.canSee(target)) {

            ++this.jumpTime;

            World world = this.king.level;
            BlockPos pos = new BlockPos((int) this.king.getX(), (int) this.king.getY(), (int) this.king.getZ());

            if (this.jumpTime == 520) {

                this.king.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);

                /*this.king.knockback(1.5F,
                        MathHelper.sin(this.king.yRot * ((float) Math.PI / 180F)),
                        -MathHelper.cos(this.king.yRot * ((float) Math.PI / 180F)));
                this.king.setDeltaMovement(this.king.getDeltaMovement().add(0, 3, 0));*/

                float moveVector = (float) Math.toRadians(this.king.vecOfTarget + 90 + this.king.getRandom().nextFloat() * 150 - 75);
                Vector3d getVector = this.king.getDeltaMovement().add(2.0F * Math.cos(moveVector), 0, 2.0F * Math.sin(moveVector));
                this.king.setDeltaMovement(getVector.x(), 1.0, getVector.z());

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.POOF, this.king.getX(), this.king.getY(), this.king.getZ(), 75, 5, 2, 5, 0.01);
                }
            }

            if (this.jumpTime >= 560) {
                this.jumpTime = this.king.getRandom().nextInt(20);
            }
        }

        this.king.setJumping(this.jumpTime > 500);
    }
}
