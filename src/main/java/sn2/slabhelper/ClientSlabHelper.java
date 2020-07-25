package sn2.slabhelper;

import java.util.HashMap;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import sn2.slabhelper.callbacks.ClientCallbackRegistry;

public class ClientSlabHelper implements ClientModInitializer {

	private static HashMap<BlockPos, SlabType> damageRender = new HashMap<>();
	private static boolean halfMine = false;

	@Override
	public void onInitializeClient() {
		// register key binding
		SlabHelperKey.init();
		ClientCallbackRegistry.init();
	}

	public static boolean isHalfMine() {
		return halfMine;
	}

	public static void setHalfMine() {
		halfMine = !halfMine;
	}

	public static SlabType getDamageRender(BlockPos pos) {
		return damageRender.get(pos);
	}

	public static void setDamageRender(BlockPos pos, SlabType type) {
		damageRender.clear();
		damageRender.put(pos, type);
	}

}
