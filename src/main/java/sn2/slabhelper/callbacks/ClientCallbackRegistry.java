package sn2.slabhelper.callbacks;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import sn2.slabhelper.ClientSlabHelper;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.SlabHelperKey;

public class ClientCallbackRegistry {
	@Environment(EnvType.CLIENT)
	public static void init() {
		// send half-mine mode change packet to server
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (SlabHelperKey.halfmine.wasPressed()) {
				ClientSlabHelper.setHalfMine();
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				ClientSidePacketRegistry.INSTANCE.sendToServer(SlabHelper.HALFMINE, passedData);
			}
		});

	}
}
