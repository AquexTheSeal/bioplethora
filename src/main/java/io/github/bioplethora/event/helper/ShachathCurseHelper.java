package io.github.bioplethora.event.helper;

import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ShachathCurseHelper {
    private final static String SHACHATH_CURSE = "bioplethora.shachath_curse";

    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof VillagerEntity) {
            if (event.getSource().getEntity() instanceof PlayerEntity) {
                PlayerEntity source = (PlayerEntity) event.getSource().getEntity();
                int curseLevel = nbt(source).getInt(SHACHATH_CURSE);

                if (EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(source)) {

                    nbt(source).putInt(SHACHATH_CURSE, curseLevel + 1);

                    if (curseLevel == 1) {
                        source.displayClientMessage(shachathMessage(1), true);
                    } else if (curseLevel == 10) {
                        source.displayClientMessage(shachathMessage(2), true);
                    } else if (curseLevel == 20) {
                        source.displayClientMessage(shachathMessage(3), true);
                    } else if (curseLevel == 30) {
                        source.displayClientMessage(shachathMessage(4), true);
                    } else if (curseLevel == 40) {
                        source.displayClientMessage(shachathMessage(5), true);
                    } else if (curseLevel == 50) {
                        source.displayClientMessage(shachathMessage(6), true);
                    } else if (curseLevel == 59) {
                        source.displayClientMessage(shachathMessage(7), true);
                    } else if (curseLevel == 60) {
                        source.displayClientMessage(shachathMessage(8), true);
                        summonShachath(source);
                    } else {
                        source.displayClientMessage(shachathMessage(0), true);
                    }
                }
            }
        }
    }

    public static void summonShachath(PlayerEntity player) {
        ShachathEntity effect = BPEntities.SHACHATH.get().create(player.level);
        BlockPos summonPos = new BlockPos(player.blockPosition()).offset(-5 + player.getRandom().nextInt(10), 0, -5 + player.getRandom().nextInt(10));
        effect.moveTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
        effect.setTarget(player);
        player.level.addFreshEntity(effect);

        effect.descendEffect();
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.BLAZE_SHOOT, 0.9F));
    }

    private static IFormattableTextComponent shachathMessage(int level) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.9F));
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.ENDERMAN_TELEPORT, 1.5F));
        return new TranslationTextComponent("message.bioplethora.shachath_curse_" + level).withStyle(TextFormatting.DARK_RED);
    }

    private static CompoundNBT nbt(PlayerEntity player) {
        CompoundNBT playerData = player.getPersistentData();
        return playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
    }
}
