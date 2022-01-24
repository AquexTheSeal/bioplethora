package io.github.bioplethora;

import io.github.bioplethora.datagen.BioBlockModelProvider;
import io.github.bioplethora.datagen.BioBlockstateProvider;
import io.github.bioplethora.registry.*;
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
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Bioplethora.MOD_ID)
public class Bioplethora {
    public static Bioplethora instance;

    public static final String MOD_ID = "bioplethora";
    public static final String MOD_NAME = "Bioplethora";
    private static final Logger LOGGER = LogManager.getLogger();

    public Bioplethora() {
        instance = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        /* final step of registering elements like Items, Entities, etc. */
        BioplethoraItems.ITEMS.register(bus);
        BioplethoraEntities.ENTITIES.register(bus);
        BioplethoraBlocks.BLOCKS.register(bus);
        BioplethoraSoundEvents.SOUNDS.register(bus);
        BioplethoraEnchantments.ENCHANTMENTS.register(bus);

        ModList modList = ModList.get();
        //if (modList.isLoaded("jeresources")) BioplethoraJER.init();

        bus.addListener(this::setup);
        bus.addListener(this::gatherData);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, EntitySpawnManager::onBiomeLoadingEvent);
        forgeBus.register(this);

        GeckoLib.initialize();

        // register this class through the Minecraft Forge Event Bus
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BioplethoraConfig.COMMON_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up [" + MOD_NAME + "], thank you for using this mod!");
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        final ExistingFileHelper efh = event.getExistingFileHelper();

        if (event.includeServer()) {
            dataGenerator.addProvider(new BioBlockModelProvider(dataGenerator, MOD_ID, efh));
            dataGenerator.addProvider(new BioBlockstateProvider(dataGenerator, MOD_ID, efh));
        }
    }
}

