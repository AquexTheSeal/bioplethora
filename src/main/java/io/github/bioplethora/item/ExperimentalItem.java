package io.github.bioplethora.item;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.IBioClassification;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ExperimentalItem extends Item {

    public ExperimentalItem(Properties properties) {
        super(properties);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.CROSSBOW;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        entity.startUsingItem(handIn);
        return ActionResult.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
        super.releaseUsing(p_77615_1_, p_77615_2_, p_77615_3_, p_77615_4_);
        RegistryKey<Biome> biome = Biomes.NETHER_WASTES;
        Bioplethora.LOGGER.info(biome.getRegistryName().getNamespace() + ":" + biome.getRegistryName().getPath());
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.test_item.desc").withStyle(TextFormatting.GRAY));
    }
}