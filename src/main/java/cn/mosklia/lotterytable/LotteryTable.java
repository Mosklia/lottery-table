package cn.mosklia.lotterytable;

import cn.mosklia.lotterytable.block.Blocks;
import cn.mosklia.lotterytable.recipe.RecipeTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotteryTable implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "lottery_table";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Blocks.registerAll();
		RecipeTypes.registerAll();

		LOGGER.info("Hello Fabric world!");
	}
}
