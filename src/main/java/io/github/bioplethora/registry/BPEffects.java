package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.potion.SpiritFissionEffect;
import io.github.bioplethora.potion.SpiritManipulationEffect;
import io.github.bioplethora.potion.SpiritStrengtheningEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Bioplethora.MOD_ID);

    public static final RegistryObject<Effect> SPIRIT_FISSION = EFFECTS.register("spirit_fission", () -> new SpiritFissionEffect(EffectType.BENEFICIAL, -3355393));
    public static final RegistryObject<Effect> SPIRIT_MANIPULATION = EFFECTS.register("spirit_manipulation", () -> new SpiritManipulationEffect(EffectType.BENEFICIAL, -3355393));
    public static final RegistryObject<Effect> SPIRIT_STRENGTHENING = EFFECTS.register("spirit_strengthening", () -> new SpiritStrengtheningEffect(EffectType.BENEFICIAL, -3355393));
}
