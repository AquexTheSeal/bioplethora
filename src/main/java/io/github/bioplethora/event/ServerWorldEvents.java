package io.github.bioplethora.event;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.entity.creatures.HeliobladeEntity;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import io.github.bioplethora.item.weapons.SwervingTotemItem;
import io.github.bioplethora.registry.BioplethoraAdvancementHelper;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber
public class ServerWorldEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        PlayerEntity eventEntity = event.getPlayer();

        if (eventEntity != null) {
            if (BioplethoraConfig.COMMON.hellMode.get() && BioplethoraConfig.COMMON.hellModeReminder.get()) {
                if (!eventEntity.level.isClientSide()) {
                    eventEntity.displayClientMessage(new StringTextComponent("\u00A7cQuick Reminder: You are in Bioplethora Hell Mode. Most Bioplethora creatures will become stronger and more powerful."), (false));
                }
            }

            if (!eventEntity.level.isClientSide()) {
                BioplethoraAdvancementHelper.grantBioAdvancement(eventEntity, "bioplethora:bioplethora_startup");
            }

            if (BioplethoraConfig.COMMON.startupBiopedia.get()) {

                CompoundNBT playerData = event.getPlayer().getPersistentData();
                CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

                if (!data.getBoolean("has_biopedia")) {
                    ItemStack stack = new ItemStack(BioplethoraItems.BIOPEDIA.get());
                    stack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(eventEntity, stack);
                    data.putBoolean("has_biopedia", true);
                    playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {

        Entity defendantEnt = event.getEntity();
        Entity attackerEnt = event.getSource().getEntity();

        if (defendantEnt instanceof PlayerEntity) {

            Item bellophiteShield = BioplethoraItems.BELLOPHITE_SHIELD.get();
            ItemStack getUseItem = ((PlayerEntity) defendantEnt).getUseItem();
            Item getItem = getUseItem.getItem();

            boolean notFireDS = (event.getSource() != DamageSource.IN_FIRE);
            boolean notLavaDS = (event.getSource() != DamageSource.LAVA);
            boolean notCactusDS = (event.getSource() != DamageSource.CACTUS);
            boolean notVoidDS = (event.getSource() != DamageSource.OUT_OF_WORLD);

            if (notFireDS && notLavaDS && notCactusDS && notVoidDS) {
                if ((getItem == bellophiteShield) && (getItem instanceof BellophiteShieldItem)) {
                    ((BellophiteShieldItem) getItem).executeSkill(getUseItem, (LivingEntity) defendantEnt, defendantEnt.level);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        boolean dsFire = (event.getSource() == DamageSource.IN_FIRE);
        boolean dsVoid = (event.getSource() == DamageSource.OUT_OF_WORLD);
        boolean dsFire2 = (event.getSource() == DamageSource.ON_FIRE);

        if (event.getEntity() instanceof HeliobladeEntity) {

            HeliobladeEntity helioblade = (HeliobladeEntity) event.getEntity();
            int shouldDodge = helioblade.getRandom().nextInt(3);

            if (!dsFire && !dsVoid && !dsFire2) {
                if ((shouldDodge == 1) || (shouldDodge == 2)) {
                    helioblade.teleportRandomly();
                    event.setCanceled(true);
                }
            }
        }

        if (event.getEntity() instanceof GrylynenEntity) {

            GrylynenEntity grylynen = (GrylynenEntity) event.getEntity();

            if (!dsVoid) {
                event.setAmount(1);
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {

        Entity projectile = event.getEntity();
        RayTraceResult result = event.getRayTraceResult();

        if (result instanceof EntityRayTraceResult) {

            //=================================================
            //          Totem of Swerving Skill
            //=================================================
            boolean targetIsEntity = ((EntityRayTraceResult) result).getEntity() instanceof LivingEntity;

            if (projectile instanceof AbstractArrowEntity) {
                ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
            }

            if (!projectile.level.isClientSide && targetIsEntity) {
                LivingEntity user = ((LivingEntity) ((EntityRayTraceResult) result).getEntity());

                int shouldDodge = user.getRandom().nextInt(3);

                if ((shouldDodge == 1) && ((user.getOffhandItem().getItem() instanceof SwervingTotemItem) || (user.getMainHandItem().getItem() instanceof SwervingTotemItem))) {
                    Vector3d projectilePos = event.getEntity().position();
                    Vector3d rVec = user.getLookAngle().yRot(0.5F + (float) Math.PI).add(user.position());
                    Vector3d lVec = user.getLookAngle().yRot(0.5F + (float) Math.PI).add(user.position());

                    boolean rDir;

                    if (projectilePos.distanceTo(rVec) < projectilePos.distanceTo(lVec)) {
                        rDir = true;
                    } else if (projectilePos.distanceTo(rVec) > projectilePos.distanceTo(lVec)) {
                        rDir = false;
                    } else {
                        rDir = user.getRandom().nextBoolean();
                    }

                    Vector3d vectorThingy = event.getEntity().getDeltaMovement().yRot((float) ((rDir ? 0.5F : -0.5F) * Math.PI)).normalize();

                    user.level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GHAST_SHOOT, SoundCategory.PLAYERS, 1, 1);
                    if (user.level instanceof ServerWorld) {
                        ((ServerWorld) user.level).sendParticles(ParticleTypes.POOF, user.getX(), user.getY(), user.getZ(), 50, 0.65, 0.65, 0.65, 0.01);
                    }

                    if (user instanceof PlayerEntity) {

                        boolean isNegVal = user.getRandom().nextBoolean();
                        int tpLoc;
                        if (isNegVal) {
                            tpLoc = -(user.getRandom().nextInt(5));
                        } else {
                            tpLoc = user.getRandom().nextInt(5);
                        }

                        BlockPos blockpos = new BlockPos(user.getX() + tpLoc, user.getY(), user.getZ() + tpLoc);

                        if (!user.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                            user.moveTo(blockpos, 0.0F, 0.0F);
                        }

                    } else {
                        user.setDeltaMovement(user.getDeltaMovement().add(vectorThingy.x() * 0.5F, 0, vectorThingy.z() * 0.5F));
                    }

                    event.setCanceled(true);
                }
            }

            //=================================================
            //            Mob Special Skills
            //=================================================
            boolean targetIsAltyrus = ((EntityRayTraceResult) result).getEntity() instanceof AltyrusEntity;

            if (projectile instanceof AbstractArrowEntity) {
                ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
            }

            if (!projectile.level.isClientSide && targetIsAltyrus) {
                AltyrusEntity altyrus = ((AltyrusEntity) ((EntityRayTraceResult) result).getEntity());
                int shouldDodge = altyrus.getRandom().nextInt(3);

                if ((shouldDodge == 1) || (shouldDodge == 2)) {

                    Vector3d projectilePos = event.getEntity().position();
                    Vector3d rVec = altyrus.getLookAngle().yRot(0.5F + (float) Math.PI).add(altyrus.position());
                    Vector3d lVec = altyrus.getLookAngle().yRot(0.5F + (float) Math.PI).add(altyrus.position());
                    BlockPos pos = new BlockPos((int) altyrus.getX(), (int) altyrus.getY(), (int) altyrus.getZ());

                    boolean rDir;

                    if (projectilePos.distanceTo(rVec) < projectilePos.distanceTo(lVec)) {
                        rDir = true;
                    } else if (projectilePos.distanceTo(rVec) > projectilePos.distanceTo(lVec)) {
                        rDir = false;
                    } else {
                        rDir = altyrus.getRandom().nextBoolean();
                    }

                    Vector3d vectorThingy = event.getEntity().getDeltaMovement().yRot((float) ((rDir ? 0.5F : -0.5F) * Math.PI)).normalize();

                    altyrus.setDodging(true);
                    altyrus.level.playSound(null, pos, altyrus.getDodgeSound(), SoundCategory.HOSTILE, (float) 1, (float) 1);
                    altyrus.setDeltaMovement(altyrus.getDeltaMovement().add(vectorThingy.x() * 0.5F, 0, vectorThingy.z() * 0.5F));

                    event.setCanceled(true);
                }
            }

            boolean targetIsHelioblade = ((EntityRayTraceResult) result).getEntity() instanceof HeliobladeEntity;

            if (!projectile.level.isClientSide && targetIsHelioblade) {
                HeliobladeEntity helioblade = ((HeliobladeEntity) ((EntityRayTraceResult) result).getEntity());
                int shouldDodge = helioblade.getRandom().nextInt(3);

                if (projectile instanceof AbstractArrowEntity) {
                    ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
                }

                if ((shouldDodge == 1) || (shouldDodge == 2)) {

                    Vector3d projectilePos = event.getEntity().position();
                    Vector3d rVec = helioblade.getLookAngle().yRot(0.5F + (float) Math.PI).add(helioblade.position());
                    Vector3d lVec = helioblade.getLookAngle().yRot(0.5F + (float) Math.PI).add(helioblade.position());
                    BlockPos pos = new BlockPos((int) helioblade.getX(), (int) helioblade.getY(), (int) helioblade.getZ());

                    boolean rDir;

                    if (projectilePos.distanceTo(rVec) < projectilePos.distanceTo(lVec)) {
                        rDir = true;
                    } else if (projectilePos.distanceTo(rVec) > projectilePos.distanceTo(lVec)) {
                        rDir = false;
                    } else {
                        rDir = helioblade.getRandom().nextBoolean();
                    }

                    Vector3d vectorThingy = event.getEntity().getDeltaMovement().yRot((float) ((rDir ? 0.5F : -0.5F) * Math.PI)).normalize();

                    helioblade.setDeltaMovement(helioblade.getDeltaMovement().add(vectorThingy.x() * 1F, 0, vectorThingy.z() * 1F));

                    event.setCanceled(true);
                }
            }

            boolean targetIsPrimordialRing = ((EntityRayTraceResult) result).getEntity() instanceof PrimordialRingEntity;

            if (!projectile.level.isClientSide && targetIsPrimordialRing) {
                PrimordialRingEntity primordialRing = ((PrimordialRingEntity) ((EntityRayTraceResult) result).getEntity());

                if (projectile instanceof AbstractArrowEntity) {
                    ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
                }

                Vector3d projectilePos = event.getEntity().position();
                Vector3d rVec = primordialRing.getLookAngle().yRot(0.5F + (float) Math.PI).add(primordialRing.position());
                Vector3d lVec = primordialRing.getLookAngle().yRot(0.5F + (float) Math.PI).add(primordialRing.position());

                boolean rDir;

                if (projectilePos.distanceTo(rVec) < projectilePos.distanceTo(lVec)) {
                    rDir = true;
                } else if (projectilePos.distanceTo(rVec) > projectilePos.distanceTo(lVec)) {
                    rDir = false;
                } else {
                    rDir = primordialRing.getRandom().nextBoolean();
                }

                Vector3d vectorThingy = event.getEntity().getDeltaMovement().yRot((float) ((rDir ? 0.5F : -0.5F) * Math.PI)).normalize();

                primordialRing.setDeltaMovement(primordialRing.getDeltaMovement().add(vectorThingy.x() * 1F, 0, vectorThingy.z() * 1F));

                event.setCanceled(true);
            }
        }
    }
}
