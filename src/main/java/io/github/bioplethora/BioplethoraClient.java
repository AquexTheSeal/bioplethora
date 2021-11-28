package io.github.bioplethora;

import io.github.bioplethora.client.entity.render.*;
import io.github.bioplethora.client.entity.render.projectile.BellophiteArrowRender;
import io.github.bioplethora.client.entity.render.projectile.BellophiteClusterRender;
import io.github.bioplethora.entity.PeaguinEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.item.ItemModelsProperties.register;

@Mod.EventBusSubscriber(bus =  Mod.EventBusSubscriber.Bus.MOD)
public class BioplethoraClient {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        //avifauna
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.PEAGUIN.get(), PeaguinEntityRender::new);

        //fairy
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALPHEM.get(), AlphemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHGOLEM.get(), BellophgolemEntityRender::new);

        //reptilia
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.NANDBRI.get(), NandbriEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), BellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_ARROW.get(), BellophiteArrowRender::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        register(BioplethoraItems.BELLOPHGOLEM_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
