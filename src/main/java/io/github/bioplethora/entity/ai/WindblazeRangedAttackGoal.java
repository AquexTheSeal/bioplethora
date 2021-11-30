package io.github.bioplethora.entity.ai;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import io.github.bioplethora.entity.projectile.WindblazeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class WindblazeRangedAttackGoal extends Goal {

    private final AlphemEntity alphem;
    public int chargeTime;

    public WindblazeRangedAttackGoal(AlphemEntity alphemEntity) {
        this.alphem = alphemEntity;
    }

    public boolean canUse() {
        return this.alphem.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.alphem.setCharging(false);
    }

    public void tick() {
        LivingEntity livingentity = this.alphem.getTarget();
        double d0 = 64.0D;
        if (livingentity.distanceToSqr(this.alphem) < 4096.0D && this.alphem.canSee(livingentity)) {
            World world = this.alphem.level;
            ++this.chargeTime;
            if (this.chargeTime == 10 && !this.alphem.isSilent()) {
                ((World) world).playSound(null, new BlockPos((int) this.alphem.getX(), (int) this.alphem.getY(), (int) this.alphem.getZ()),
                        (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.activate"))),
                        SoundCategory.HOSTILE, (float) 1, (float) 1);
            }
            if (this.chargeTime == 20) {
                double d1 = 4.0D;
                Vector3d vector3d = this.alphem.getViewVector(1.0F);
                double d2 = livingentity.getX() - (this.alphem.getX() + vector3d.x * 4.0D);
                double d3 = livingentity.getY(0.5D) - (0.5D + this.alphem.getY(0.5D));
                double d4 = livingentity.getZ() - (this.alphem.getZ() + vector3d.z * 4.0D);
                if (!this.alphem.isSilent()) {
                    ((World) world).playSound(null, new BlockPos((int) this.alphem.getX(), (int) this.alphem.getY(), (int) this.alphem.getZ()),
                            (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot"))),
                            SoundCategory.HOSTILE, (float) 1, (float) 1);
                }

                WindblazeEntity windblazeEntity = new WindblazeEntity(world, this.alphem, d2, d3, d4);
                windblazeEntity.setPos(this.alphem.getX() + vector3d.x * 4.0D, this.alphem.getY(0.5D) + 0.5D, windblazeEntity.getZ() + vector3d.z * 4.0D);
                world.addFreshEntity(windblazeEntity);
                this.chargeTime = -40;
            }
        } else if (this.chargeTime > 0) {
            --this.chargeTime;
        }
        this.alphem.setCharging(this.chargeTime > 10);
    }
}
