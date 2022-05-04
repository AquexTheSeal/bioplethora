package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.BPBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;

public class BPBoatRender extends BoatRenderer {

    public BPBoatRender(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity) {
        BPBoatEntity bpBoat = ((BPBoatEntity) entity);
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/boat/" + bpBoat.getWoodType() + ".png");
    }
}
