package sn2.slabhelper.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.config.SlabHelperConfig;

public class ItemRegistry {
	
	public static final Item WOODEN_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.WOOD, 0, false);
	public static final Item STONE_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.STONE, 0, false);
	public static final Item IRON_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.IRON, 1, false);
	public static final Item GOLD_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.IRON, 1, true);
	public static final Item DIAMOND_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.DIAMOND, 2, false);
	public static final Item NETHERITE_SLAB_HOOKER = new ItemSlabHooker(ToolMaterials.NETHERITE, 3, true);
	
	public static void init() {
		if (SlabHelperConfig.enableHooker) {
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "wooden_slab_hooker"), WOODEN_SLAB_HOOKER);
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "stone_slab_hooker"), STONE_SLAB_HOOKER);
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "iron_slab_hooker"), IRON_SLAB_HOOKER);
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "golden_slab_hooker"), GOLD_SLAB_HOOKER);
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "diamond_slab_hooker"), DIAMOND_SLAB_HOOKER);
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "netherite_slab_hooker"), NETHERITE_SLAB_HOOKER);
		}
	}
}
