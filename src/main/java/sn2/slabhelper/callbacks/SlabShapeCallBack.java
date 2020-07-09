package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public interface SlabShapeCallBack {
	public TypedActionResult<VoxelShape> render(BlockState state, BlockView world, BlockPos pos, ShapeContext context);
	
	Event<SlabShapeCallBack> EVENT = EventFactory.createArrayBacked(SlabShapeCallBack.class, 
			listeners -> (state, world, pos, context) -> {
				for (SlabShapeCallBack callback : listeners) {
					if (callback.render(state, world, pos, context).getResult() != ActionResult.FAIL)
						return new TypedActionResult<>(ActionResult.PASS, VoxelShapes.fullCube());
				}
				return new TypedActionResult<>(ActionResult.PASS, null);
			});
}
