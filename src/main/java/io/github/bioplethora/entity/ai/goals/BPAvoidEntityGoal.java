package io.github.bioplethora.entity.ai.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class BPAvoidEntityGoal<T extends LivingEntity> extends Goal {

    protected final MobEntity mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected T toAvoid;
    protected final float maxDist;
    protected Path path;
    protected final PathNavigator pathNav;
    protected final Class<T> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final EntityPredicate avoidEntityTargeting;

    public BPAvoidEntityGoal(MobEntity p_i46404_1_, Class<T> p_i46404_2_, float p_i46404_3_, double p_i46404_4_, double p_i46404_6_) {
        this(p_i46404_1_, p_i46404_2_, (p_200828_0_) -> true, p_i46404_3_, p_i46404_4_, p_i46404_6_, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public BPAvoidEntityGoal(MobEntity p_i48859_1_, Class<T> p_i48859_2_, Predicate<LivingEntity> p_i48859_3_, float p_i48859_4_, double p_i48859_5_, double p_i48859_7_, Predicate<LivingEntity> p_i48859_9_) {
        this.mob = p_i48859_1_;
        this.avoidClass = p_i48859_2_;
        this.avoidPredicate = p_i48859_3_;
        this.maxDist = p_i48859_4_;
        this.walkSpeedModifier = p_i48859_5_;
        this.sprintSpeedModifier = p_i48859_7_;
        this.predicateOnAvoidEntity = p_i48859_9_;
        this.pathNav = p_i48859_1_.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.avoidEntityTargeting = (new EntityPredicate()).range((double)p_i48859_4_).selector(p_i48859_9_.and(p_i48859_3_));
    }

    public BPAvoidEntityGoal(MobEntity p_i48860_1_, Class<T> p_i48860_2_, float p_i48860_3_, double p_i48860_4_, double p_i48860_6_, Predicate<LivingEntity> p_i48860_8_) {
        this(p_i48860_1_, p_i48860_2_, (p_203782_0_) -> true, p_i48860_3_, p_i48860_4_, p_i48860_6_, p_i48860_8_);
    }

    public boolean canUse() {
        this.toAvoid = this.mob.level.getNearestLoadedEntity(this.avoidClass, this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().inflate((double)this.maxDist, 3.0D, (double)this.maxDist));
        if (this.toAvoid == null) {
            return false;
        } else {
            Vector3d vector3d = getPosAvoid(this.mob, 16, 7, this.toAvoid.position());
            if (vector3d == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
                return this.path != null;
            }
        }
    }

    @Nullable
    public static Vector3d getPosAvoid(MobEntity p_75461_0_, int p_75461_1_, int p_75461_2_, Vector3d p_75461_3_) {
        Vector3d vector3d = p_75461_0_.position().subtract(p_75461_3_);
        return generateRandomPos(p_75461_0_, p_75461_1_, p_75461_2_, 0, vector3d, true, (double)((float)Math.PI / 2F), (a) -> 0.0F, false, 0, 0, true);
    }

    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.toAvoid = null;
    }

    public void tick() {
        if (this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }

    @Nullable
    public static Vector3d generateRandomPos(MobEntity p_226339_0_, int p_226339_1_, int p_226339_2_, int p_226339_3_, @Nullable Vector3d p_226339_4_, boolean p_226339_5_, double p_226339_6_, ToDoubleFunction<BlockPos> p_226339_8_, boolean p_226339_9_, int p_226339_10_, int p_226339_11_, boolean p_226339_12_) {
        PathNavigator pathnavigator = p_226339_0_.getNavigation();
        Random random = p_226339_0_.getRandom();
        boolean flag;
        if (p_226339_0_.hasRestriction()) {
            flag = p_226339_0_.getRestrictCenter().closerThan(p_226339_0_.position(), (double)(p_226339_0_.getRestrictRadius() + (float)p_226339_1_) + 1.0D);
        } else {
            flag = false;
        }

        boolean flag1 = false;
        double d0 = Double.NEGATIVE_INFINITY;
        BlockPos blockpos = p_226339_0_.blockPosition();

        for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = getRandomDelta(random, p_226339_1_, p_226339_2_, p_226339_3_, p_226339_4_, p_226339_6_);
            if (blockpos1 != null) {
                int j = blockpos1.getX();
                int k = blockpos1.getY();
                int l = blockpos1.getZ();
                if (p_226339_0_.hasRestriction() && p_226339_1_ > 1) {
                    BlockPos blockpos2 = p_226339_0_.getRestrictCenter();
                    if (p_226339_0_.getX() > (double)blockpos2.getX()) {
                        j -= random.nextInt(p_226339_1_ / 2);
                    } else {
                        j += random.nextInt(p_226339_1_ / 2);
                    }

                    if (p_226339_0_.getZ() > (double)blockpos2.getZ()) {
                        l -= random.nextInt(p_226339_1_ / 2);
                    } else {
                        l += random.nextInt(p_226339_1_ / 2);
                    }
                }

                BlockPos blockpos3 = new BlockPos((double)j + p_226339_0_.getX(), (double)k + p_226339_0_.getY(), (double)l + p_226339_0_.getZ());
                if (blockpos3.getY() >= 0 && blockpos3.getY() <= p_226339_0_.level.getMaxBuildHeight() && (!flag || p_226339_0_.isWithinRestriction(blockpos3)) && (!p_226339_12_ || pathnavigator.isStableDestination(blockpos3))) {
                    if (p_226339_9_) {
                        blockpos3 = moveUpToAboveSolid(blockpos3, random.nextInt(p_226339_10_ + 1) + p_226339_11_, p_226339_0_.level.getMaxBuildHeight(), (p_226341_1_) -> {
                            return p_226339_0_.level.getBlockState(p_226341_1_).getMaterial().isSolid();
                        });
                    }

                    if (p_226339_5_ || !p_226339_0_.level.getFluidState(blockpos3).is(FluidTags.WATER)) {
                        PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(p_226339_0_.level, blockpos3.mutable());
                        if (p_226339_0_.getPathfindingMalus(pathnodetype) == 0.0F) {
                            double d1 = p_226339_8_.applyAsDouble(blockpos3);
                            if (d1 > d0) {
                                d0 = d1;
                                blockpos = blockpos3;
                                flag1 = true;
                            }
                        }
                    }
                }
            }
        }

        return flag1 ? Vector3d.atBottomCenterOf(blockpos) : null;
    }

    static BlockPos moveUpToAboveSolid(BlockPos p_226342_0_, int p_226342_1_, int p_226342_2_, Predicate<BlockPos> p_226342_3_) {
        if (p_226342_1_ < 0) {
            throw new IllegalArgumentException("aboveSolidAmount was " + p_226342_1_ + ", expected >= 0");
        } else if (!p_226342_3_.test(p_226342_0_)) {
            return p_226342_0_;
        } else {
            BlockPos blockpos;
            for(blockpos = p_226342_0_.above(); blockpos.getY() < p_226342_2_ && p_226342_3_.test(blockpos); blockpos = blockpos.above()) {
            }

            BlockPos blockpos1;
            BlockPos blockpos2;
            for(blockpos1 = blockpos; blockpos1.getY() < p_226342_2_ && blockpos1.getY() - blockpos.getY() < p_226342_1_; blockpos1 = blockpos2) {
                blockpos2 = blockpos1.above();
                if (p_226342_3_.test(blockpos2)) {
                    break;
                }
            }

            return blockpos1;
        }
    }

    @Nullable
    private static BlockPos getRandomDelta(Random p_226343_0_, int p_226343_1_, int p_226343_2_, int p_226343_3_, @Nullable Vector3d p_226343_4_, double p_226343_5_) {
        if (p_226343_4_ != null && !(p_226343_5_ >= Math.PI)) {
            double d3 = MathHelper.atan2(p_226343_4_.z, p_226343_4_.x) - (double)((float)Math.PI / 2F);
            double d4 = d3 + (double)(2.0F * p_226343_0_.nextFloat() - 1.0F) * p_226343_5_;
            double d0 = Math.sqrt(p_226343_0_.nextDouble()) * (double)MathHelper.SQRT_OF_TWO * (double)p_226343_1_;
            double d1 = -d0 * Math.sin(d4);
            double d2 = d0 * Math.cos(d4);
            if (!(Math.abs(d1) > (double)p_226343_1_) && !(Math.abs(d2) > (double)p_226343_1_)) {
                int l = p_226343_0_.nextInt(2 * p_226343_2_ + 1) - p_226343_2_ + p_226343_3_;
                return new BlockPos(d1, (double)l, d2);
            } else {
                return null;
            }
        } else {
            int i = p_226343_0_.nextInt(2 * p_226343_1_ + 1) - p_226343_1_;
            int j = p_226343_0_.nextInt(2 * p_226343_2_ + 1) - p_226343_2_ + p_226343_3_;
            int k = p_226343_0_.nextInt(2 * p_226343_1_ + 1) - p_226343_1_;
            return new BlockPos(i, j, k);
        }
    }
}
