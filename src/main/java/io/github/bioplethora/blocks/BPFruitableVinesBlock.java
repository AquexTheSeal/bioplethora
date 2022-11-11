package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.Random;

public abstract class BPFruitableVinesBlock extends BPVinesBlock {

    public BPFruitableVinesBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public abstract Block getFruitedBodyBlock();

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == this.growthDirection.getOpposite() && !pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.getBlockTicks().scheduleTick(pCurrentPos, this, 1);
        }
        AbstractTopPlantBlock abstracttopplantblock = this.getHeadBlock();
        if (pFacing == this.growthDirection) {
            Block block = pFacingState.getBlock();
            if (block != getBodyBlock() && block != getFruitedBodyBlock() && block != abstracttopplantblock) {
                return abstracttopplantblock.getStateForPlacement(pLevel);
            }
        }
        if (this.scheduleFluidTicks) {
            pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return pState;
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlock(pos, getFruitedBodyBlock().defaultBlockState(), 2);
        super.performBonemeal(world, random, pos, state);
    }

    @Override
    public Optional<BlockPos> getHeadPos(IBlockReader p_235501_1_, BlockPos p_235501_2_, BlockState p_235501_3_) {
        BlockPos blockpos = p_235501_2_;

        Block block;
        do {
            blockpos = blockpos.relative(this.growthDirection);
            block = p_235501_1_.getBlockState(blockpos).getBlock();
        } while(block == p_235501_3_.getBlock() || block == getFruitedBodyBlock().getBlock());

        return block == this.getHeadBlock() ? Optional.of(blockpos) : Optional.empty();
    }

    public static class BasaltSpeleothermBlock extends BPFruitableVinesBlock {

        public BasaltSpeleothermBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }
    }
    public static class FieryBasaltSpeleothermBlock extends BPFruitableVinesBlock {

        public FieryBasaltSpeleothermBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }

        @Override
        public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {

            boolean flag = false;
            if (pPlayer.getItemInHand(pHand).getItem() == Items.GLASS_BOTTLE) {

                pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
                pPlayer.getItemInHand(pHand).shrink(1);
                pLevel.playSound(null, pPos, SoundEvents.BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                if (pPlayer.getItemInHand(pHand).isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()));

                } else if (!pPlayer.inventory.add(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()))) {
                    pPlayer.drop(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()), false);
                }

                flag = true;
            }
            if (flag) {
                return ActionResultType.sidedSuccess(pLevel.isClientSide);

            } else {
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }
    }

    public static class ThontusThistleBlock extends BPFruitableVinesBlock {

        public ThontusThistleBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.THONTUS_THISTLE.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity entity) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, entity);
            entity.hurt(DamageSource.CACTUS, 1);
        }
    }
    public static class BerriedThontusThistleBlock extends BPFruitableVinesBlock {

        public BerriedThontusThistleBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.THONTUS_THISTLE.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.THONTUS_THISTLE_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity entity) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, entity);
            entity.hurt(DamageSource.CACTUS, 1);
        }

        @Override
        public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
            int i = pLevel.random.nextBoolean() ? 2 : 3;
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.THONTUS_BERRIES.get(), i));
            return ActionResultType.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class TurquoisePendentBlock extends BPFruitableVinesBlock {

        public TurquoisePendentBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.TURQUOISE_PENDENT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }
    }
    public static class BlossomingTurquoisePendentBlock extends BPFruitableVinesBlock {

        public BlossomingTurquoisePendentBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.TURQUOISE_PENDENT.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.TURQUOISE_PENDENT_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }

        @Override
        public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
            int i = 2 + pLevel.random.nextInt(2);
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.WARPED_GRAPES.get(), i));
            return ActionResultType.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class CeriseIvyBlock extends BPFruitableVinesBlock {

        public CeriseIvyBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.CERISE_IVY.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }
    }
    public static class SeededCeriseIvyBlock extends BPFruitableVinesBlock {

        public SeededCeriseIvyBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.CERISE_IVY.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.CERISE_IVY_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }

        @Override
        public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
            int i = 3 + pLevel.random.nextInt(5);
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.CRIMSON_BITTERSWEET_SEEDS.get(), i));
            return ActionResultType.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class SoulEternBlock extends BPFruitableVinesBlock {

        public SoulEternBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.SOUL_ETERN.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }
    }
    public static class FlourishedSoulEternBlock extends BPFruitableVinesBlock {

        public FlourishedSoulEternBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.SOUL_ETERN.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SOUL_ETERN_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }

        @Override
        public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {

            boolean flag = false;
            if (pPlayer.getItemInHand(pHand).getItem() == Items.GLASS_BOTTLE) {

                pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
                pPlayer.getItemInHand(pHand).shrink(1);
                pLevel.playSound(null, pPos, SoundEvents.BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                if (pPlayer.getItemInHand(pHand).isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()));

                } else if (!pPlayer.inventory.add(new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()))) {
                    pPlayer.drop(new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()), false);
                }

                flag = true;
            }
            if (flag) {
                return ActionResultType.sidedSuccess(pLevel.isClientSide);

            } else {
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }
    }
}
