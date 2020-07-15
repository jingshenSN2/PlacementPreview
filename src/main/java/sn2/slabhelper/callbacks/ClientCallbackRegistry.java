package sn2.slabhelper.callbacks;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.SlabHelperKey;

public class ClientCallbackRegistry {
	public static void init() {
		// send half-mine mode change packet to server
		ClientTickCallback.EVENT.register(client -> {
		    if (SlabHelperKey.halfmine.wasPressed()) {
		    	PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		    	ClientSidePacketRegistry.INSTANCE.sendToServer(SlabHelper.HALFMINE, passedData);
		    }
		});
		
	}
}

