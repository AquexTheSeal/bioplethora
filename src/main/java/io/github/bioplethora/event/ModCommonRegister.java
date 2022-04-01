package io.github.bioplethora.event;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.BellophiteArrowEntity;
import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonRegister {

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.initUnaddedEggs();
    }

    @SubscribeEvent
    public static void registerDispenserBehaviors(FMLCommonSetupEvent event) {

        DispenserBlock.registerBehavior(BPItems.WIND_ARROW.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectile(World pLevel, IPosition pPosition, ItemStack pStack) {
                WindArrowEntity arrowentity = new WindArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.BELLOPHITE_ARROW.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectile(World pLevel, IPosition pPosition, ItemStack pStack) {
                BellophiteArrowEntity arrowentity = new BellophiteArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.MAGMA_BOMB.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectile(World pLevel, IPosition pPosition, ItemStack pStack) {
                return Util.make(new MagmaBombEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (p_218408_1_) -> {
                });
            }
        });
    }
}
