package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShachathEntityStrikeGoal extends Goal {

    private final ShachathEntity shachath;
    private final List<LivingEntity> targetsList = new ArrayList<>();
    public int chargeTime;

    public ShachathEntityStrikeGoal(ShachathEntity shachathEntity) {
        this.shachath = shachathEntity;
    }

    public boolean canUse() {
        return this.shachath.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.chargeTime = 0;
        targetsList.clear();
        this.shachath.setStriking(false);
    }

    public void tick() {
        int chargeCap = 260;
        LivingEntity target = this.shachath.getTarget();
        if (target.distanceToSqr(this.shachath) < 4096.0D && shachath.canSee(target)) {

            this.shachath.getLookControl().setLookAt(shachath.getTarget(), 10.0F, 10.0F);
            ++this.chargeTime;

            if (chargeTime > chargeCap) {
                shachath.particleCharge(0);
            }

            if (this.chargeTime == chargeCap) {
                // First Target
                targetsList.add(target);

                // Second Target
                List<LivingEntity> targetAABB = shachath.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(16, 16, 16));
                List<LivingEntity> targetAABBFiltered = targetAABB.stream().filter((a) -> doFilter(a) && a != target).collect(Collectors.toList());
                if (targetAABBFiltered.isEmpty()) {
                    targetsList.add(target);
                } else {
                    targetsList.add(targetAABBFiltered.get(shachath.getRandom().nextInt(targetAABBFiltered.size())));
                }

                // Third Target
                List<LivingEntity> target2AABB = shachath.level.getEntitiesOfClass(LivingEntity.class, targetsList.get(1).getBoundingBox().inflate(16, 16, 16));
                List<LivingEntity> target2AABBFiltered = target2AABB.stream().filter((a) -> doFilter(a) && a != target && a != targetsList.get(1)).collect(Collectors.toList());
                if (target2AABBFiltered.isEmpty()) {
                    targetsList.add(targetsList.get(1));
                } else {
                    targetsList.add(target2AABBFiltered.get(shachath.getRandom().nextInt(target2AABBFiltered.size())));
                }
            }

            if (this.chargeTime == chargeCap + 25) {
                attackTarget(shachath.level, targetsList.get(0), 0);
            }

            if (this.chargeTime == chargeCap + 35) {
                attackTarget(shachath.level, targetsList.get(1), 1);
            }

            if (this.chargeTime == chargeCap + 40) {
                attackTarget(shachath.level, targetsList.get(2), 2);
            }

            if (this.chargeTime == chargeCap + 60) {
                targetsList.clear();
                chargeTime = 0;
            }

            this.shachath.setStriking(this.chargeTime > chargeCap + 60);
        } else {
            this.shachath.setStriking(false);
        }
    }

    public boolean doFilter(LivingEntity entity) {
        return entity != shachath && EntityUtils.IsNotPet(shachath).test(entity) && EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(entity) && shachath.canSee(entity);
    }

    public void attackTarget(World world, LivingEntity target, int phase) {
        float damageReduction = target.getUseItem().getItem() instanceof ShieldItem ? 2.25F : 1F;
        double d0 = -MathHelper.sin(shachath.yRot * ((float)Math.PI / 180F)) * 6;
        double d1 = MathHelper.cos(shachath.yRot * ((float)Math.PI / 180F)) * 6;

        this.ballParticleOnTarget(world, target);
        if (target.getUseItem().getItem() instanceof ShieldItem) {
            target.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            if (target instanceof PlayerEntity) {
                if (!((PlayerEntity) target).abilities.instabuild) {
                    target.getUseItem().hurtAndBreak(3, shachath, (user) -> user.broadcastBreakEvent(target.getUsedItemHand()));
                }
            }
        }
        shachath.moveTo(target.getX(), target.getY(), target.getZ());
        shachath.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
        target.hurt(DamageSource.mobAttack(shachath), 7);
        shachath.particleCharge(phase + 1);
        if (phase == 0) {
            BPEffectEntity.createInstance(shachath, BPEffectTypes.SHACHATH_SLASH_FLAT);
            shachath.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
            shachath.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 1.25F);
            for (LivingEntity entities : world.getEntitiesOfClass(LivingEntity.class, shachath.getBoundingBox().inflate(2.4, 0, 2.4))) {
                if (entities != shachath) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(shachath), (shachath.isClone() ? 6F : 9F) / damageReduction);
                    world.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    world.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        } else if (phase == 1) {
            BPEffectEntity.createInstance(shachath, BPEffectTypes.SHACHATH_SLASH_FRONT);
            shachath.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
            shachath.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 1.25F);
            for (LivingEntity entities : world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(shachath.getX() - d0, shachath.getY() - 2.5, shachath.getZ() - d1, shachath.getX() + d0, shachath.getY() + 2.5, shachath.getZ() + d1))) {
                if (entities != shachath) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(shachath), (shachath.isClone() ? 6F : 9F) / damageReduction);
                    world.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    world.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        } else if (phase == 2) {
            BPEffectEntity.createInstance(shachath, BPEffectTypes.SHACHATH_SLASH_FLAT);
            BPEffectEntity.createInstance(shachath, BPEffectTypes.SHACHATH_SLASH_FRONT);
            shachath.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
            shachath.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 1.25F);
            for (LivingEntity entities : world.getEntitiesOfClass(LivingEntity.class, shachath.getBoundingBox().inflate(2.4, 0, 2.4))) {
                if (entities != shachath) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(shachath), (shachath.isClone() ? 9F : 14F) / damageReduction);
                    world.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    world.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
            for (LivingEntity entities : world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(shachath.getX() - d0, shachath.getY() - 2.5, shachath.getZ() - d1, shachath.getX() + d0, shachath.getY() + 2.5, shachath.getZ() + d1))) {
                if (entities != shachath) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(shachath), (shachath.isClone() ? 9F : 14F) / damageReduction);
                    world.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    world.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        }
    }

    public void ballParticleOnTarget(World world, LivingEntity target) {
        int pSize = 10;
        for(int i = -pSize; i <= pSize; ++i) {
            for(int j = -pSize; j <= pSize; ++j) {
                for(int k = -pSize; k <= pSize; ++k) {
                    double d3 = (double)j + (shachath.getRandom().nextDouble() - shachath.getRandom().nextDouble()) * 0.5D;
                    double d4 = (double)i + (shachath.getRandom().nextDouble() - shachath.getRandom().nextDouble()) * 0.5D;
                    double d5 = (double)k + (shachath.getRandom().nextDouble() - shachath.getRandom().nextDouble()) * 0.5D;
                    double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / 1.2 + shachath.getRandom().nextGaussian() * 0.05D;
                    world.addParticle(ParticleTypes.FLAME, target.getX(), target.getY(), target.getZ(), d3 / d6, d4 / d6, d5 / d6);
                    if (i != -pSize && i != pSize && j != -pSize && j != pSize) {
                        k += pSize * 2 - 1;
                    }
                }
            }
        }
    }
}
