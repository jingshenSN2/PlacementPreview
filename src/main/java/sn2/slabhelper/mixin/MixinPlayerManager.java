package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import sn2.slabhelper.VersionCheck;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

	@Inject(method = "onPlayerConnect", at = @At("TAIL"), cancellable = true)
	public void SLABHELPER$LOGIN(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
		String update = VersionCheck.check();
		if (update != null) {
			MutableText text = new TranslatableText("message.player.update", update,
					new TranslatableText("message.player.update.here")
							.setStyle(Style.EMPTY.withFormatting(Formatting.UNDERLINE, Formatting.ITALIC)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
											"https://www.curseforge.com/minecraft/mc-mods/slab-helper"))));
			player.sendMessage(text.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(10025880))), false);
		}
	}
}
