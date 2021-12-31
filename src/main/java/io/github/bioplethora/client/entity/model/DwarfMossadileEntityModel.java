package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.DwarfMossadileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DwarfMossadileEntityModel extends AnimatedGeoModel<DwarfMossadileEntity> {

    @Override
    public ResourceLocation getModelLocation(DwarfMossadileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/dwarf_mossadile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DwarfMossadileEntity entity) {
        if (entity.isNetherVariant) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/dwarf_mossadile_nether.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/dwarf_mossadile.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DwarfMossadileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/dwarf_mossadile.animation.json");
    }

    /*@Override
    public void setLivingAnimations(DwarfMossadileEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head2");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }*/
}
