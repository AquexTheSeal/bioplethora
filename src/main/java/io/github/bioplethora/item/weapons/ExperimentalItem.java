package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.projectile.WindblazeEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class ExperimentalItem extends Item {

    public ExperimentalItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemInHand = playerIn.getItemInHand(handIn);

        if (!(worldIn instanceof ServerWorld))
            return new ActionResult<>(ActionResultType.PASS, itemInHand);

        float AngleX = -MathHelper.sin(playerIn.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.xRot * ((float) Math.PI / 180F));
        float AngleY = -MathHelper.sin(playerIn.xRot * ((float) Math.PI / 180F));
        float AngleZ = MathHelper.cos(playerIn.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.xRot * ((float) Math.PI / 180F));

        WindblazeEntity windblazeEntity = new WindblazeEntity(worldIn, playerIn, AngleX, AngleY, AngleZ);
        windblazeEntity.setPos(playerIn.getX(), playerIn.getY() + 1.5, playerIn.getZ());
        windblazeEntity.shootFromRotation(windblazeEntity, playerIn.xRot, playerIn.yHeadRot, 0, 1F, 0);

        worldIn.addFreshEntity(windblazeEntity);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.special_skill.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.test_item.desc_0").withStyle(TextFormatting.GRAY));
    }
}