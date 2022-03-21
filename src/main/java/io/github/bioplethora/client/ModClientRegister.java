package io.github.bioplethora.client;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.render.*;
import io.github.bioplethora.client.entity.render.others.AltyrusSummoningRender;
import io.github.bioplethora.client.entity.render.others.BellophiteShieldWaveRender;
import io.github.bioplethora.client.entity.render.others.GrylynenCoreBombRender;
import io.github.bioplethora.client.entity.render.others.PrimordialRingEntityRender;
import io.github.bioplethora.client.entity.render.projectile.*;
import io.github.bioplethora.gui.screen.ReinforcingTableScreen;
import io.github.bioplethora.item.weapons.ArbitraryBallistaItem;
import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import io.github.bioplethora.item.weapons.GrylynenShieldBaseItem;
import io.github.bioplethora.keybindings.BPKeybinds;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraContainerTypes;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientRegister {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        Minecraft mcClient = event.getMinecraftSupplier().get();

        RenderTypeLookup.setRenderLayer(BioplethoraBlocks.FLEIGNARITE_REMAINS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BioplethoraBlocks.FLEIGNARITE_VINES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BioplethoraBlocks.FLEIGNARITE_VINES_PLANT.get(), RenderType.cutout());

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);

        //Plethoneutral
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.PEAGUIN.get(), PeaguinEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.NANDBRI.get(), NandbriEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CAVERN_FLEIGNAR.get(), CavernFleignarEntityRender::new);

        //Dangerum
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALPHEM.get(), AlphemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.GAUGALEM.get(), GaugalemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.TRAPJAW.get(), TrapjawEntityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.WOODEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.STONE_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.GOLDEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.IRON_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.DIAMOND_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.NETHERITE_GRYLYNEN.get(), GrylynenEntityRender::new);

        //Hellsent
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHGOLEM.get(), BellophgolemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.HELIOBLADE.get(), HeliobladeEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.TELEMREYE.get(), TelemreyeEntityRender::new);

        //Elderia
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALTYRUS.get(), AltyrusEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.MYLIOTHAN.get(), MyliothanEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALPHEM_KING.get(), AlphemKingEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.MAGMA_BOMB.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), BellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_ARROW.get(), BellophiteArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateBellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.VERMILION_BLADE_PROJECTILE.get(), VermilionBladeProjectileRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.WIND_ARROW.get(), WindArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.CRYOBLAZE.get(), CryoblazeRender::new);

        //others
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.BELLOPHITE_SHIELD_WAVE.get(), BellophiteShieldWaveRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BioplethoraEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);

        ScreenManager.register(BioplethoraContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //Bellophite Shield
        ItemModelsProperties.register(BioplethoraItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BioplethoraItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientWorld, entity) -> entity != null && ((BellophiteShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

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

        //Grylynen Shields
        for (RegistryObject<Item> items : BioplethoraItems.ITEMS.getEntries()) {
            Item shields = items.get();
            if (shields instanceof GrylynenShieldBaseItem) {
                ItemModelsProperties.register(shields, new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
            }
        }
    }
}
