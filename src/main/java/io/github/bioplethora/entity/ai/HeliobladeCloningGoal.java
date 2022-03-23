package io.github.bioplethora.entity.ai;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.HeliobladeEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HeliobladeCloningGoal extends Goal {

    private final HeliobladeEntity helioblade;

    public HeliobladeCloningGoal(HeliobladeEntity heliobladeEntity) {
        this.helioblade = heliobladeEntity;
    }

    public boolean canUse() {
        return (this.helioblade.getTarget() != null) && (this.helioblade.getOwner() == null);
    }

    public void start() {
        this.helioblade.cloneChargeTime = 0;
    }

    public void stop() {
    }

    public void tick() {
        LivingEntity target = this.helioblade.getTarget();
        
        if (target.distanceToSqr(this.helioblade) < 4096.0D && this.helioblade.canSee(target)) {

            this.helioblade.getLookControl().setLookAt(target, 30.0F, 30.0F);
            BlockPos blockpos = this.helioblade.blockPosition();

            ++this.helioblade.cloneChargeTime;
            if (this.helioblade.cloneChargeTime == 300) {

                if (this.helioblade.level instanceof ServerWorld) {
                    ((ServerWorld) this.helioblade.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.helioblade.getX(), this.helioblade.getY(), this.helioblade.getZ(), 75, 0.3, 0.2, 0.2, 0.1);
                }

                HeliobladeEntity clone1 = new HeliobladeEntity(BPEntities.HELIOBLADE.get(), this.helioblade.level);
                clone1.setOwner(this.helioblade);
                clone1.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
                clone1.getAttribute(Attributes.ARMOR).setBaseValue(5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
                clone1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
                clone1.setHealth(30 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
                clone1.moveTo(blockpos, 0.1F, 0.1F);

                clone1.setHasLimitedLife(true);
                clone1.setExplodeOnExpiry(false);
                clone1.setLifeLimitBeforeDeath(150 + this.helioblade.getRandom().nextInt(75));
                clone1.setClone(true);

                clone1.teleportRandomly();
                this.helioblade.level.addFreshEntity(clone1);

                HeliobladeEntity clone2 = new HeliobladeEntity(BPEntities.HELIOBLADE.get(), this.helioblade.level);
                clone2.setOwner(this.helioblade);
                clone2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
                clone2.getAttribute(Attributes.ARMOR).setBaseValue(5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
                clone2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
                clone2.setHealth(30 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
                clone2.moveTo(blockpos, 0.1F, 0.1F);

                clone2.setHasLimitedLife(true);
                clone2.setExplodeOnExpiry(false);
                clone2.setLifeLimitBeforeDeath(150 + this.helioblade.getRandom().nextInt(75));
                clone2.setClone(true);

                clone2.teleportRandomly();
                this.helioblade.level.addFreshEntity(clone2);

                this.helioblade.cloneChargeTime = 0;
            }
        }
    }
}
