package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.NucleusActivatePacket;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AlphanumNucleusTileEntity extends TileEntity implements ITickableTileEntity {
    public int activeTicks;
    public int summonCounter;
    
    public AlphanumNucleusTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public AlphanumNucleusTileEntity() {
        this(BPTileEntities.ALPHANUM_NUCLEUS.get());
    }

    @Override
    public void tick() {
        if (getBlockState().getValue(AlphanumNucleusBlock.ACTIVATED)) {
            ++activeTicks;
            BPNetwork.CHANNEL.sendToServer(new NucleusActivatePacket(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()));

            int areaint = 15;
            AxisAlignedBB aabb = new AxisAlignedBB(getBlockPos().getX() - areaint, getBlockPos().getY() - areaint, getBlockPos().getZ() - areaint, getBlockPos().getX() + areaint, getBlockPos().getY() + areaint, getBlockPos().getZ() + areaint);
            for (PlayerEntity areaEnt : getLevel().getEntitiesOfClass(PlayerEntity.class, aabb)) {
                ((IPlayerEntityMixin) areaEnt).setScreenShaking(5);
            }

            if (!getLevel().isClientSide()) {
                ((ServerWorld) getLevel()).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getBlockPos().getX(), getBlockPos().getY() + 0.5D, getBlockPos().getZ(), 7, 3.5, 3.5, 3.5, 0.1);
            }

            if (activeTicks >= 20) {
                ++summonCounter;
                if (!getLevel().isClientSide()) {
                    ((ServerWorld) getLevel()).sendParticles(BPParticles.KINGS_ROAR.get(), getBlockPos().getX(), getBlockPos().getY() + 0.5D, getBlockPos().getZ(), 1, 0, 0, 0, 0);
                }
                activeTicks = 0;
            }

            if (summonCounter >= 5) {
                AlphemKingEntity king = new AlphemKingEntity(BPEntities.ALPHEM_KING.get(), getLevel());
                king.moveTo(getBlockPos(), 30.0F, 30.0F);
                king.setBarriered(true);
                if (!getLevel().isClientSide()) {
                    ((ServerWorld) getLevel()).sendParticles(ParticleTypes.EXPLOSION, getBlockPos().getX(), getBlockPos().getY() + 0.5D, getBlockPos().getZ(), 15, 1.25, 1.25, 1.25, 0.1);
                    BlockUtils.destroyAllNearbyBlocks(getLevel(), getBlockPos(), 3, 2, 3, false);
                }
                getLevel().addFreshEntity(king);
                king.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 1.0F, 1.0F);
            }
        }
    }
}
