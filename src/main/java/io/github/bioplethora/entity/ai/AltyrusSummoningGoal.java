package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AltyrusSummoningGoal extends Goal {

    private final AltyrusEntity altyrus;
    public int summonTime;

    public AltyrusSummoningGoal(AltyrusEntity altyrusEntity) {
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
        LivingEntity livingentity = this.altyrus.getTarget();
        ServerWorld serverworld = (ServerWorld)this.altyrus.level;
        World world = this.altyrus.level;

        ++this.summonTime;

        if (this.summonTime > 400) {
            BlockPos blockpos = this.altyrus.blockPosition();

            BellophgolemEntity bellophgolemEntity = BioplethoraEntities.BELLOPHGOLEM.get().create(world);
            bellophgolemEntity.moveTo(blockpos, 0.0F, 0.0F);
            bellophgolemEntity.setOwner(this.altyrus);
            bellophgolemEntity.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);

            bellophgolemEntity.setLimitedLife(0);
            bellophgolemEntity.setExplodeOnExpiry(true);
            bellophgolemEntity.setLifeLimitBeforeDeath(200);

            serverworld.addFreshEntityWithPassengers(bellophgolemEntity);

            BellophgolemEntity bellophgolemEntity2 = BioplethoraEntities.BELLOPHGOLEM.get().create(world);
            bellophgolemEntity2.moveTo(blockpos, 0.0F, 0.0F);
            bellophgolemEntity2.setOwner(this.altyrus);
            bellophgolemEntity2.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);

            bellophgolemEntity2.setLimitedLife(0);
            bellophgolemEntity2.setExplodeOnExpiry(true);
            bellophgolemEntity2.setLifeLimitBeforeDeath(200);

            serverworld.addFreshEntityWithPassengers(bellophgolemEntity2);

            this.summonTime = 0;
        }

        this.altyrus.setSummoning(this.summonTime > 360);
    }
}
