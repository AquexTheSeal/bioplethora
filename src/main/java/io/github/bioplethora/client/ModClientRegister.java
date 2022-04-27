package io.github.bioplethora.client;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.armor.render.AquChestplateRender;
import io.github.bioplethora.client.block.render.FleignariteSplotchBlockRender;
import io.github.bioplethora.client.entity.render.*;
import io.github.bioplethora.client.entity.render.others.*;
import io.github.bioplethora.client.entity.render.projectile.*;
import io.github.bioplethora.gui.screen.ReinforcingTableScreen;
import io.github.bioplethora.item.armor.AquChestplateItem;
import io.github.bioplethora.item.weapons.*;
import io.github.bioplethora.network.keybindings.BPKeybinds;
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
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SeparatePerspectiveModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

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

        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_SPROUTS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_TALL_GRASS.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.LAVA_SPIRE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.WARPED_DANCER.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.SPIRIT_DANGLER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.SPIRIT_DANGLER_PLANT.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.BASALT_SPELEOTHERM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.THONTUS_THISTLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.THONTUS_THISTLE_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.BERRIED_THONTUS_THISTLE.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.TURQUOISE_PENDENT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.TURQUOISE_PENDENT_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.CERISE_IVY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.CERISE_IVY_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.SEEDED_CERISE_IVY.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_ETERN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_ETERN_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLOURISHED_SOUL_ETERN.get(), RenderType.cutout());

        // Armor
        GeoArmorRenderer.registerArmorRenderer(AquChestplateItem.class, new AquChestplateRender());

        // Tile Entity
        ClientRegistry.bindTileEntityRenderer(BPTileEntities.FLEIGNARITE_SPLOTCH.get(), FleignariteSplotchBlockRender::new);

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.SOUL_EURYDN.get(), EurydnEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.FIERY_EURYDN.get(), EurydnEntityRender::new);

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

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TERRAITH.get(), TerraithEntityRender::new);

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
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDY_ESSENCE.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_CLUSTER.get(), BellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_ARROW.get(), BellophiteArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateBellophiteClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VERMILION_BLADE_PROJECTILE.get(), VermilionBladeProjectileRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WIND_ARROW.get(), WindArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYOBLAZE.get(), CryoblazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), AlphanumObliteratorSpearRender::new);

        //others
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_SHIELD_WAVE.get(), BellophiteShieldWaveRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_SHARD.get(), AlphanumShardRender::new);

        ScreenManager.register(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(Bioplethora.MOD_ID, "separate_perspective"), SeparatePerspectiveModel.Loader.INSTANCE);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //Bellophite Shield
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientWorld, entity) -> entity != null && ((BellophiteShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

        //Infernal Quarterstaff
        ItemModelsProperties.register(BPItems.INFERNAL_QUARTERSTAFF.get(), new ResourceLocation("reverse"), (itemStack, clientWorld, entity) -> entity != null && InfernalQuarterstaffItem.isReversed(itemStack) ? 1.0F : 0.0F);

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

        //Alphanum Obliterator
        ItemModelsProperties.register(BPItems.ALPHANUM_OBLITERATOR.get(), new ResourceLocation("charged"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && AlphanumObliteratorItem.isCharged(itemStack) ? 1.0F : 0.0F);

        //Grylynen Shields
        for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
            Item shields = items.get();
            if (shields instanceof GrylynenShieldBaseItem) {
                ItemModelsProperties.register(shields, new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
            }
        }
    }
}
