package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import sn2.slabhelper.ClientSlabHelper;
import sn2.slabhelper.util.MathUtils;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@Shadow
	private MinecraftClient client;

	@Inject(method = "attackBlock", at = @At(value = "RETURN", ordinal = 2) )
	public void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
		BlockState origin = client.world.getBlockState(pos);
		if (ClientSlabHelper.isHalfMine() && origin.getBlock() instanceof SlabBlock && origin.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
			BlockState state = MathUtils.getNewState(pos, client.player, origin);
			if (state.get(SlabBlock.TYPE) == SlabType.TOP)
				ClientSlabHelper.setDamageRender(pos, SlabType.BOTTOM);
			else
				ClientSlabHelper.setDamageRender(pos, SlabType.TOP);
		}
	}
}
