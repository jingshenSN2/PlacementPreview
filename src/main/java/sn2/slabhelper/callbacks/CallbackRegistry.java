package sn2.slabhelper.callbacks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import sn2.slabhelper.SlabHelperConfig;
import sn2.slabhelper.item.ItemSlabPickaxe;

public class CallbackRegistry {
	public static void init() {
		if (SlabHelperConfig.renderAdditionalEdges)
			SlabShapeCallBack.EVENT.register((state, world, pos, context) -> {
				if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
					return new TypedActionResult<>(ActionResult.PASS, null);
				VoxelShape shape = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.999D, 16.0D), 
						Block.createCuboidShape(0.0D, 8.001D, 0.0D, 16.0D, 16.0D, 16.0D));
				return new TypedActionResult<>(ActionResult.FAIL, shape);
			});
		
		if (SlabHelperConfig.enablePickaxe) {
			BlockBreakCallback.EVENT.register((world, player, pos, state, blockEntity, stack) -> {
				if (world.isClient)
					return ActionResult.PASS;
				if (player.isCreative())
					return ActionResult.PASS;
				if (!(stack.getItem() instanceof ItemSlabPickaxe)) 
					return ActionResult.PASS;
				if (!(state.getBlock() instanceof SlabBlock)) 
					return ActionResult.PASS;
				if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
					return ActionResult.PASS;
				BlockState halfSlab = state.with(SlabBlock.TYPE, SlabType.TOP);
				player.incrementStat(Stats.MINED.getOrCreateStat(halfSlab.getBlock()));
			    player.addExhaustion(0.005F);
			    System.out.println("nb");
			    Block.dropStacks(halfSlab, world, pos, blockEntity, player, stack);
				return ActionResult.FAIL;
			});
		}
	}
}
