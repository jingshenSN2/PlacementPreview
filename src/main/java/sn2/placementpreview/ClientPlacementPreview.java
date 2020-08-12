package sn2.placementpreview;

import net.fabricmc.api.ClientModInitializer;
import sn2.placementpreview.callbacks.ClientCallbackRegistry;

public class ClientPlacementPreview implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// register key binding
		KeyBinds.init();
		ClientCallbackRegistry.init();
	}

}
