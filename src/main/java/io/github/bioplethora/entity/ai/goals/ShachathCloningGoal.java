package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ShachathCloningGoal extends Goal {

    private final ShachathEntity shachath;

    public ShachathCloningGoal(ShachathEntity shachathEntity) {
        this.shachath = shachathEntity;
    }

    public boolean canUse() {
        return (this.shachath.getTarget() != null) && (this.shachath.getOwner() == null);
    }

    public void start() {
        this.shachath.cloneChargeTime = 0;
    }

    public void stop() {
    }

    public void tick() {
        LivingEntity target = this.shachath.getTarget();
        
        if (target.distanceToSqr(this.shachath) < 4096.0D && this.shachath.canSee(target)) {

            this.shachath.getLookControl().setLookAt(target, 30.0F, 30.0F);
            BlockPos blockpos = this.shachath.blockPosition();

            ++this.shachath.cloneChargeTime;
            if (this.shachath.cloneChargeTime == 300) {

                if (this.shachath.level instanceof ServerWorld) {
                    ((ServerWorld) this.shachath.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.shachath.getX(), this.shachath.getY(), this.shachath.getZ(), 75, 0.3, 0.2, 0.2, 0.1);
                }

                ShachathEntity clone1 = new ShachathEntity(BPEntities.SHACHATH.get(), this.shachath.level);
                clone1.setOwner(this.shachath);
                clone1.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
                clone1.getAttribute(Attributes.ARMOR).setBaseValue(5 * BPConfig.COMMON.mobArmorMultiplier.get());
                clone1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30 * BPConfig.COMMON.mobHealthMultiplier.get());
                clone1.setHealth(30 * BPConfig.COMMON.mobHealthMultiplier.get());
                clone1.moveTo(blockpos, 0.1F, 0.1F);

                clone1.setHasLimitedLife(true);
                clone1.setExplodeOnExpiry(false);
                clone1.setLifeLimitBeforeDeath(150 + this.shachath.getRandom().nextInt(75));
                clone1.setClone(true);

                clone1.teleportRandomly();
                this.shachath.level.addFreshEntity(clone1);

                ShachathEntity clone2 = new ShachathEntity(BPEntities.SHACHATH.get(), this.shachath.level);
                clone2.setOwner(this.shachath);
                clone2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
                clone2.getAttribute(Attributes.ARMOR).setBaseValue(5 * BPConfig.COMMON.mobArmorMultiplier.get());
                clone2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30 * BPConfig.COMMON.mobHealthMultiplier.get());
                clone2.setHealth(30 * BPConfig.COMMON.mobHealthMultiplier.get());
                clone2.moveTo(blockpos, 0.1F, 0.1F);

                clone2.setHasLimitedLife(true);
                clone2.setExplodeOnExpiry(false);
                clone2.setLifeLimitBeforeDeath(150 + this.shachath.getRandom().nextInt(75));
                clone2.setClone(true);

                clone2.teleportRandomly();
                this.shachath.level.addFreshEntity(clone2);

                this.shachath.cloneChargeTime = 0;
            }
        }
    }
}
