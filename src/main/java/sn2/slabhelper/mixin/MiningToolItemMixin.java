package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sn2.slabhelper.callbacks.MineSlabCallback;


@Mixin(MiningToolItem.class)
public class MiningToolItemMixin {
	@Inject(method = "postMine", at = @At("HEAD"), cancellable = true)
	public void accept(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> info) {
		TypedActionResult<Boolean> result = MineSlabCallback.EVENT.invoker().check(stack,world, state, pos, miner);
		if (result.getResult() == ActionResult.FAIL)
			info.setReturnValue(result.getValue());
	}
}
