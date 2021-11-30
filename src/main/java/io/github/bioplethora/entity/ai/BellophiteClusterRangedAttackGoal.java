package io.github.bioplethora.entity.ai;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class BellophiteClusterRangedAttackGoal extends Goal {

    private final BellophgolemEntity bellophgolem;
    public int chargeTime;

    public BellophiteClusterRangedAttackGoal(BellophgolemEntity bellophgolemEntity) {
        this.bellophgolem = bellophgolemEntity;
    }

    public boolean canUse() {
        return this.bellophgolem.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.bellophgolem.setCharging(false);
    }

    public void tick() {
        LivingEntity livingentity = this.bellophgolem.getTarget();
        double d0 = 64.0D;
        if (livingentity.distanceToSqr(this.bellophgolem) < 4096.0D && this.bellophgolem.canSee(livingentity)) {
            World world = this.bellophgolem.level;
            ++this.chargeTime;
            if (this.chargeTime == 10 && !this.bellophgolem.isSilent()) {
                ((World) world).playSound(null, new BlockPos((int) this.bellophgolem.getX(), (int) this.bellophgolem.getY(), (int) this.bellophgolem.getZ()),
                        (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.activate"))),
                        SoundCategory.HOSTILE, (float) 1, (float) 1);
            }

            if (!BioplethoraConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    double d1 = 4.0D;
                    Vector3d vector3d = this.bellophgolem.getViewVector(1.0F);
                    double d2 = livingentity.getX() - (this.bellophgolem.getX() + vector3d.x * 4.0D);
                    double d3 = livingentity.getY(0.5D) - (0.5D + this.bellophgolem.getY(0.5D));
                    double d4 = livingentity.getZ() - (this.bellophgolem.getZ() + vector3d.z * 4.0D);
                    if (!this.bellophgolem.isSilent()) {
                        ((World) world).playSound(null, new BlockPos((int) this.bellophgolem.getX(), (int) this.bellophgolem.getY(), (int) this.bellophgolem.getZ()),
                                (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot"))),
                                SoundCategory.HOSTILE, (float) 1, (float) 1);
                    }

                    BellophiteClusterEntity bellophiteClusterEntity = new BellophiteClusterEntity(world, this.bellophgolem, d2, d3, d4);
                    bellophiteClusterEntity.setPos(this.bellophgolem.getX() + vector3d.x * 4.0D, this.bellophgolem.getY(0.5D) + 0.5D, bellophiteClusterEntity.getZ() + vector3d.z * 4.0D);
                    world.addFreshEntity(bellophiteClusterEntity);
                    this.chargeTime = -40;
                }

                /* IF IN HELLMODE*/
            } else if (BioplethoraConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    double d1 = 4.0D;
                    Vector3d vector3d = this.bellophgolem.getViewVector(1.0F);
                    double d2 = livingentity.getX() - (this.bellophgolem.getX() + vector3d.x * 4.0D);
                    double d3 = livingentity.getY(0.5D) - (0.5D + this.bellophgolem.getY(0.5D));
                    double d4 = livingentity.getZ() - (this.bellophgolem.getZ() + vector3d.z * 4.0D);
                    if (!this.bellophgolem.isSilent()) {
                        ((World) world).playSound(null, new BlockPos((int) this.bellophgolem.getX(), (int) this.bellophgolem.getY(), (int) this.bellophgolem.getZ()),
                                (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot"))),
                                SoundCategory.HOSTILE, (float) 1, (float) 1);
                    }

                    BellophiteClusterEntity bellophiteClusterEntity = new BellophiteClusterEntity(world, this.bellophgolem, d2, d3, d4);
                    bellophiteClusterEntity.setPos(this.bellophgolem.getX() + vector3d.x * 4.0D, this.bellophgolem.getY(0.5D) + 0.5D, bellophiteClusterEntity.getZ() + vector3d.z * 4.0D);
                    world.addFreshEntity(bellophiteClusterEntity);
                }

                if (this.chargeTime == 30) {
                    double d1 = 4.0D;
                    Vector3d vector3d = this.bellophgolem.getViewVector(1.0F);
                    double d2 = livingentity.getX() - (this.bellophgolem.getX() + vector3d.x * 4.0D);
                    double d3 = livingentity.getY(0.5D) - (0.5D + this.bellophgolem.getY(0.5D));
                    double d4 = livingentity.getZ() - (this.bellophgolem.getZ() + vector3d.z * 4.0D);
                    if (!this.bellophgolem.isSilent() && this.bellophgolem.getHealth() <= 100) {
                        ((World) world).playSound(null, new BlockPos((int) this.bellophgolem.getX(), (int) this.bellophgolem.getY(), (int) this.bellophgolem.getZ()),
                                (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot"))),
                                SoundCategory.HOSTILE, (float) 1, (float) 1);
                    }
                    if (this.bellophgolem.getHealth() <= 100) {
                        BellophiteClusterEntity bellophiteClusterEntity2 = new BellophiteClusterEntity(world, this.bellophgolem, d2, d3, d4);
                        bellophiteClusterEntity2.setPos(this.bellophgolem.getX() + vector3d.x * 4.0D, this.bellophgolem.getY(0.5D) + 0.5D, bellophiteClusterEntity2.getZ() + vector3d.z * 4.0D);
                        world.addFreshEntity(bellophiteClusterEntity2);
                    }
                    this.chargeTime = -40;
                }
            }
        } else if (this.chargeTime > 0) {
            --this.chargeTime;
        }

        this.bellophgolem.setCharging(this.chargeTime > 10);
    }
}
