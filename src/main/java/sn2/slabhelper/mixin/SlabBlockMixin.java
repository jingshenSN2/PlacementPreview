package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import sn2.slabhelper.callbacks.SlabShapeCallBack;

@Mixin(SlabBlock.class)
public class SlabBlockMixin {

	@Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
	public void renderShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> info) {
		TypedActionResult<VoxelShape> result = SlabShapeCallBack.EVENT.invoker().render(state, world, pos, context);
		if (result.getResult() == ActionResult.FAIL)
			info.setReturnValue(result.getValue());
	}
}
