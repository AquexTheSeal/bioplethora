package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.AltyrusEntity;
import io.github.bioplethora.entity.projectile.UltimateBellophiteClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class AltyrusRangedAttackGoal extends Goal {

    private final AltyrusEntity altyrus;
    public int chargeTime;

    public AltyrusRangedAttackGoal(AltyrusEntity altyrusEntity) {
        this.altyrus = altyrusEntity;
    }

    public boolean canUse() {
        return this.altyrus.getTarget() != null && !this.altyrus.isSummoning() && !this.altyrus.getAttacking();
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.altyrus.setCharging(false);
    }

    public void tick() {
        LivingEntity livingentity = this.altyrus.getTarget();
        double d0 = 64.0D;
        if (livingentity.distanceToSqr(this.altyrus) < 1024.0D && this.altyrus.canSee(livingentity)) {
            World world = this.altyrus.level;
            double d1 = 4.0D;

            Vector3d vector3d = this.altyrus.getViewVector(1.0F);
            double d2 = livingentity.getX() - (this.altyrus.getX() + vector3d.x * 4.0D);
            double d3 = livingentity.getY(0.5D) - (0.5D + this.altyrus.getY(0.5D));
            double d4 = livingentity.getZ() - (this.altyrus.getZ() + vector3d.z * 4.0D);
            UltimateBellophiteClusterEntity ultimateBellophiteClusterEntity = new UltimateBellophiteClusterEntity(world, this.altyrus, d2, d3, d4);
            ultimateBellophiteClusterEntity.setPos(this.altyrus.getX() + vector3d.x * 4.0D, this.altyrus.getY(0.5D) + 0.5D, ultimateBellophiteClusterEntity.getZ() + vector3d.z * 4.0D);

            ++this.chargeTime;

            if (this.chargeTime == 10 && !this.altyrus.isSilent()) {
                ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                        SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
            }

            if (this.chargeTime == 30) {
                if (!this.altyrus.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                            SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
                }

                world.addFreshEntity(ultimateBellophiteClusterEntity);
            }

            if (this.chargeTime == 35) {
                if (!this.altyrus.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                            SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
                }

                world.addFreshEntity(ultimateBellophiteClusterEntity);
            }

            if (this.chargeTime == 40) {
                if (!this.altyrus.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                            SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
                }

                world.addFreshEntity(ultimateBellophiteClusterEntity);
            }

            if (this.chargeTime == 45) {
                if (!this.altyrus.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                            SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
                }

                world.addFreshEntity(ultimateBellophiteClusterEntity);
            }

            if (this.chargeTime == 50) {
                if (!this.altyrus.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ()),
                            SoundEvents.SHULKER_SHOOT, SoundCategory.AMBIENT, (float) 1, (float) 1);
                }

                world.addFreshEntity(ultimateBellophiteClusterEntity);

                this.chargeTime = -100;
            }

        } else if (this.chargeTime > 0) {
            --this.chargeTime;
        }

        this.altyrus.setCharging(this.chargeTime > 10);
    }
}
