package sn2.slabhelper;

import net.fabricmc.api.ClientModInitializer;
import sn2.slabhelper.callbacks.ClientCallbackRegistry;

public class ClientSlabHelper implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		// TODO Auto-generated method stub
		// register key binding
		SlabHelperKey.init();
		ClientCallbackRegistry.init();
	}

}
