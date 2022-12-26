package io.github.bioplethora.client;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.blocks.BPLanternPlantBlock;
import io.github.bioplethora.client.entity.render.*;
import io.github.bioplethora.client.entity.render.others.*;
import io.github.bioplethora.client.entity.render.projectile.*;
import io.github.bioplethora.client.armor.render.AquChestplateRender;
import io.github.bioplethora.client.block.render.FleignariteSplotchBlockRender;
import io.github.bioplethora.entity.ai.client.entity.render.*;
import io.github.bioplethora.entity.ai.client.entity.render.others.*;
import io.github.bioplethora.entity.ai.client.entity.render.projectile.*;
import io.github.bioplethora.gui.screen.ReinforcingTableScreen;
import io.github.bioplethora.item.armor.AquChestplateItem;
import io.github.bioplethora.item.weapons.*;
import io.github.bioplethora.network.keybindings.BPKeybinds;
import io.github.bioplethora.registry.*;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BPClientRegister {

    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        Minecraft mcClient = event.getMinecraftSupplier().get();

        // Tile Entity
        ClientRegistry.bindTileEntityRenderer(BPTileEntities.FLEIGNARITE_SPLOTCH.get(), FleignariteSplotchBlockRender::new);
        ClientRegistry.bindTileEntityRenderer(BPTileEntities.BP_SIGN.get(), SignTileEntityRenderer::new);

        // Automatic Entry
        for (RegistryObject<Block> block : BPBlocks.BLOCKS.getEntries()) {
            if (
                    block.get() instanceof DoorBlock || block.get() instanceof TrapDoorBlock ||
                    block.get() instanceof LeavesBlock || block.get() instanceof AbstractSignBlock ||
                    block.get() instanceof SaplingBlock || block.get() instanceof BPLanternPlantBlock ||
                    block.get() instanceof BPIdeFanBlock || block.get() instanceof AbstractBodyPlantBlock ||
                    block.get() instanceof AbstractTopPlantBlock || block.get() instanceof BushBlock
            ) {
                RenderTypeLookup.setRenderLayer(block.get(), RenderType.cutout());
            }
        }

        RenderTypeLookup.setRenderLayer(BPBlocks.REINFORCING_TABLE.get(), RenderType.cutout());

        // Plants
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_REMAINS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_VINES_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_VINES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.FLEIGNARITE_SPLOTCH.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.KYRIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.KYRIA_BELINE.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_SPROUTS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.SOUL_TALL_GRASS.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.LAVA_SPIRE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.WARPED_DANCER.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.IRION_GRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BPBlocks.IRION_TALL_GRASS.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.AZURLIA.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.ARTAIRIUS.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(BPBlocks.FROSTEM.get(), RenderType.cutout());

        // Armor
        GeoArmorRenderer.registerArmorRenderer(AquChestplateItem.class, new AquChestplateRender());

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ONOFISH.get(), OnofishRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TRIGGERFISH.get(), TriggerfishRenderer::new);

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
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TERRAITH.get(), TerraithEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MYUTHINE.get(), MyuthineEntityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WOODEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.STONE_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GOLDEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.IRON_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.DIAMOND_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NETHERITE_GRYLYNEN.get(), GrylynenEntityRender::new);

        //Hellsent
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.FROSTBITE_GOLEM.get(), FrostbiteGolemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.SHACHATH.get(), ShachathEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TELEMREYE.get(), TelemreyeEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VOIDJAW.get(), VoidjawEntityRender::new);

        //Elderia
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS.get(), AltyrusEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MYLIOTHAN.get(), MyliothanEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHEM_KING.get(), AlphemKingEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MAGMA_BOMB.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDY_ESSENCE.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ABYSSAL_SCALES.get(), (rendererManager) -> new AbyssalScalesRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_CLUSTER.get(), FrostbiteMetalClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_ARROW.get(), FrostbiteMetalArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateFrostbiteMetalClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WIND_ARROW.get(), WindArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYOBLAZE.get(), CryoblazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), AlphanumObliteratorSpearRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VERMILION_BLADE_PROJECTILE.get(), (rendererManager) -> new VermilionBladeProjectileRender<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYEANUM_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ECHO_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));

        //others
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CAERULWOOD_BOAT.get(), BPBoatRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ENIVILE_BOAT.get(), BPBoatRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_SHIELD_WAVE.get(), FrostbiteMetalShieldWaveRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_SHARD.get(), AlphanumShardRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BP_EFFECT.get(), BPEffectRender::new);

        ScreenManager.register(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //FrostbiteMetal Shield
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientWorld, entity) -> entity != null && ((FrostbiteMetalShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

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
