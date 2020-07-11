package sn2.slabhelper.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.SlabHelperConfig;

public class ItemRegistry {
	
	public static final Item SLAB_HOOKER = new ItemSlabHooker(
			new Item.Settings().maxCount(1).maxDamageIfAbsent(SlabHelperConfig.hookerDurability).group(ItemGroup.TOOLS));
	
	public static void init() {
		if (SlabHelperConfig.enableHooker)
			Registry.register(Registry.ITEM, new Identifier(SlabHelper.MODID, "slab_hooker"), SLAB_HOOKER);
	}
}
