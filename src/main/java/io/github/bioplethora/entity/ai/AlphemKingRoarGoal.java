package io.github.bioplethora.entity.ai;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AlphemKingRoarGoal extends Goal {

    private final AlphemKingEntity king;
    public int roarTime;

    public AlphemKingRoarGoal(AlphemKingEntity kingEntity) {
        this.king = kingEntity;
    }

    public boolean canUse() {
        return (this.king.getTarget() != null);
    }

    public void start() {
        this.roarTime = 0;
    }

    public void stop() {
        this.king.setRoaring(false);
    }

    public boolean canContinueToUse() {
        return this.king.getTarget() != null;
    }

    public void tick() {
        LivingEntity target = this.king.getTarget();

        if (target != null && target.distanceToSqr(this.king) < 4096.0D /*&& this.king.canSee(target)*/) {

            ++this.roarTime;

            World world = this.king.level;
            BlockPos pos = new BlockPos((int) this.king.getX(), (int) this.king.getY(), (int) this.king.getZ());

            if (this.roarTime == 320) {
                this.king.playSound(this.king.getRoarSound(), 1.5F, 1.0F);
            }

            if (this.roarTime >= 320) {
                for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, this.king.getBoundingBox().inflate(15, 1.5, 15))) {

                    if (areaEnt != this.king) {
                        areaEnt.knockback((float) areaEnt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 0.5F,
                                MathHelper.sin(this.king.yRot * ((float) Math.PI / 180F)),
                                -MathHelper.cos(this.king.yRot * ((float) Math.PI / 180F)));
                    }
                }

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, this.king.getX(), this.king.getY(), this.king.getZ(), 30, 5, 2, 5, 0.01);
                }
            }

            if (this.roarTime == 420) {

                BlockPos aPos1 = new BlockPos((int) this.king.getX() + 4, (int) this.king.getY(), (int) this.king.getZ() + 4);
                BlockPos aPos2 = new BlockPos((int) this.king.getX() + 4, (int) this.king.getY(), (int) this.king.getZ() - 4);
                BlockPos aPos3 = new BlockPos((int) this.king.getX() - 4, (int) this.king.getY(), (int) this.king.getZ() + 4);
                BlockPos aPos4 = new BlockPos((int) this.king.getX() - 4, (int) this.king.getY(), (int) this.king.getZ() - 4);

                this.explodeOnBlockPos(aPos1, world);
                this.explodeOnBlockPos(aPos2, world);
                this.explodeOnBlockPos(aPos3, world);
                this.explodeOnBlockPos(aPos4, world);

                AlphemEntity alphem1 = BPEntities.ALPHEM.get().create(world);
                this.summonAlphem(alphem1, aPos1, world);
                AlphemEntity alphem2 = BPEntities.ALPHEM.get().create(world);
                this.summonAlphem(alphem2, aPos2, world);
                AlphemEntity alphem3 = BPEntities.ALPHEM.get().create(world);
                this.summonAlphem(alphem3, aPos3, world);
                AlphemEntity alphem4 = BPEntities.ALPHEM.get().create(world);
                this.summonAlphem(alphem4, aPos4, world);

                if (BPConfig.getHellMode) {

                    BlockPos aPos5 = new BlockPos((int) this.king.getX(), (int) this.king.getY(), (int) this.king.getZ() + 4);
                    BlockPos aPos6 = new BlockPos((int) this.king.getX() + 4, (int) this.king.getY(), (int) this.king.getZ());
                    BlockPos aPos7 = new BlockPos((int) this.king.getX() - 4, (int) this.king.getY(), (int) this.king.getZ());
                    BlockPos aPos8 = new BlockPos((int) this.king.getX(), (int) this.king.getY(), (int) this.king.getZ() - 4);

                    this.explodeOnBlockPos(aPos5, world);
                    this.explodeOnBlockPos(aPos6, world);
                    this.explodeOnBlockPos(aPos7, world);
                    this.explodeOnBlockPos(aPos8, world);

                    AlphemEntity alphem5 = BPEntities.ALPHEM.get().create(world);
                    this.summonAlphem(alphem5, aPos5, world);
                    AlphemEntity alphem6 = BPEntities.ALPHEM.get().create(world);
                    this.summonAlphem(alphem6, aPos6, world);
                    AlphemEntity alphem7 = BPEntities.ALPHEM.get().create(world);
                    this.summonAlphem(alphem7, aPos7, world);
                    AlphemEntity alphem8 = BPEntities.ALPHEM.get().create(world);
                    this.summonAlphem(alphem8, aPos8, world);
                }

                this.roarTime = this.king.getRandom().nextInt(40);
            }
        }

        this.king.setRoaring(this.roarTime > 300);
    }

    public void explodeOnBlockPos(BlockPos pos, World world) {
        world.explode(null, DamageSource.indirectMagic(this.king, this.king), null, pos.getX(), pos.getY(), pos.getZ(), 1.5F, false, Explosion.Mode.NONE);

        if (world instanceof ServerWorld) {

            double d0 = this.king.getRandom().nextGaussian() * 0.02D;
            double d1 = this.king.getRandom().nextGaussian() * 0.02D;
            double d2 = this.king.getRandom().nextGaussian() * 0.02D;

            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), pos.getY(), pos.getZ(), d0, d1, d2);
            world.addParticle(ParticleTypes.POOF, pos.getX(), pos.getY(), pos.getZ(), d0, d1, d2);
        }
    }

    public void summonAlphem(AlphemEntity alphem, BlockPos pos, World world) {
        if (world instanceof ServerWorld) {
            ServerWorld serverworld = (ServerWorld) world;
            alphem.moveTo(pos, 0.0F, 0.0F);
            alphem.setOwner(this.king);
            alphem.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(pos), SpawnReason.MOB_SUMMONED, null, null);
            alphem.setHasLimitedLife(false);
            alphem.setExplodeOnExpiry(false);
            serverworld.addFreshEntity(alphem);
        }
    }
}
