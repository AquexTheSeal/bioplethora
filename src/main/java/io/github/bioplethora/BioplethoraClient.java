package io.github.bioplethora;

import io.github.bioplethora.client.entity.render.AlphemEntityRender;
import io.github.bioplethora.client.entity.render.BellophgolemEntityRender;
import io.github.bioplethora.client.entity.render.CrephoxlEntityRender;
import io.github.bioplethora.client.entity.render.NandbriEntityRender;
import io.github.bioplethora.client.entity.render.projectile.BellophiteClusterRender;
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
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CREPHOXL.get(), manager -> new CrephoxlEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALPHEM.get(), manager -> new AlphemEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.NANDBRI.get(), manager -> new NandbriEntityRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHGOLEM.get(), manager -> new BellophgolemEntityRender(manager));

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), manager -> new BellophiteClusterRender(manager));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        register(BioplethoraItems.BELLOPHGOLEM_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
