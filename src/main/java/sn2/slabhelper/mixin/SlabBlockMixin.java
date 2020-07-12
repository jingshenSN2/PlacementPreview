package sn2.slabhelper.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.SlabHelperConfig;
import sn2.slabhelper.callbacks.SlabShapeCallBack;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block {

	public SlabBlockMixin(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
	public void renderShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> info) {
		TypedActionResult<VoxelShape> result = SlabShapeCallBack.EVENT.invoker().render(state, world, pos, context);
		if (result.getResult() == ActionResult.FAIL)
			info.setReturnValue(result.getValue());
	}
	
	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
		
		player.incrementStat(Stats.MINED.getOrCreateStat(this));
	    player.addExhaustion(0.005F);
	    if (SlabHelperConfig.halfmining && state.get(SlabBlock.TYPE) == SlabType.DOUBLE && SlabHelper.getHalfMineStatus(player)) {
	    	Box box = new Box(pos);
		    Vec3d vec3d = player.getCameraPosVec(1);
		    Vec3d vec3d2 = player.getRotationVec(1);
		    Vec3d vec3d3 = vec3d.add(vec3d2.x * 10, vec3d2.y * 10, vec3d2.z * 10);
		    Optional<Vec3d> result = box.rayTrace(vec3d, vec3d3);
			Vec3d hit = result.get();
			double hitY = hit.getY();
			double relativeY = hitY - (int)hitY;
			// hit at top or bottom
			if (relativeY == 0) {
				// hit at bottom
				if (hitY >= player.getPos().y + (double)player.getStandingEyeHeight()) 
					state = state.with(SlabBlock.TYPE, SlabType.TOP);
				// hit at top
				else 
					state = state.with(SlabBlock.TYPE, SlabType.BOTTOM);
			}
			// hit at y>0.5
			else if (relativeY >= 0.5)
				state = state.with(SlabBlock.TYPE, SlabType.BOTTOM);
			// hit at y<0.5
			else
				state = state.with(SlabBlock.TYPE, SlabType.TOP);
			world.setBlockState(pos, state);
			// mc vanilla behaviors
			player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
		    player.addExhaustion(0.005F);
		    dropStacks(state, world, pos, blockEntity, player, stack);
	    }
	    else
	    	dropStacks(state, world, pos, blockEntity, player, stack);
	   }
}
