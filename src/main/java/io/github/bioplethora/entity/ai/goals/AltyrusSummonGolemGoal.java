package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AltyrusSummonGolemGoal extends Goal {

    private final AltyrusEntity altyrus;
    public int summonTime;

    public AltyrusSummonGolemGoal(AltyrusEntity altyrusEntity) {
        this.altyrus = altyrusEntity;
    }

    public boolean canUse() {
        return this.altyrus.getTarget() != null;
    }

    public void start() {
        this.summonTime = 0;
    }

    public void stop() {
        this.altyrus.setSummoning(false);
    }

    public void tick() {
        LivingEntity target = this.altyrus.getTarget();
        ServerWorld serverworld = (ServerWorld) this.altyrus.level;
        World world = this.altyrus.level;

        if (target != null && target.distanceToSqr(this.altyrus) < 4096.0D /*&& this.altyrus.canSee(target)*/) {
            ++this.summonTime;

            if (this.summonTime == 400) {
                BlockPos blockpos = this.altyrus.blockPosition();

                FrostbiteGolemEntity frostbite_golemEntity = BPEntities.FROSTBITE_GOLEM.get().create(world);
                frostbite_golemEntity.moveTo(blockpos, 0.0F, 0.0F);
                frostbite_golemEntity.setOwner(this.altyrus);
                frostbite_golemEntity.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                frostbite_golemEntity.setHasLimitedLife(true);
                frostbite_golemEntity.setExplodeOnExpiry(true);
                frostbite_golemEntity.setLifeLimitBeforeDeath(200);

                serverworld.addFreshEntity(frostbite_golemEntity);

                FrostbiteGolemEntity frostbite_golemEntity2 = BPEntities.FROSTBITE_GOLEM.get().create(world);
                frostbite_golemEntity2.moveTo(blockpos, 0.0F, 0.0F);
                frostbite_golemEntity2.setOwner(this.altyrus);
                frostbite_golemEntity2.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                frostbite_golemEntity2.setHasLimitedLife(true);
                frostbite_golemEntity2.setExplodeOnExpiry(true);
                frostbite_golemEntity2.setLifeLimitBeforeDeath(200);
                serverworld.addFreshEntity(frostbite_golemEntity2);

                this.summonTime = 0;
            }

            this.altyrus.setSummoning(this.summonTime > 360);
        }
    }
}
