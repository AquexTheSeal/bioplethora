package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.container.AbstractReinforcingContainer;
import io.github.bioplethora.container.ReinforcingTableContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Bioplethora.MOD_ID);

    public static final RegistryObject<ContainerType<AbstractReinforcingContainer>> REINFORCING_TABLE_CONTAINER
            = CONTAINERS.register("reinforcing", () -> IForgeContainerType.create((windowId, inv, data) ->
            new ReinforcingTableContainer(windowId, inv)));
}
