package io.github.bioplethora.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BiopediaItem extends Item {

    public BiopediaItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (playerIn instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
            PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, Registry.ITEM.getKey(this));
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
