package io.github.bioplethora.recipe;

import com.google.gson.JsonObject;
import io.github.bioplethora.registry.BioplethoraRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ReinforcingRecipeBuilder {

    private final Ingredient topIngredient, midIngredient, botIngredient;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final IRecipeSerializer<?> type;

    public ReinforcingRecipeBuilder(IRecipeSerializer<?> pType, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult) {
        this.type = pType;
        this.topIngredient = topIngredient;
        this.midIngredient = midIngredient;
        this.botIngredient = botIngredient;
        this.result = pResult;
    }

    public static ReinforcingRecipeBuilder reinforcing(Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult) {
        return new ReinforcingRecipeBuilder(BioplethoraRecipes.REINFORCING_SERIALIZER.get(), topIngredient, midIngredient, botIngredient, pResult);
    }

    public ReinforcingRecipeBuilder unlocks(String pName, ICriterionInstance pCriterion) {
        this.advancement.addCriterion(pName, pCriterion);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, String pId) {
        this.save(pFinishedRecipeConsumer, new ResourceLocation(pId));
    }

    public void save(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pId) {
        this.ensureValid(pId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId)).rewards(AdvancementRewards.Builder.recipe(pId)).requirements(IRequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new ReinforcingRecipeBuilder.Result(pId, this.type, this.topIngredient, this.midIngredient, this.botIngredient, this.result, this.advancement, new ResourceLocation(pId.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + pId.getPath())));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient topIngredient, midIngredient, botIngredient;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<?> type;

        public Result(ResourceLocation pId, IRecipeSerializer<?> pType, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.type = pType;
            this.topIngredient = topIngredient;
            this.midIngredient = midIngredient;
            this.botIngredient = botIngredient;
            this.result = pResult;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("topIngredient", this.topIngredient.toJson());
            pJson.add("midIngredient", this.midIngredient.toJson());
            pJson.add("botIngredient", this.botIngredient.toJson());
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            pJson.add("result", jsonobject);
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        public IRecipeSerializer<?> getType() {
            return this.type;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
