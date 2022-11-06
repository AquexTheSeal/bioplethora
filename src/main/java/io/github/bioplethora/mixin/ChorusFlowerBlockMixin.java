package io.github.bioplethora.mixin;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin {

    @Shadow @Final private ChorusPlantBlock plant;

    @Shadow protected abstract void placeGrownFlower(World pLevel, BlockPos pPos, int pAge);

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom, CallbackInfo ci) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (BPTags.Blocks.CHORUS_GROWABLE.contains(blockstate.getBlock())) {
            BlockPos abovePos = pPos.above();
            if (pLevel.isEmptyBlock(abovePos) && abovePos.getY() < 256) {
                int i = pState.getValue(ChorusFlowerBlock.AGE);
                if (i < 5) {
                    this.placeGrownFlower(pLevel, abovePos, i + 1);
                    pLevel.setBlock(pPos, plant.defaultBlockState().setValue(ChorusPlantBlock.UP, true).setValue(ChorusPlantBlock.DOWN, true), 1|16|2);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (BPTags.Blocks.CHORUS_GROWABLE.contains(blockstate.getBlock())) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
