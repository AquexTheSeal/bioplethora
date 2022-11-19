package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class AlphemKingPursuitGoal extends Goal {

    private final AlphemKingEntity king;
    public int pursTime;
    public boolean hasRaised;
    public BlockPos targetPos;

    public AlphemKingPursuitGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return this.king.getTarget() != null && !this.king.getRoaring();
    }

    public void start() {
        this.pursTime = 0;
    }

    public void stop() {
        this.king.setPursuit(false);
        this.king.setNoGravity(false);
    }

    public boolean canContinueToUse() {
        return this.king.getTarget() != null && !this.king.getRoaring();
    }

    public void tick() {
        LivingEntity target = this.king.getTarget();

        this.king.setPursuit(hasRaised);

        if ((target.distanceToSqr(this.king) >= 48.0D || target.getY() > king.getY() + 8) && !hasRaised) {
            ++pursTime;
            if (pursTime == 60) {
                teleportWithEffect(target.getX(), target.getY() + 5, target.getZ());
                targetPos = new BlockPos(target.getX(), king.getGroundPos(king.level, (int) target.getX(), (int) target.getZ()).getY(), target.getZ());
                this.king.setNoGravity(true);

                hasRaised = true;
            }
        }
        if (hasRaised && targetPos != null) {
            ++pursTime;
            if (pursTime == 80) {

                int areaint = 5;
                int wsHeight = king.getGroundPos(king.level, (int) king.getX(), (int) king.getZ()).getY();
                AxisAlignedBB aabb = new AxisAlignedBB(king.getX() - areaint, (king.getY() - areaint) - (king.getY() - wsHeight), king.getZ() - areaint, king.getX() + areaint, king.getY(), king.getZ() + areaint);
                for (LivingEntity areaEnt : king.level.getEntitiesOfClass(LivingEntity.class, aabb)) {
                    if (areaEnt != this.king) {
                        areaEnt.moveTo(areaEnt.getX(), targetPos.getY(), areaEnt.getZ());
                        areaEnt.hurt(DamageSource.explosion(this.king), 5.0F);
                    }
                }

                teleportWithEffect(targetPos.getX(), targetPos.getY(), targetPos.getZ());

                for (LivingEntity areaEnt : king.level.getEntitiesOfClass(LivingEntity.class, king.getBoundingBox().inflate(7, 1, 7).move(targetPos))) {
                    if (areaEnt != this.king) {
                        areaEnt.hurt(DamageSource.explosion(this.king), 7.0F);
                        areaEnt.knockback(2F, this.king.getX() - areaEnt.getX(), this.king.getZ() - areaEnt.getZ());
                        areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
                    }
                }

                this.king.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);
                BlockUtils.knockUpRandomNearbyBlocks(king.level, 0.3D, targetPos.below(), 3, 1, 3, false, true);

                if (king.level instanceof ServerWorld) {
                    ((ServerWorld) king.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                            25, 0.45, 0.45, 0.45, 0.001);
                    ((ServerWorld) king.level).sendParticles(ParticleTypes.POOF, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                            25, 0.45, 0.45, 0.45, 0.001);
                }

                EntityUtils.shakeNearbyPlayersScreen(this.king, 32, 10);

                this.king.setNoGravity(false);

                pursTime = 0;
                hasRaised = false;
            }
        }
    }

    public void teleportWithEffect(double xLoc, double yLoc, double zLoc) {
        this.king.level.playSound(null, xLoc, yLoc, zLoc, SoundEvents.EVOKER_FANGS_ATTACK, SoundCategory.HOSTILE, (float) 1, (float) 1);
        if (this.king.level instanceof ServerWorld) {
            ((ServerWorld) this.king.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, xLoc, yLoc, zLoc, 55, 2, 2, 2, 0.0001);
        }
        this.king.moveTo(xLoc, yLoc, zLoc);
    }
}
