package io.github.bioplethora.entity.others;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.enums.BPGrylynenTier;
import io.github.bioplethora.event.helper.GrylynenSpawnHelper;
import io.github.bioplethora.registry.BioplethoraAdvancementHelper;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrylynenCoreBombEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public IGrylynenTier tier;
    public int birthTime = 0;

    public GrylynenCoreBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen_core_bomb.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "grylynen_core_bomb_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void tick() {
        super.tick();

        ++birthTime;
        this.level.addParticle(ParticleTypes.FLAME,
                getX() + (Math.random() / 2), getY() + (Math.random() / 2), getZ() + (Math.random() / 2),
                0.01D, 0.01D, 0.01D);

        if (this.birthTime >= (BioplethoraConfig.getHellMode ? 40 : 60)) {

            if (!this.level.isClientSide) {
                ServerWorld serverworld = (ServerWorld) this.level;
                BlockPos blockpos = (new BlockPos(this.getX(), this.getY(), this.getZ()));

                GrylynenEntity grylynen = BioplethoraEntities.NETHERITE_GRYLYNEN.get().create(this.level);

                if (this.tier == BPGrylynenTier.WOODEN) {
                    grylynen = BioplethoraEntities.WOODEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.STONE) {
                    grylynen = BioplethoraEntities.STONE_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.GOLDEN) {
                    grylynen = BioplethoraEntities.GOLDEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.IRON) {
                    grylynen = BioplethoraEntities.IRON_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.DIAMOND) {
                    grylynen = BioplethoraEntities.DIAMOND_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.NETHERITE) {
                    grylynen = BioplethoraEntities.NETHERITE_GRYLYNEN.get().create(this.level);
                }

                grylynen.moveTo(blockpos, 0.0F, 0.0F);
                grylynen.finalizeSpawn(serverworld, level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    GrylynenSpawnHelper.breakSurroundingBlocks(serverworld, blockpos);
                }
                serverworld.addFreshEntity(grylynen);

                this.remove();
            }
        }
    }

    public void setTier(IGrylynenTier tier) {
        this.tier = tier;
    }

    public void summonParticleBarrier(ServerWorld serverWorld) {

        int loop = 0; int particleAmount = 10; int xRad = 3; int zRad = 3;

        if (loop < particleAmount) {
            serverWorld.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + 0.5 + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                    this.getY(),
                    this.getZ() + 0.5 + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                    0.0, 1.0, 0.0);
            ++loop;
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void grantBirthAdvancement(int radius) {
        Entity entity = this.getEntity();
        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        AxisAlignedBB area = new AxisAlignedBB(x - (radius / 2d), y, z - (radius / 2d), x + (radius / 2d), y + (radius / 2d), z + (radius / 2d));

        //Grant Advancement to all nearby players
        for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entityIterator != null) {
                BioplethoraAdvancementHelper.grantBioAdvancement(entityIterator, "bioplethora:altyrus_summoning");
            }
        }
    }
}
