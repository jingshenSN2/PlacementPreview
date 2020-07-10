package sn2.slabhelper.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface MineSlabCallback {
	public TypedActionResult<Boolean> check(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner);
	
	Event<MineSlabCallback> EVENT = EventFactory.createArrayBacked(MineSlabCallback.class, 
			listeners -> (stack, world, state, pos, miner) -> {
				for (MineSlabCallback callback : listeners) {
					if (callback.check(stack, world, state, pos, miner).getResult() != ActionResult.FAIL)
						return new TypedActionResult<>(ActionResult.PASS, true);
				}
				return new TypedActionResult<>(ActionResult.PASS, true);
			});
}
