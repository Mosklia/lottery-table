package cn.mosklia.lotterytable.recipe;

import cn.mosklia.lotterytable.LotteryTable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LotteryRecipeSerializer implements RecipeSerializer<LotteryRecipe> {
    private LotteryRecipeSerializer() {
    }

    public static final LotteryRecipeSerializer INSTANCE = new LotteryRecipeSerializer();
    public static final Identifier ID = LotteryRecipe.TYPE_ID;

    @Override
    public LotteryRecipe read(Identifier id, JsonObject json) {
        LotteryRecipe.LotteryRecipeJsonFormat jsonFormat = new Gson().fromJson(json, LotteryRecipe.LotteryRecipeJsonFormat.class);
        Ingredient input = Ingredient.fromJson(jsonFormat.input);
        LotteryTable.LOGGER.info("Reading lottery recipe: " + id.toString());

        LotteryRecipe.OutputList outputList = new LotteryRecipe.OutputList();
        for (var item : jsonFormat.output) {
            JsonObject jsonObject = (JsonObject) item;
            Identifier itemID = new Identifier(jsonObject.get("item").getAsString());
            Item itemToAppend = Registry.ITEM.get(itemID);
            int weight = jsonObject.get("weight").getAsInt();
            int count = jsonObject.has("count") ? jsonObject.get("count").getAsInt() : 1;
            outputList.append(new LotteryRecipe.OutputType(itemToAppend, weight, count));
        }

        return new LotteryRecipe(input, outputList, id);
    }

    @Override
    public LotteryRecipe read(Identifier id, PacketByteBuf buf) {
        return null;
    }

    @Override
    public void write(PacketByteBuf buf, LotteryRecipe recipe) {
    }
}
