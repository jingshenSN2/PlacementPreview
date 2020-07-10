package sn2.slabhelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import sn2.slabhelper.callbacks.CallbackRegistry;
import sn2.slabhelper.config.ConfigHandler;
import sn2.slabhelper.item.ItemRegistry;

public class SlabHelper implements ModInitializer {
	
	public static final String MODID = "slabhelper";
	public static final String VERSION = "1.1.1";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Override
	public void onInitialize() {
		new ConfigHandler(SlabHelperConfig.class, MODID);
		VersionCheck.check();
		ItemRegistry.init();
		CallbackRegistry.init();
		LOGGER.info("Slab Helper initialized");
	}
}
