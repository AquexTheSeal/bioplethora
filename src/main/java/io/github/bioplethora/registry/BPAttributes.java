package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.events.BPHooks;
import io.github.bioplethora.api.world.EffectUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Bioplethora.MOD_ID);

    public static final RegistryObject<Attribute> TRUE_DEFENSE = ATTRIBUTES.register("bioplethora.true_defense", () -> new RangedAttribute("attribute.name.bioplethora.true_defense", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    @SubscribeEvent
    public static void useTrueDefenseAttribute(LivingHurtEvent event) {
        LivingEntity victim = (LivingEntity) event.getEntity();
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

        if (victim.getAttribute(TRUE_DEFENSE.get()) != null) {

            if (BPHooks.onTrueDefenseHurt(victim, event.getSource())) return;

            victim.playSound(BPSoundEvents.TRUE_DEFENSE_CLASH.get(), 1.0F, 0.5F + victim.getRandom().nextFloat());

            if (victim.level instanceof ServerWorld) {
                ((ServerWorld) victim.level).sendParticles(BPParticles.TRUE_DEFENSE_CLASH.get(),
                        victim.getX(), victim.getY() + 2, victim.getZ(), 1,
                        victim.getRandom().nextDouble() / 2.0, victim.getRandom().nextDouble() / 2.0, victim.getRandom().nextDouble() / 2.0, 0.02);

                EffectUtils.addCircleParticleForm(victim.level, victim, ParticleTypes.CRIT, 75, 0.55, 0.01);
            }

            event.setAmount(event.getAmount() - (float) victim.getAttributeValue(TRUE_DEFENSE.get()));
        }
    }
}
