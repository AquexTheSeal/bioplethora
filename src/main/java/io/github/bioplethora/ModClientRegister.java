package io.github.bioplethora;

import io.github.bioplethora.client.entity.render.*;
import io.github.bioplethora.client.entity.render.others.AltyrusSummoningRender;
import io.github.bioplethora.client.entity.render.others.BellophiteShieldWaveRender;
import io.github.bioplethora.client.entity.render.projectile.BellophiteArrowRender;
import io.github.bioplethora.client.entity.render.projectile.BellophiteClusterRender;
import io.github.bioplethora.client.entity.render.projectile.UltimateBellophiteClusterRender;
import io.github.bioplethora.client.entity.render.projectile.WindBlazeRender;
import io.github.bioplethora.item.weapons.ArbitraryBallistaItem;
import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus =  Mod.EventBusSubscriber.Bus.MOD)
public class ModClientRegister {

    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        Minecraft mcClient = event.getMinecraftSupplier().get();

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);

        //Plethoneutral
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.PEAGUIN.get(), PeaguinEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.NANDBRI.get(), NandbriEntityRender::new);

        //Dangerum
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALPHEM.get(), AlphemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.GAUGALEM.get(), GaugalemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntityRender::new);

        //Hellsent
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHGOLEM.get(), BellophgolemEntityRender::new);

        //Elderia
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALTYRUS.get(), AltyrusEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.MAGMA_BOMB.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), BellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_ARROW.get(), BellophiteArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateBellophiteClusterRender::new);

        //others
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_SHIELD_WAVE.get(), BellophiteShieldWaveRender::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //Bellophite Shield
        ItemModelsProperties.register(BioplethoraItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BioplethoraItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientWorld, entity) -> entity != null && ((BellophiteShieldItem) itemStack.getItem()).getIsCharged() ? 1.0F : 0.0F);

        //Arbitrary Ballista
        ItemModelsProperties.register(BioplethoraItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return ArbitraryBallistaItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float) ArbitraryBallistaItem.getChargeDuration(itemStack);
            }
        });
        ItemModelsProperties.register(BioplethoraItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BioplethoraItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("charged"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BioplethoraItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("firework"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) && ArbitraryBallistaItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
    }
}
