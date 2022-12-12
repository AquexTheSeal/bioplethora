package io.github.bioplethora.mixin;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = ChorusPlantBlock.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends SixWayBlock {

    public ChorusPlantBlockMixin(float p_i48355_1_, Properties pProperties) {
        super(p_i48355_1_, pProperties);
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    public void getStateForPlacement(BlockItemUseContext ctx, CallbackInfoReturnable<BlockState> info) {
        BlockPos pos = ctx.getClickedPos();
        World world = ctx.getLevel();
        BlockState plant = info.getReturnValue();
        if (ctx.canPlace() && plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
        }
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    private void getStateForPlacementX(IBlockReader world, BlockPos pos, CallbackInfoReturnable<BlockState> info)
    {
        BlockState plant = info.getReturnValue();
        if (plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
        }
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void canSurvive(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "updateShape", at = @At("RETURN"), cancellable = true)
    private void updateShape(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> info) {
        BlockState plant = info.getReturnValue();
        if (plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            plant = plant.setValue(BlockStateProperties.DOWN, true);
            info.setReturnValue(plant);

        }
    }
}
