package cn.mosklia.lotterytable.block.entity;

import cn.mosklia.lotterytable.LotteryTable;
import cn.mosklia.lotterytable.api.ImplementedInventory;
import cn.mosklia.lotterytable.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.hardware.platform.linux.LinuxHWDiskStore;

import java.util.List;

public class CraftingTableEntity extends BlockEntity implements SidedInventory, ImplementedInventory {
    public static final BlockEntityType<CraftingTableEntity> TYPE = FabricBlockEntityTypeBuilder.create(CraftingTableEntity::new, net.minecraft.block.Blocks.CRAFTING_TABLE).build();
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public CraftingTableEntity(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public ItemStack getInputStack() {
        return getItems().get(0);
    }

    public ItemStack getOutputStack() {
        return getItems().get(1);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        super.writeNbt(nbt);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        List<Item> acceptableItems = List.of(Items.COPPER_INGOT, Items.ROTTEN_FLESH, Items.ENDER_PEARL);
//        LotteryTable.LOGGER.debug(stack.toString());
        return slot == 0 && getInputStack().getCount() == 0 && acceptableItems.contains(stack.getItem());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    private void generateLoot() {
        this.getInputStack().decrement(1);
        this.getItems().set(1, new ItemStack(Items.APPLE));
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0, 1};
    }

    public static void tick(World world, BlockPos pos, BlockState state, CraftingTableEntity entity) {
        if (entity.getInputStack().getCount() > 0 && entity.getOutputStack().getCount() == 0) {
            entity.generateLoot();
        }
    }
}
