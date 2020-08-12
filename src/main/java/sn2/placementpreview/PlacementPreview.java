package sn2.placementpreview;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class PlacementPreview implements ModInitializer {

	public static final String MODID = "placementpreview";
	public static final String VERSION = "0.1.0";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static Identifier PREVIEW = new Identifier(PlacementPreview.MODID, "PlacementPreview");

	@Override
	public void onInitialize() {
		// check update
		VersionCheck.check();
		LOGGER.info("Slab Helper initialized");
	}
}
