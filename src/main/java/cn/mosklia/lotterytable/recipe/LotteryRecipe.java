package cn.mosklia.lotterytable.recipe;

import cn.mosklia.lotterytable.LotteryTable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Random;

public class LotteryRecipe implements Recipe<Inventory> {
    public static final Identifier TYPE_ID = new Identifier(LotteryTable.MODID, "lottery_recipe");

    private final Ingredient input;
    private final OutputList output;
    private final Identifier id;
    private final Random random;

    public LotteryRecipe(Ingredient input, OutputList output, Identifier id) {
        this.input = input;
        this.output = output;
        this.id = id;
        this.random = new Random();
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 1) {
            return false;
        }
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return output.getByWeight(random.nextInt(1, output.getTotalWeight() + 1)).stack;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LotteryRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient getInput() {
        return input;
    }

    public static class Type implements RecipeType<LotteryRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final String ID = "lottery_recipe";
    }

    public static class LotteryRecipeJsonFormat {
        JsonObject input;
        JsonArray output;
    }

    public static class OutputType {
        public ItemStack stack;
        public int weight;

        public OutputType(Item item, int weight, int count) {
            this.stack = new ItemStack(item);
            this.stack.setCount(count);
            this.weight = weight;
        }

        public OutputType(Item item, int weight) {
            this.stack = new ItemStack(item);
            this.stack.setCount(1);
            this.weight = weight;
        }
    }

    public static class OutputList {
        private int totalWeight;
        private DefaultedList<OutputType> items;

        OutputList() {
            totalWeight = 0;
            items = DefaultedList.of();
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        public OutputType getByWeight(int weight) {
            for (var item : this.items) {
                weight -= item.weight;
                if (weight <= 0) {
                    return item;
                }
            }
            return null;
        }

        public void append(OutputType item) {
            totalWeight += item.weight;
            items.add(item);
        }
    }
}
