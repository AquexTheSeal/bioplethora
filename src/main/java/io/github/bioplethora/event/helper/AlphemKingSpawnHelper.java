package io.github.bioplethora.event.helper;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import java.util.List;

public class AlphemKingSpawnHelper {

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Entity projectile = event.getEntity();
        World world = projectile.level;
        RayTraceResult result = event.getRayTraceResult();

        if (projectile instanceof WindArrowEntity) {
            WindArrowEntity windArrow = (WindArrowEntity) projectile;
            if (result instanceof BlockRayTraceResult) {
                BlockRayTraceResult blockResult = (BlockRayTraceResult) result;

                if (world.getBlockState(blockResult.getBlockPos()).getBlock() == BPBlocks.ALPHANUM_NUCLEUS.get()) {

                    BlockUtils.destroyAllNearbyBlocks(world, blockResult.getBlockPos(), 3, 2, 3, false);

                    if (!world.isClientSide()) {
                        ((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, windArrow.getX(), windArrow.getY(), windArrow.getZ(),
                                30, 1.2, 1.2, 1.2, 0.1);

                        if (BPConfig.COMMON.announceAlphemKing.get()) {
                            List<ServerPlayerEntity> list = ((ServerWorld) world).getPlayers((playerEntity) -> true);
                            for (ServerPlayerEntity serverplayerentity : list) {
                                serverplayerentity.playSound(SoundEvents.WITHER_DEATH, 1.0F, 1.0F);
                                serverplayerentity.displayClientMessage(new TranslationTextComponent("message.bioplethora.alphem_king.summon",
                                                windArrow.getOwner().getDisplayName(), windArrow.getX(), windArrow.getY(), windArrow.getZ())
                                                .withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD),
                                        false);
                            }
                        }
                    }

                    windArrow.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 1.0F, 1.0F);

                    AlphemKingEntity king = new AlphemKingEntity(BPEntities.ALPHEM_KING.get(), world);
                    king.moveTo(blockResult.getBlockPos(), 30.0F, 30.0F);
                    king.setBarriered(true);
                    world.addFreshEntity(king);

                    windArrow.remove();
                }
            }
        }
    }
}
