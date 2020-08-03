package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import sn2.slabhelper.HalfMinePlayerEntity;
import sn2.slabhelper.SlabHelper;

public class CallbackRegistry {
	public static void init() {

		ServerSidePacketRegistry.INSTANCE.register(SlabHelper.HALFMINE, (packetContext, attachedData) -> {
			HalfMinePlayerEntity player = (HalfMinePlayerEntity) packetContext.getPlayer();
			player.setHalfMine();
			SlabHelper.LOGGER.info(packetContext.getPlayer().getEntityName() + " change Half-Mime mode to"
					+ (player.isHalfMine() ? "On" : "Off"));
		});

	}
}
