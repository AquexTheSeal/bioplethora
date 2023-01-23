package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.NucleusActivatePacket;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

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

                Vector3d from = new Vector3d(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
                Vector3d to = new Vector3d(getBlockPos().getX() + (-40 + getLevel().getRandom().nextInt(80)), 255, getBlockPos().getZ() + (-40 + getLevel().getRandom().nextInt(80)));
                Vector3d per = to.subtract(from).normalize();
                Vector3d current = from.add(0, 0, 0);
                double distance = from.distanceTo(to);
                for (double i = 0; i < distance; i++) {
                    ((ServerWorld) getLevel()).sendParticles(ParticleTypes.POOF, current.x(), current.y(), current.z(), 1, 0, 0, 0, 0);
                    current = current.add(per);
                }
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
                    king.finalizeSpawn((ServerWorld) getLevel(), getLevel().getCurrentDifficultyAt(getBlockPos()), SpawnReason.MOB_SUMMONED, null, null);
                    BlockUtils.destroyAllNearbyBlocks(getLevel(), getBlockPos(), 3, 2, 3, false);
                    constructArena();
                }
                getLevel().addFreshEntity(king);

                BPEffectEntity effect = BPEntities.BP_EFFECT.get().create(getLevel());
                effect.setEffectType(BPEffectTypes.ALPHANUM_FIRE);
                effect.setOwner(king);
                effect.moveTo(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
                getLevel().addFreshEntity(effect);

                king.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 1.0F, 1.0F);
            }
        }
    }

    public void constructArena() {
        int genRad = 32;
        this.createPart(genRad, -1, BPBlocks.ALPHANUM.get());
        this.createPart(genRad + 2, 0, BPBlocks.ALPHANUM.get());
        for (int i = 1; i < 16; i++) {
            if (i == 13 || i == 15) {
                this.createPart((genRad + 2) + i, i, Blocks.GREEN_STAINED_GLASS);
            } else if (i == 14) {
                this.createPart((genRad + 2) + i, i, Blocks.GLOWSTONE);
            } else {
                this.createPart((genRad + 2) + i, i, BPBlocks.ALPHANUM_BRICKS.get());
            }
            this.createPart((genRad + 1) + i, i, Blocks.AIR);
        }
        createColumnGroup(24);
    }

    public void createColumnGroup(int spreadValue) {
        createColumn((spreadValue - 8), 0);
        createColumn(-(spreadValue - 8), 0);
        createColumn(0, (spreadValue - 8));
        createColumn(0, -(spreadValue - 8));

        createColumn(spreadValue, -spreadValue);
        createColumn(-spreadValue, spreadValue);
        createColumn(-spreadValue, spreadValue);
        createColumn(spreadValue, -spreadValue);
        createColumn(spreadValue, spreadValue);
        createColumn(-spreadValue, -spreadValue);
    }

    public void createColumn(int xOff, int zOff) {
        for (int sy = 1; sy <= 24; sy++) {
            BlockPos.Mutable newPos = getBlockPos().offset(xOff, sy - 4, zOff).mutable();
            if (sy == 6 || sy == 8) {
                getLevel().setBlock(newPos, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 2);
            } else if (sy == 7) {
                getLevel().setBlock(newPos,  Blocks.GLOWSTONE.defaultBlockState(), 2);
            } else {
                getLevel().setBlock(newPos, BPBlocks.ALPHANUM_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 2);
            }
        }
    }

    public void createPart(int rad, int yOffset, Block block) {
        for (int sx = -rad; sx <= rad; sx++) {
            for (int sz = -rad; sz <= rad; sz++) {
                if (sx * sx + sz * sz <= rad * rad) {
                    BlockPos.Mutable newPos = getBlockPos().offset(sx, yOffset - 4, sz).mutable();
                    getLevel().setBlock(newPos, block.defaultBlockState(), 2);
                }
            }
        }
    }
}
