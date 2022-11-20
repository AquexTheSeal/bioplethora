package io.github.bioplethora.event.helper;

import io.github.bioplethora.blocks.tile_entities.AlphanumNucleusBlock;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.NucleusActivatePacket;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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
                BlockPos pos = blockResult.getBlockPos();

                if (world.getBlockState(blockResult.getBlockPos()).getBlock() == BPBlocks.ALPHANUM_NUCLEUS.get()) {

                    if (windArrow.getOwner() instanceof ServerPlayerEntity) {
                        world.setBlock(pos, world.getBlockState(blockResult.getBlockPos()).setValue(AlphanumNucleusBlock.ACTIVATED, true), 2);

                        if (!world.isClientSide()) {
                            ((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, windArrow.getX(), windArrow.getY(), windArrow.getZ(),
                                    30, 1.2, 1.2, 1.2, 0.1);

                            if (BPConfig.COMMON.announceAlphemKing.get()) {
                                List<ServerPlayerEntity> list = ((ServerWorld) world).getPlayers((playerEntity) -> true);
                                for (ServerPlayerEntity serverplayerentity : list) {
                                    serverplayerentity.displayClientMessage(new TranslationTextComponent("message.bioplethora.alphem_king.summon",
                                                    windArrow.getOwner().getDisplayName(), (float) windArrow.getX(), (float) windArrow.getY(), (float) windArrow.getZ())
                                                    .withStyle(TextFormatting.RED).withStyle(TextFormatting.ITALIC),
                                            false);
                                }
                            }
                        }
                    }

                    windArrow.remove();
                }
            }
        }
    }
}
