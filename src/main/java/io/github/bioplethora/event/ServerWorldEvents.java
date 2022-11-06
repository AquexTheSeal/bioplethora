package io.github.bioplethora.event;

import io.github.bioplethora.api.IHurtSkillArmor;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.advancements.AdvancementUtils;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.event.helper.*;
import io.github.bioplethora.item.ExperimentalItem;
import io.github.bioplethora.item.functionals.SwervingTotemItem;
import io.github.bioplethora.item.weapons.AbyssalBladeItem;
import io.github.bioplethora.item.weapons.FrostbiteMetalShieldItem;
import io.github.bioplethora.item.weapons.GrylynenShieldBaseItem;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.LeftSwingPacket;
import io.github.bioplethora.network.functions.RightSwingPacket;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEffects;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber
public class ServerWorldEvents {

    @SubscribeEvent
    public static void playerTickDebug(TickEvent.PlayerTickEvent event) {
        /*
        if (event.player.getMainHandItem().getItem() == BPItems.TEST_ITEM.get()) {
            event.player.displayClientMessage(new StringTextComponent("xRot: " + event.player.xRot), true);
        } else {
            event.player.displayClientMessage(new StringTextComponent("yRot: " + event.player.yRot), true);
        }*/
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {

        hitHandler(event.getPlayer(), event.getItemStack());

        if (event.getItemStack().getItem() instanceof IReachWeapon) {
            if (event.getWorld().isClientSide()) {
                BPNetwork.CHANNEL.sendToServer(new LeftSwingPacket());
            }
        }
    }


    /**
     * Off-Hand combat integration
     */
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickEmpty event) {

        if (ModList.get().isLoaded("offhandcombat")) {

            hitHandler(event.getPlayer(), event.getItemStack());

            if (event.getItemStack().getItem() instanceof IReachWeapon) {
                if (event.getWorld().isClientSide()) {
                    BPNetwork.CHANNEL.sendToServer(new RightSwingPacket());
                }
            }
        }
    }

