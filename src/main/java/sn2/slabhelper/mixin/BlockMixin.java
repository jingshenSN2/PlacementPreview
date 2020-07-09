package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sn2.slabhelper.callbacks.BlockBreakCallback;

@Mixin(Block.class)
public class BlockMixin {
	
	@Inject(method = "afterBreak", at = @At("HEAD"), cancellable = true)
	public void beforeDrop(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo info) {
		if (BlockBreakCallback.EVENT.invoker().accept(world, player, pos, state, blockEntity, stack) == ActionResult.FAIL)
			info.cancel();
	}
}
