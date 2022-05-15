package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BPWoodTypes {
    public static final WoodType ENIVILE = WoodType.create(new ResourceLocation(Bioplethora.MOD_ID, "enivile").toString());
    public static final WoodType CAERULWOOD = WoodType.create(new ResourceLocation(Bioplethora.MOD_ID, "caerulwood").toString());
    public static final WoodType PETRAWOOD = WoodType.create(new ResourceLocation(Bioplethora.MOD_ID, "petrawood").toString());

    public static void registerWoodType(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WoodType.register(ENIVILE);
            WoodType.register(CAERULWOOD);
            WoodType.register(PETRAWOOD);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerWoodTypeClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Atlases.addWoodType(ENIVILE);
            Atlases.addWoodType(CAERULWOOD);
            Atlases.addWoodType(PETRAWOOD);
        });
    }
}
