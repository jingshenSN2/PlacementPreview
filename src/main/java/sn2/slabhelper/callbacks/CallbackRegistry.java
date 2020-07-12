package sn2.slabhelper.callbacks;


import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import sn2.slabhelper.SlabHelper;
import sn2.slabhelper.SlabHelperConfig;

public class CallbackRegistry {
	public static void init() {
		
		// additional line callback
		if (SlabHelperConfig.renderAdditionalEdges)
			SlabShapeCallBack.EVENT.register((state, world, pos, context) -> {
				if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
					return new TypedActionResult<>(ActionResult.PASS, null);
				// A shape unioned by two half block
				VoxelShape shape = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.999D, 16.0D), 
						Block.createCuboidShape(0.0D, 8.001D, 0.0D, 16.0D, 16.0D, 16.0D));
				return new TypedActionResult<>(ActionResult.FAIL, shape);
			});
		
		ServerSidePacketRegistry.INSTANCE.register(SlabHelper.HALFMINE,  (packetContext, attachedData) -> {
			boolean enable = SlabHelper.getHalfMineStatus(packetContext.getPlayer());
			if (enable == false) {
				SlabHelper.setHalfMineStatus(packetContext.getPlayer(), true);
				System.out.println("enable");
			}
			else {
				SlabHelper.setHalfMineStatus(packetContext.getPlayer(), false);
				System.out.println("disable");
			}
		});
		
	}
}
