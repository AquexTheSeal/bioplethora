package io.github.bioplethora.client;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.block.render.FleignariteSplotchBlockRender;
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
import io.github.bioplethora.registry.*;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientRegister {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        Minecraft mcClient = event.getMinecraftSupplier().get();

        // Block Render Changer
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_REMAINS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_VINES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_VINES_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_SPLOTCH.get(), RenderType.cutout());

        // Tile Entity
        ClientRegistry.bindTileEntityRenderer(BPTileEntities.FLEIGNARITE_SPLOTCH.get(), FleignariteSplotchBlockRender::new);

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);

        //Plethoneutral
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PEAGUIN.get(), PeaguinEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NANDBRI.get(), NandbriEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CAVERN_FLEIGNAR.get(), CavernFleignarEntityRender::new);

        //Dangerum
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHEM.get(), AlphemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GAUGALEM.get(), GaugalemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TRAPJAW.get(), TrapjawEntityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WOODEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.STONE_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GOLDEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.IRON_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.DIAMOND_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NETHERITE_GRYLYNEN.get(), GrylynenEntityRender::new);

        //Hellsent
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHGOLEM.get(), BellophgolemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.HELIOBLADE.get(), HeliobladeEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TELEMREYE.get(), TelemreyeEntityRender::new);

        //Elderia
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS.get(), AltyrusEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MYLIOTHAN.get(), MyliothanEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHEM_KING.get(), AlphemKingEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MAGMA_BOMB.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_CLUSTER.get(), BellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_ARROW.get(), BellophiteArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateBellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VERMILION_BLADE_PROJECTILE.get(), VermilionBladeProjectileRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WIND_ARROW.get(), WindArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYOBLAZE.get(), CryoblazeRender::new);

        //others
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_SHIELD_WAVE.get(), BellophiteShieldWaveRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);

        ScreenManager.register(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //Bellophite Shield
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientWorld, entity) -> entity != null && ((BellophiteShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

        //Arbitrary Ballista
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return ArbitraryBallistaItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float) ArbitraryBallistaItem.getChargeDuration(itemStack);
            }
        });
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("charged"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("firework"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) && ArbitraryBallistaItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        //Grylynen Shields
        for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
            Item shields = items.get();
            if (shields instanceof GrylynenShieldBaseItem) {
                ItemModelsProperties.register(shields, new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
            }
        }
    }
}