    public static void hitHandler(PlayerEntity entity, ItemStack stack) {

        if (stack.getItem() instanceof IReachWeapon) {

            double range = ((IReachWeapon) stack.getItem()).getReachDistance();
            double distance = range * range;
            Vector3d vec = entity.getEyePosition(1);
            Vector3d vec1 = entity.getViewVector(1);
            Vector3d targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
            AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
            EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, vec, targetVec, aabb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR, distance);

            if ((result != null ? result.getEntity() : null) != null) {
                entity.attack(result.getEntity());
            }

            if (result == null) {
                if (stack.getItem() instanceof InfernalQuarterstaffItem) {
                    ((InfernalQuarterstaffItem) stack.getItem()).emptySwingHandler(stack, entity);
                }
            }

            if (stack.getItem() instanceof AbyssalBladeItem) {
                ((AbyssalBladeItem) stack.getItem()).shootHandler(entity, stack, 0);
                ((AbyssalBladeItem) stack.getItem()).shootHandler(entity, stack, -15);
                ((AbyssalBladeItem) stack.getItem()).shootHandler(entity, stack, 15);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        PlayerEntity eventEntity = event.getPlayer();

        if (eventEntity != null) {
            if (BPConfig.COMMON.hellMode.get() && BPConfig.COMMON.hellModeReminder.get()) {
                if (!eventEntity.level.isClientSide()) {
                    eventEntity.displayClientMessage(new StringTextComponent("\u00A7cQuick Reminder: You are in Bioplethora Hell Mode. Most Bioplethora creatures will become stronger and more powerful."), (false));
                }
            }

            if (!eventEntity.level.isClientSide()) {
                AdvancementUtils.grantBioAdvancement(eventEntity, "bioplethora:bioplethora_startup");
            }

            if (BPConfig.COMMON.startupBiopedia.get()) {

                CompoundNBT playerData = event.getPlayer().getPersistentData();
                CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

                if (!data.getBoolean("has_biopedia")) {
                    ItemStack stack = new ItemStack(BPItems.BIOPEDIA.get());
                    stack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(eventEntity, stack);
                    data.putBoolean("has_biopedia", true);
                    playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        TooltipEventHelper.onTooltip(event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        GrylynenSpawnHelper.onBlockBreak(event);
    }

    @SubscribeEvent
    public static void performBonemealAction(BonemealEvent event) {
        BonemealBlocksHelper.performBonemealAction(event);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {

        Entity defendantEnt = event.getEntity();
        Entity attackerEnt = event.getSource().getEntity();

        if (defendantEnt instanceof PlayerEntity) {

            ItemStack getUseItem = ((PlayerEntity) defendantEnt).getUseItem();
            Item getItem = getUseItem.getItem();

            boolean[] dmgEx = new boolean[]{
                    event.getSource() != DamageSource.IN_FIRE,
                    event.getSource() != DamageSource.LAVA,
                    event.getSource() != DamageSource.CACTUS,
                    event.getSource() != DamageSource.OUT_OF_WORLD
            };

            if (dmgEx[0] && dmgEx[1] && dmgEx[2] && dmgEx[3]) {
                if (getItem instanceof FrostbiteMetalShieldItem) {
                    ((FrostbiteMetalShieldItem) getItem).executeSkill(getUseItem, (LivingEntity) defendantEnt, defendantEnt.level);
                }

                if (getItem instanceof GrylynenShieldBaseItem) {

                    int recoveryAmount = ((GrylynenShieldBaseItem) getItem).getArmorBonus();
                    LivingEntity defendantLiving = (LivingEntity) defendantEnt;

                    ((GrylynenShieldBaseItem) getItem).blockingSkill(getUseItem, defendantLiving, attackerEnt, event.getEntity().level);

                    defendantLiving.getItemBySlot(EquipmentSlotType.HEAD)
                            .setDamageValue(defendantLiving.getItemBySlot(EquipmentSlotType.HEAD).getDamageValue() - recoveryAmount);
                    defendantLiving.getItemBySlot(EquipmentSlotType.CHEST)
                            .setDamageValue(defendantLiving.getItemBySlot(EquipmentSlotType.CHEST).getDamageValue() - recoveryAmount);
                    defendantLiving.getItemBySlot(EquipmentSlotType.LEGS)
                            .setDamageValue(defendantLiving.getItemBySlot(EquipmentSlotType.LEGS).getDamageValue() - recoveryAmount);
                    defendantLiving.getItemBySlot(EquipmentSlotType.FEET)
                            .setDamageValue(defendantLiving.getItemBySlot(EquipmentSlotType.FEET).getDamageValue() - recoveryAmount);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        boolean dsFire = (event.getSource() == DamageSource.IN_FIRE);
        boolean dsVoid = (event.getSource() == DamageSource.OUT_OF_WORLD);
        boolean dsFire2 = (event.getSource() == DamageSource.ON_FIRE);

        if (event.getEntity() instanceof LivingEntity) {

            LivingEntity living = (LivingEntity) event.getEntity();
            ItemStack sHead = living.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack sChest = living.getItemBySlot(EquipmentSlotType.CHEST);
            ItemStack sLegs = living.getItemBySlot(EquipmentSlotType.LEGS);
            ItemStack sFeet = living.getItemBySlot(EquipmentSlotType.FEET);

            if (event.getSource().getEntity() instanceof LivingEntity) {
                if (sHead.getItem() instanceof IHurtSkillArmor) {
                    ((IHurtSkillArmor) sHead.getItem()).onUserHurtWithArmor(living, (LivingEntity) event.getSource().getEntity(), sHead);
                }
                if (sChest.getItem() instanceof IHurtSkillArmor) {
                    ((IHurtSkillArmor) sChest.getItem()).onUserHurtWithArmor(living, (LivingEntity) event.getSource().getEntity(), sChest);
                }
                if (sLegs.getItem() instanceof IHurtSkillArmor) {
                    ((IHurtSkillArmor) sLegs.getItem()).onUserHurtWithArmor(living, (LivingEntity) event.getSource().getEntity(), sLegs);
                }
                if (sFeet.getItem() instanceof IHurtSkillArmor) {
                    ((IHurtSkillArmor) sFeet.getItem()).onUserHurtWithArmor(living, (LivingEntity) event.getSource().getEntity(), sFeet);
                }
            }
        }

        if (event.getSource().getEntity() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            if (((LivingEntity) event.getSource().getEntity()).hasEffect(BPEffects.SPIRIT_STRENGTHENING.get())) {
                float dmgCap = BPConfig.IN_HELLMODE ? 7 : 12;
                float floorReduction = MathHelper.ceil(((LivingEntity) event.getEntity()).getHealth() * 0.025F);
                float floor = MathHelper.floor(((LivingEntity) event.getEntity()).getHealth() * 0.05F);
                float armorReduction = MathHelper.ceil(((LivingEntity) event.getEntity()).getArmorValue() / 4F);
                float healthScaledDmg = MathHelper.clamp(floor - floorReduction, 0.0F, dmgCap);
                event.setAmount((event.getAmount() * 1.10F) + healthScaledDmg - armorReduction);
            }
        }

        if (event.getEntity() instanceof ShachathEntity) {

            ShachathEntity shachath = (ShachathEntity) event.getEntity();
            int shouldDodge = shachath.getRandom().nextInt(3);

            if (!dsFire && !dsVoid && !dsFire2) {
                if ((shouldDodge == 1) || (shouldDodge == 2)) {
                    shachath.teleportRandomly();
                    event.setCanceled(true);
                }
            }
        }

        if (event.getEntity() instanceof AlphemKingEntity) {

            AlphemKingEntity king = (AlphemKingEntity) event.getEntity();

            if (!dsVoid) {
                if (king.isBarriered()) {
                    king.playSound(SoundEvents.GLASS_BREAK, 1.5F, 1.0F);
                    king.setBarriered(false);
                    event.setCanceled(true);

                    if (!king.level.isClientSide()) {
                        ((ServerWorld) king.level).sendParticles(ParticleTypes.ASH, king.getX(), king.getY() + 1, king.getZ(),
                                30, 0.75, 0.75, 0.75, 0.01);
                    }
                }
            }
        }

        MobCapEventHelper.onEntityHurt(event);
    }

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {

        ArrowMixinHelper.onProjectileImpact(event);
        AlphemKingSpawnHelper.onProjectileImpact(event);

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

                if ((user.getOffhandItem().getItem() instanceof SwervingTotemItem) || (user.getMainHandItem().getItem() instanceof SwervingTotemItem)) {

                    if (shouldDodge == 1) {

                        boolean isNegVal = user.getRandom().nextBoolean();
                        int tpLoc = 1 + user.getRandom().nextInt(2);

                        user.level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GHAST_SHOOT, SoundCategory.PLAYERS, 1, 1);
                        if (user.level instanceof ServerWorld) {
                            ((ServerWorld) user.level).sendParticles(ParticleTypes.POOF, user.getX(), user.getY(), user.getZ(), 50, 0.65, 0.65, 0.65, 0.01);
                        }

                        BlockPos blockpos = new BlockPos(user.getX() + (isNegVal ? tpLoc : -tpLoc), user.getY(), user.getZ() + (isNegVal ? tpLoc : -tpLoc));

                        if (!user.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                            user.teleportTo(blockpos.getX(), blockpos.getY(), blockpos.getZ());

                            event.setCanceled(true);
                        }
                    }
                }
            }

            //=================================================
            //            Mob Special Skills
            //=================================================
            boolean targetIsAltyrus = ((EntityRayTraceResult) result).getEntity() instanceof AltyrusEntity;

            if (projectile instanceof AbstractArrowEntity) {
                ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
            }

            if (!projectile.level.isClientSide() && targetIsAltyrus) {
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

            boolean targetIsShachath = ((EntityRayTraceResult) result).getEntity() instanceof ShachathEntity;

            if (!projectile.level.isClientSide && targetIsShachath) {
                ShachathEntity shachath = ((ShachathEntity) ((EntityRayTraceResult) result).getEntity());
                int shouldDodge = shachath.getRandom().nextInt(3);

                if (projectile instanceof AbstractArrowEntity) {
                    ((AbstractArrowEntity) projectile).setPierceLevel((byte) 0);
                }

                if ((shouldDodge == 1) || (shouldDodge == 2)) {

                    Vector3d projectilePos = event.getEntity().position();
                    Vector3d rVec = shachath.getLookAngle().yRot(0.5F + (float) Math.PI).add(shachath.position());
                    Vector3d lVec = shachath.getLookAngle().yRot(0.5F + (float) Math.PI).add(shachath.position());
                    BlockPos pos = new BlockPos((int) shachath.getX(), (int) shachath.getY(), (int) shachath.getZ());

                    boolean rDir;

                    if (projectilePos.distanceTo(rVec) < projectilePos.distanceTo(lVec)) {
                        rDir = true;
                    } else if (projectilePos.distanceTo(rVec) > projectilePos.distanceTo(lVec)) {
                        rDir = false;
                    } else {
                        rDir = shachath.getRandom().nextBoolean();
                    }

                    Vector3d vectorThingy = event.getEntity().getDeltaMovement().yRot((float) ((rDir ? 0.5F : -0.5F) * Math.PI)).normalize();

                    shachath.setDeltaMovement(shachath.getDeltaMovement().add(vectorThingy.x() * 1F, 0, vectorThingy.z() * 1F));

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

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        PlayerEntity player = event.getPlayer();

        if (player.getMainHandItem().getItem() instanceof ExperimentalItem) {
            BlockUtils.knockUpRandomNearbyBlocks(world, 0.5D, pos, 4, 2, 4, false, true);
        }

        if (block == BPBlocks.FIERY_BASALT_SPELEOTHERM.get()) {
            if (player.getMainHandItem().getItem() == Items.GLASS_BOTTLE) {
                player.swing(Hand.MAIN_HAND);
                player.addItem(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()));
                world.setBlock(pos, BPBlocks.BASALT_SPELEOTHERM_PLANT.get().defaultBlockState(), 2);
                player.getMainHandItem().shrink(1);
            } else if (player.getOffhandItem().getItem() == Items.GLASS_BOTTLE) {
                player.swing(Hand.OFF_HAND);
                player.addItem(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()));
                world.setBlock(pos, BPBlocks.BASALT_SPELEOTHERM_PLANT.get().defaultBlockState(), 2);
                player.getOffhandItem().shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        RenderEventHelper.onPlayerTick(event);
    }

    @SubscribeEvent
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        RenderEventHelper.onCameraSetup(event);
    }

    @SubscribeEvent
    public static void onRenderingPlayer(RenderPlayerEvent event) {
        RenderEventHelper.onRenderingPlayer(event);
    }

    @SubscribeEvent
    public static void onRenderingOverlay(RenderGameOverlayEvent.Pre event) {
        RenderEventHelper.onRenderingOverlay(event);
    }

    @SubscribeEvent
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        RenderEventHelper.onFogDensity(event);
    }
}
