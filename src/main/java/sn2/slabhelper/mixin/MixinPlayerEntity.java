package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import sn2.slabhelper.HalfMinePlayerEntity;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements HalfMinePlayerEntity {
	@Shadow
	public abstract void sendMessage(Text message, boolean actionBar);

	private boolean isHalfMine = false;

	@Override
	public boolean isHalfMine() {
		return isHalfMine;
	}

	@Override
	public void setHalfMine() {
		isHalfMine = !isHalfMine;
		MutableText ON = new TranslatableText("message.player.halfmine.on")
				.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(152 + 251 * 256 + 152 * 256 * 256)));
		MutableText OFF = new TranslatableText("message.player.halfmine.off")
				.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(34 + 34 * 256 + 178 * 256 * 256)));
		this.sendMessage(new TranslatableText("message.player.halfmine", isHalfMine ? ON : OFF), true);
	}
}
