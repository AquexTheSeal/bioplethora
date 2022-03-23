package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bioplethora.MOD_ID);

    public static final IRecipeType<ReinforcingRecipe> REINFORCING = new ReinforcingRecipe.ReinforcingRecipeType();
    //public static final IRecipeType<ReinforcingTableRecipe> REINFORCING = IRecipeType.register(Bioplethora.MOD_ID + "_reinforcing");

    public static final RegistryObject<IRecipeSerializer<?>> REINFORCING_SERIALIZER =
            RECIPE_SERIALIZERS.register("reinforcing", ReinforcingRecipe.Serializer::new);

    public static void registerRecipes(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, "reinforcing", REINFORCING);
    }
}
