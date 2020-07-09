package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockBreakCallback {
	ActionResult accept(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack);
	
	Event<BlockBreakCallback> EVENT = EventFactory.createArrayBacked(BlockBreakCallback.class, 
			listeners -> (world, player, pos, state, blockEntity, stack) -> {
				for (BlockBreakCallback callback : listeners) {
					if (callback.accept(world, player, pos, state, blockEntity, stack) != ActionResult.FAIL)
						return ActionResult.PASS;
				}
				return ActionResult.PASS;
			});
}
