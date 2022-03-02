package io.github.bioplethora.item;

import io.github.bioplethora.entity.IBioClassification;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
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
            switch (((IBioClassification) entity).getBioplethoraClass()) {
                case NONE: player.displayClientMessage(new StringTextComponent("Entity Class: None"), (false));
                case ECOHARMLESS: player.displayClientMessage(new StringTextComponent("Entity Class: Ecoharmless"), (false));
                case PLETHONEUTRAL: player.displayClientMessage(new StringTextComponent("Entity Class: Plethoneutral"), (false));
                case DANGERUM: player.displayClientMessage(new StringTextComponent("Entity Class: Dangerum"), (false));
                case HELLSENT: player.displayClientMessage(new StringTextComponent("Entity Class: Hellsent"), (false));
                case ELDERIA: player.displayClientMessage(new StringTextComponent("Entity Class: Elderia"), (false));
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