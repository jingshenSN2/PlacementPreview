package sn2.slabhelper.config;

public class SlabHelperConfig {
	@Config(category = "item", key = "Slab Hooker", comment = "Disable this if you dont want slab hooker. (wont disable the additional lines on the double slab)")
	public static boolean enableHooker = true;

	@Config(category = "function", key = "Slab Hooker In Creative", comment = "Enable this if you want to use slab hooker only in survival mode.")
	public static boolean useHookerOnlySurvivalMode = false;

	@Config(category = "function", key = "Enable Half-Mining", comment = "Enable this if you want that tools can half-mine the full slab.")
	public static boolean halfmining = true;

	@Config(category = "render", key = "Additional Line for Double Slab", comment = "Disable this if you dont want additional lines on the double slab.")
	public static boolean renderAdditionalEdges = true;
}
