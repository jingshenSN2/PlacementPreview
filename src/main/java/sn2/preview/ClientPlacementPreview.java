package sn2.preview;

import net.fabricmc.api.ClientModInitializer;
import sn2.preview.callbacks.ClientCallbackRegistry;

public class ClientPlacementPreview implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// register key binding
		KeyBinds.init();
		ClientCallbackRegistry.init();
	}

}
