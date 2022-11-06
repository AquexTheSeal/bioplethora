package io.github.bioplethora.mixin;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
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

@Mixin(ChorusPlantFeature.class)
public abstract class ChorusPlantFeatureMixin {

    @Inject(method = "place*", at = @At("HEAD"), cancellable = true)
    private void place(ISeedReader worldIn, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig config, CallbackInfoReturnable<Boolean> info) {
        if (worldIn.isEmptyBlock(blockPos) && worldIn.getBlockState(blockPos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            ChorusFlowerBlock.generatePlant(worldIn, blockPos, random, 8);
            BlockState bottom = worldIn.getBlockState(blockPos);
            if (bottom.is(Blocks.CHORUS_PLANT)) {
                worldIn.setBlock(blockPos, bottom.setValue(SixWayBlock.DOWN, true), 1|16|2);
            }
            info.setReturnValue(true);
        }
    }
}
