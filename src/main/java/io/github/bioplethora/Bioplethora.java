package io.github.bioplethora;

import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import io.github.bioplethora.data.*;
import io.github.bioplethora.integration.BPCompatTOP;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.registry.*;
import io.github.bioplethora.registry.worldgen.*;
import io.github.bioplethora.world.BPBiomeGeneration;
import io.github.bioplethora.world.EntitySpawnManager;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Bioplethora.MOD_ID)
public class Bioplethora {

    public static Bioplethora instance;

    public static final String MOD_ID = "bioplethora";
    public static final String MOD_NAME = "Bioplethora";
    public static final Logger LOGGER = LogManager.getLogger();

    // TODO: 08/05/2022 Switch Item, Block, and Entity to Abnormal's core registry system for easier future registry. (after: Sounds)
    public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MOD_ID, helper -> {
        helper.putSubHelper(ForgeRegistries.ITEMS, new ItemSubRegistryHelper(helper));
    });

    public Bioplethora() {
        instance = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        /* final step of registering elements like Items, Entities, etc. */
        BPItems.ITEMS.register(bus);
        BPBlocks.BLOCKS.register(bus);
        BPBlocks.BLOCK_ITEMS.register(bus);
        BPEntities.ENTITIES.register(bus);

        BPFeatures.FEATURES.register(bus);
        BPSoundEvents.SOUNDS.register(bus);
        BPParticles.PARTICLES.register(bus);
        BPBiomes.BIOMES.register(bus);
        BPEffects.EFFECTS.register(bus);
        BPEnchantments.ENCHANTMENTS.register(bus);
        BPStructures.STRUCTURES.register(bus);
        BPAttributes.ATTRIBUTES.register(bus);
        BPBlockPlacers.BLOCK_PLACERS.register(bus);
        BPSurfaceBuilders.SURFACE_BUILDERS.register(bus);
        BPTileEntities.TILE_ENTITIES.register(bus);
        BPContainerTypes.CONTAINERS.register(bus);

        BPRecipes.registerRecipes(bus);

        bus.addListener(this::setup);
        bus.addListener(this::gatherData);
        bus.addListener(this::onInterModEnqueueEvent);

        bus.addListener(BPWoodTypes::registerWoodType);
        bus.addListener(BPWoodTypes::registerWoodTypeClient);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, EntitySpawnManager::onBiomeLoadingEvent);
        forgeBus.addListener(EventPriority.NORMAL, BPVillagerTrades::onVillagerTrades);
        forgeBus.addListener(EventPriority.NORMAL, BPAttributes::useTrueDefenseAttribute);
        forgeBus.register(this);

        GeckoLib.initialize();

        // register this class through the Minecraft Forge Event Bus
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BPConfig.COMMON_SPEC);
    }

    private void onInterModEnqueueEvent(final InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) BPCompatTOP.register();
        //if (ModList.get().isLoaded("jeresources")) BPCompatJER.register();
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up [" + MOD_NAME + "], thank you for using this mod!");

        BPLootConditions.registerConditions();

        BPNetwork.initializeNetwork();
        BPBiomeGeneration.generateBiomes();
        BPStructures.setupStructures();
        BPExtras.addExtras();
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        final ExistingFileHelper efh = event.getExistingFileHelper();

        if (event.includeServer()) {
            dataGenerator.addProvider(new BioBlockModelProvider(dataGenerator, MOD_ID, efh));
            dataGenerator.addProvider(new BioBlockstateProvider(dataGenerator, MOD_ID, efh));
            dataGenerator.addProvider(new BioItemModelProvider(dataGenerator, efh));
            dataGenerator.addProvider(new BioRecipeProvider(dataGenerator));
            dataGenerator.addProvider(new BioLootTablesProvider(dataGenerator));

            dataGenerator.addProvider(new BioBlockTagsProvider(dataGenerator, efh));
            dataGenerator.addProvider(new BioEntityTagsProvider(dataGenerator, efh));
            dataGenerator.addProvider(new BioItemTagsProvider(dataGenerator, new BioBlockTagsProvider(dataGenerator, efh), efh));

            dataGenerator.addProvider(new BioAdvancementProvider(dataGenerator, efh));

            dataGenerator.addProvider(new BioLanguageProvider(dataGenerator, MOD_ID, "en_us_test"));
        }
    }
}
