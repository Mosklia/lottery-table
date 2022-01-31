package cn.mosklia.lotterytable.block;

import cn.mosklia.lotterytable.LotteryTable;
import cn.mosklia.lotterytable.block.entity.CraftingTableEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
    public static BlockEntityType<CraftingTableEntity> CRAFTING_TABLE_ENTITY;

    public static void registerAll() {
        CRAFTING_TABLE_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(LotteryTable.MODID, "crafting_table_entity"),
                CraftingTableEntity.TYPE);
    }
}
