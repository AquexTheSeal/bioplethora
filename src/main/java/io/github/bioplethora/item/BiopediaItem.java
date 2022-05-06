package io.github.bioplethora.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;

public class BiopediaItem extends Item {

    public BiopediaItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);

        if (ModList.get().isLoaded("patchouli")) {
            if (!world.isClientSide()) {

                if (playerIn instanceof ServerPlayerEntity) {
                    PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, Registry.ITEM.getKey(this));
                }
            }
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            playerIn.displayClientMessage(new TranslationTextComponent("message.bioplethora.biopedia.patchouli_notif")
                            .withStyle(TextFormatting.WHITE)
                            .withStyle(TextFormatting.BOLD)
                            .withStyle(TextFormatting.ITALIC),
                    true
            );
            return new ActionResult<>(ActionResultType.PASS, stack);
        }
    }
}
