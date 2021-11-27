package io.github.bioplethora.entity.ai.navigator;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WaterAndLandPathNavigator extends SwimmerPathNavigator {

    public WaterAndLandPathNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected PathFinder createPathFinder(int p_179679_1_) {
        this.nodeEvaluator = new WalkAndSwimNodeProcessor();
        return new PathFinder(this.nodeEvaluator, p_179679_1_);
    }

    protected boolean canUpdatePath() {
        return true;
    }

    protected Vector3d getTempMobPos() {
        return new Vector3d(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    }

    protected boolean canMoveDirectly(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ) {
        Vector3d vector3d = new Vector3d(posVec32.x, posVec32.y + (double)this.mob.getBbHeight() * 0.5D, posVec32.z);
        return this.level.clip(new RayTraceContext(posVec31, vector3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.mob)).getType() == RayTraceResult.Type.MISS;
    }

    public boolean isStableDestination(BlockPos pos) {
        return  !this.level.getBlockState(pos.below()).isAir();
    }

    public void setCanFloat(boolean canSwim) {
    }
}
