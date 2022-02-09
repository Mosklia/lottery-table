package cn.mosklia.lotterytable.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public class RecipeTypes {

    public static void registerAll() {
        RecipeType.register(LotteryRecipe.Type.ID);
        Registry.register(Registry.RECIPE_SERIALIZER, LotteryRecipeSerializer.ID, LotteryRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, LotteryRecipe.TYPE_ID, LotteryRecipe.Type.INSTANCE);
    }
}
