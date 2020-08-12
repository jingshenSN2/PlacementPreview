package sn2.preview.callbacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sn2.preview.PreviewPlayerEntity;
import sn2.preview.KeyBinds;

public class ClientCallbackRegistry {
	@Environment(EnvType.CLIENT)
	public static void init() {
		// send half-mine mode change packet to server
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (KeyBinds.preview.wasPressed()) {
				((PreviewPlayerEntity) client.player).setPreview();
			}
		});

	}
}
