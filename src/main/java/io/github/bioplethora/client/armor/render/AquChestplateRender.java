package io.github.bioplethora.client.armor.render;

import io.github.bioplethora.client.armor.model.AquChestplateModel;
import io.github.bioplethora.item.armor.AquChestplateItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AquChestplateRender extends GeoArmorRenderer<AquChestplateItem> {

    public AquChestplateRender() {
        super(new AquChestplateModel());

        this.headBone = "dummybone";

        this.bodyBone = "body";
        this.rightArmBone = "right_arm";
        this.leftArmBone = "left_arm";

        this.rightLegBone = "dummybone";
        this.leftLegBone = "dummybone";
        this.rightBootBone = "dummybone";
        this.leftBootBone = "dummybone";
    }
}
