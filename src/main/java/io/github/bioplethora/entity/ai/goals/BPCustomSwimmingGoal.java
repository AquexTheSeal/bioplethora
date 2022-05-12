package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class BPCustomSwimmingGoal extends Goal {

    public WaterAndLandAnimalEntity entity;
    
    public BPCustomSwimmingGoal(WaterAndLandAnimalEntity entity) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.entity = entity;
    }

    public boolean canUse() {
        if (entity.isInWater()) {
            return !entity.getMoveControl().hasWanted() && entity.getRandom().nextInt(7) == 0;
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        return false;
    }

    public void tick() {
        BlockPos blockpos = entity.getBoundOrigin();
        if (blockpos == null) {
            blockpos = entity.blockPosition();
        }

        for(int i = 0; i < 3; ++i) {
            BlockPos blockpos1 = blockpos.offset(entity.getRandom().nextInt(15) - 7, entity.getRandom().nextInt(11) - 3, entity.getRandom().nextInt(15) - 7);
            if (entity.level.isEmptyBlock(blockpos1)) {
                entity.getMoveControl().setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                if (entity.getTarget() == null) {
                    entity.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                }
                break;
            }
        }
    }
}
