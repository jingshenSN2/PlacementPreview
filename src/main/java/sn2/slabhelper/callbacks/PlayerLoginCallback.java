package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerLoginCallback {
	void onLogin(ClientConnection connection, ServerPlayerEntity player);
	
	Event<PlayerLoginCallback> EVENT = EventFactory.createArrayBacked(PlayerLoginCallback.class, 
			listeners -> (connection, player) -> {
				for (PlayerLoginCallback callback : listeners) {
					callback.onLogin(connection, player);
				}
			});
}
