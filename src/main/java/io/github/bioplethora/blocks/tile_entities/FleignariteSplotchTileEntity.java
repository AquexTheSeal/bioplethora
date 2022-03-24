package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class FleignariteSplotchTileEntity extends TileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public FleignariteSplotchTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public FleignariteSplotchTileEntity() {
        this(BPTileEntities.FLEIGNARITE_SPLOTCH.get());
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fleignarite_splotch.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "fleignarite_splotch_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
