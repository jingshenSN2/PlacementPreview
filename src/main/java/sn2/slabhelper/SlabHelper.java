package sn2.slabhelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import sn2.slabhelper.callbacks.CallbackRegistry;
import sn2.slabhelper.config.ConfigHandler;
import sn2.slabhelper.config.SlabHelperConfig;
import sn2.slabhelper.item.ItemRegistry;

public class SlabHelper implements ModInitializer {

	public static final String MODID = "slabhelper";
	public static final String VERSION = "1.3.2";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static Identifier HALFMINE = new Identifier(SlabHelper.MODID, "half");

	@Override
	public void onInitialize() {
		new ConfigHandler(SlabHelperConfig.class, MODID);
		// check update
		VersionCheck.init();
		// register item
		ItemRegistry.init();
		// register events
		CallbackRegistry.init();
		LOGGER.info("Slab Helper initialized");
	}
}
