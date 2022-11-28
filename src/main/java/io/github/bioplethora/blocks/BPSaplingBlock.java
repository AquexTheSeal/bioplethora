package io.github.bioplethora.blocks;

import io.github.bioplethora.blocks.api.world.WorldgenUtils;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BPSaplingBlock extends SaplingBlock {

    public BioPlantType type;
    public WorldgenUtils.NBTTree nbtTree;

    public BPSaplingBlock(BioPlantType type, WorldgenUtils.NBTTree nbtTree, Properties properties) {
        super(null, properties);
        this.type = type;
        this.nbtTree = nbtTree;
    }

    @Override
    public void advanceTree(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
            this.nbtTree.placeAt(world, world.getChunkSource().getGenerator(), pos, state, rand);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return type.getWhitelist().get().contains(state.getBlock());
    }
}
