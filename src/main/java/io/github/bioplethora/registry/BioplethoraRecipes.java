package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.recipe.ReinforcingTableRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bioplethora.MOD_ID);

    public static final IRecipeType<ReinforcingTableRecipe> REINFORCING_TABLE_RECIPE = IRecipeType.register(Bioplethora.MOD_ID + "_reinforcing");

    public static final RegistryObject<IRecipeSerializer<?>> REINFORCING_TABLE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("reinforcing", ReinforcingTableRecipe.Serializer::new);
}
