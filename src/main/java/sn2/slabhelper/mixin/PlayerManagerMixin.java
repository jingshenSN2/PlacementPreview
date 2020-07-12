package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import sn2.slabhelper.callbacks.PlayerLoginCallback;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	
	@Inject(method = "onPlayerConnect", at = @At("TAIL"), cancellable = true)
	public void login(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
		PlayerLoginCallback.EVENT.invoker().onLogin(connection, player);
	}
}
