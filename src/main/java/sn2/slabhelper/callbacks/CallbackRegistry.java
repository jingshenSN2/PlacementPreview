package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import sn2.slabhelper.SlabHelper;

public class CallbackRegistry {
	public static void init() {
		
		ServerSidePacketRegistry.INSTANCE.register(SlabHelper.HALFMINE,  (packetContext, attachedData) -> {
			boolean enable = SlabHelper.getHalfMineStatus(packetContext.getPlayer());
			if (enable == false) {
				SlabHelper.setHalfMineStatus(packetContext.getPlayer(), true);
				SlabHelper.LOGGER.info(packetContext.getPlayer().getDisplayName() + "enable Half-Mime mode");
			}
			else {
				SlabHelper.setHalfMineStatus(packetContext.getPlayer(), false);
				SlabHelper.LOGGER.info(packetContext.getPlayer().getDisplayName() + "disable Half-Mime mode");
			}
		});
		
	}
}
