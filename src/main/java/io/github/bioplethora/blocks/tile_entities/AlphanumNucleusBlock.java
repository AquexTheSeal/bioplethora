package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.registry.BPBlockstateProperties;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class AlphanumNucleusBlock extends RotatedPillarBlock {
    public static final BooleanProperty ACTIVATED = BPBlockstateProperties.NUCLEUS_ACTIVATED;

    public AlphanumNucleusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVATED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AlphanumNucleusTileEntity();
    }
}
