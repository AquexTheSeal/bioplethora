package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.world.BPFeatureGeneration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.BlockWithContextFeature;

import java.util.Random;

public class FleignaritePatchFeature extends BlockWithContextFeature {

    public FleignaritePatchFeature(Codec<BlockWithContextConfig> codec) {
        super(codec);
    }

    public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, BlockWithContextConfig config) {
        if (config.placeOn.contains(reader.getBlockState(pos.below())) && config.placeIn.contains(reader.getBlockState(pos)) && config.placeUnder.contains(reader.getBlockState(pos.above()))) {
            if (BPFeatureGeneration.isFleignariteChunk(pos, reader)) {
                if (Math.random() <= 0.15 && BioplethoraConfig.COMMON.spawnCavernFleignar.get()) {
                    if (reader.getBlockState(pos.above()).isAir() && reader.getBlockState(pos.above(2)).isAir() && reader.getBlockState(pos.above(3)).isAir()) {
                        CavernFleignarEntity fleignar = BioplethoraEntities.CAVERN_FLEIGNAR.get().create(reader.getLevel());
                        fleignar.moveTo(pos, 0.0F, 0.0F);
                        reader.addFreshEntity(fleignar);
                    }
                }
                reader.setBlock(pos, config.toPlace, 2);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
