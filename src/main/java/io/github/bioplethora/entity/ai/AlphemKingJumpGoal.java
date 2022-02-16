package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AlphemKingJumpGoal extends Goal {

    private final AlphemKingEntity king;
    public int jumpTime;

    public AlphemKingJumpGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return (this.king.getTarget() != null);
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

        if (target != null && target.distanceToSqr(this.king) < 4096.0D /*&& this.king.canSee(target)*/) {

            ++this.jumpTime;

            World world = this.king.level;
            BlockPos pos = new BlockPos((int) this.king.getX(), (int) this.king.getY(), (int) this.king.getZ());

            if (this.jumpTime == 430) {

                this.king.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);

                this.king.knockback(1.5F,
                        MathHelper.sin(this.king.yRot * ((float) Math.PI / 180F)),
                        -MathHelper.cos(this.king.yRot * ((float) Math.PI / 180F)));

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.POOF, this.king.getX(), this.king.getY(), this.king.getZ(), 75, 5, 2, 5, 0.01);
                }
            }

            if (this.jumpTime == 460) {
                this.jumpTime = this.king.getRandom().nextInt(40);
            }
        }

        this.king.setJumping(this.jumpTime > 400);
    }
}
