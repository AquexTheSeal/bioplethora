package io.github.bioplethora.item;

import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
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

        if (playerIn.isCrouching()) {
            BlockPos blockpos = playerIn.blockPosition();

            PrimordialRingEntity bellophgolemEntity = BioplethoraEntities.PRIMORDIAL_RING.get().create(worldIn);
            bellophgolemEntity.moveTo(blockpos, 0.0F, 0.0F);
            bellophgolemEntity.setOwner(playerIn);
            bellophgolemEntity.finalizeSpawn((IServerWorld) worldIn, worldIn.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

            bellophgolemEntity.setHasLimitedLife(true);
            bellophgolemEntity.setLifeLimitBeforeDeath(1000 + playerIn.getRandom().nextInt(300));

            worldIn.addFreshEntity(bellophgolemEntity);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemInHand);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {

        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        PlayerEntity player = (PlayerEntity) source;

        boolean retval = super.hurtEnemy(stack, entity, source);

        if (entity instanceof IBioClassification) {
            if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.NONE) {
                player.displayClientMessage(new StringTextComponent("Entity Class: None"), (false));
            } else if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.ECOHARMLESS) {
                player.displayClientMessage(new StringTextComponent("Entity Class: Ecoharmless"), (false));
            } else if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.PLETHONEUTRAL) {
                player.displayClientMessage(new StringTextComponent("Entity Class: Plethoneutral"), (false));
            } else if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.DANGERUM) {
                player.displayClientMessage(new StringTextComponent("Entity Class: Dangerum"), (false));
            } else if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.HELLSENT) {
                player.displayClientMessage(new StringTextComponent("\u00A7cEntity Class: Hellsent"), (false));
            } else if (((IBioClassification) entity).getBioplethoraClass() == BPEntityClasses.ELDERIA) {
                player.displayClientMessage(new StringTextComponent("\u00A7cEntity Class: Elderia"), (false));
            }
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.test_item.desc").withStyle(TextFormatting.GRAY));
    }
}