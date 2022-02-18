package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.potion.SpiritFissionEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Bioplethora.MOD_ID);

    public static final RegistryObject<Effect> SPIRIT_FISSION = EFFECTS.register("spirit_fission", () -> new SpiritFissionEffect(EffectType.BENEFICIAL, -3355393));
}
