package io.github.bioplethora.event;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.*;
import io.github.bioplethora.entity.projectile.FrostbiteMetalArrowEntity;
import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonRegister {

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.initUnaddedEggs();
    }

    @SubscribeEvent
    public static void registerDispenserBehaviors(FMLCommonSetupEvent event) {

        DispenserBlock.registerBehavior(BPItems.WIND_ARROW.get(), new ProjectileDispenseBehavior() {
            protected @NotNull ProjectileEntity getProjectile(@NotNull World pLevel, @NotNull IPosition pPosition, @NotNull ItemStack pStack) {
                WindArrowEntity arrowentity = new WindArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.BELLOPHITE_ARROW.get(), new ProjectileDispenseBehavior() {
            protected @NotNull ProjectileEntity getProjectile(@NotNull World pLevel, @NotNull IPosition pPosition, @NotNull ItemStack pStack) {
                FrostbiteMetalArrowEntity arrowentity = new FrostbiteMetalArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.MAGMA_BOMB.get(), new ProjectileDispenseBehavior() {
            protected @NotNull ProjectileEntity getProjectile(@NotNull World pLevel, @NotNull IPosition pPosition, @NotNull ItemStack pStack) {
                return Util.make(new MagmaBombEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (p_218408_1_) -> {
                });
            }
        });
    }


    @SubscribeEvent
    public static void registerEntityPlacements(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(BPEntities.CUTTLEFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CuttlefishEntity::checkCuttlefishSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.ONOFISH.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OnofishEntity::checkOnofishSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.TRIGGERFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.FIERY_EURYDN.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.SOUL_EURYDN.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);

        EntitySpawnPlacementRegistry.register(BPEntities.DWARF_MOSSADILE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.GAUGALEM.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GaugalemEntity::checkGaugalemSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.MYUTHINE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);

        EntitySpawnPlacementRegistry.register(BPEntities.CREPHOXL.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(BPEntities.VOIDJAW.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VoidjawEntity::checkVoidjawSpawnRules);

        EntitySpawnPlacementRegistry.register(BPEntities.MYLIOTHAN.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MyliothanEntity::checkMyliothanSpawnRules);
    }
}
