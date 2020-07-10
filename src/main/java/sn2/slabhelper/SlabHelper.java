package sn2.slabhelper;

import net.fabricmc.api.ModInitializer;
import sn2.slabhelper.callbacks.CallbackRegistry;
import sn2.slabhelper.config.ConfigHandler;
import sn2.slabhelper.item.ItemRegistry;

public class SlabHelper implements ModInitializer {
	
	public static final String MODID = "slabhelper";
	public static final String VERSION = "1.0.0";
	
	@Override
	public void onInitialize() {
		new ConfigHandler(SlabHelperConfig.class, MODID);
		VersionCheck.check();
		ItemRegistry.init();
		CallbackRegistry.init();
		System.out.println("slab helper initialized");
	}
}
