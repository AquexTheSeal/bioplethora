package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class AlphemKingRangedAttackGoal extends Goal {

    private final AlphemKingEntity king;
    public int chargeTime;

    public AlphemKingRangedAttackGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return this.king.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.king.setCharging(false);
    }

    public void tick() {
        int chargeCap = 300;
        LivingEntity target = this.king.getTarget();
        if (target.distanceToSqr(this.king) < 4096.0D && king.canSee(target)) {
            World world = this.king.level;

            this.king.getLookControl().setLookAt(king.getTarget(), 10.0F, 10.0F);

            ++this.chargeTime;

            if (this.chargeTime == chargeCap && !this.king.isSilent()) {
                this.king.level.playSound(null, this.king.getX(), this.king.getY(), this.king.getZ(), SoundEvents.BEACON_ACTIVATE, this.king.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
            }

            if (this.chargeTime == chargeCap + 20) this.shootBlaze(world, target);

            if (this.chargeTime == chargeCap + 30) this.shootBlaze(world, target);

            if (this.chargeTime == chargeCap + 40) this.shootBlaze(world, target);

            if (this.chargeTime == chargeCap + 50) this.shootBlaze(world, target);

            if (this.chargeTime == chargeCap + 60) this.shootBlaze(world, target);

            if (this.chargeTime == chargeCap + 70) {
                this.shootBlaze(world, target);
                this.chargeTime = 0;
            }

            this.king.setCharging(this.chargeTime > chargeCap - 20);
        } else {
            this.king.setCharging(false);
        }
    }

    public void shootBlaze(World world, LivingEntity target) {

        Vector3d vector3d = this.king.getViewVector(1.0F);
        double d1 = target.getX() - this.king.getX();
        double d2 = target.getY(0.5D) - this.king.getY(0.5D);
        double d3 = target.getZ() - this.king.getZ();
        if (!this.king.isSilent()) {
            this.king.level.playSound(null, this.king.getX(), this.king.getY(), this.king.getZ(), SoundEvents.SHULKER_SHOOT, this.king.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
        }

        BPEffectEntity.createInstance(king, BPEffectTypes.ALPHEM_KING_BREATH);

        CryoblazeEntity cryo = new CryoblazeEntity(world, this.king, d1, d2, d3);
        cryo.setOwner(this.king);
        cryo.setPos(cryo.getX(), this.king.getY(0.5D) + 0.5D, cryo.getZ());
        world.addFreshEntity(cryo);

        CryoblazeEntity cryoL = new CryoblazeEntity(world, this.king, d1, d2, d3);
        cryoL.setOwner(this.king);
        cryoL.setPos(cryoL.getX() + vector3d.z * 4.0D, this.king.getY(0.5D) + 0.5D, cryoL.getZ() + vector3d.z * -4.0D);
        world.addFreshEntity(cryoL);

        CryoblazeEntity cryoR = new CryoblazeEntity(world, this.king, d1, d2, d3);
        cryoR.setOwner(this.king);
        cryoR.setPos(cryoR.getX() + vector3d.z * -4.0D, this.king.getY(0.5D) + 0.5D, cryoR.getZ() + vector3d.z * 4.0D);
        world.addFreshEntity(cryoR);
    }
}
