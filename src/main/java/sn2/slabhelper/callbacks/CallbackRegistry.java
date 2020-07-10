package sn2.slabhelper.callbacks;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import sn2.slabhelper.SlabHelperConfig;
import sn2.slabhelper.VersionCheck;
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
				if (SlabHelperConfig.needSlabPickaxe && !(stack.getItem() instanceof ItemSlabPickaxe)) 
					return ActionResult.PASS;
				if (!(state.getBlock() instanceof SlabBlock)) 
					return ActionResult.PASS;
				if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
					return ActionResult.PASS;
				BlockState halfSlab = state.with(SlabBlock.TYPE, SlabType.TOP);
				player.incrementStat(Stats.MINED.getOrCreateStat(halfSlab.getBlock()));
			    player.addExhaustion(0.005F);
			    Block.dropStacks(halfSlab, world, pos, blockEntity, player, stack);
				return ActionResult.FAIL;
			});
		}
		if (!SlabHelperConfig.needSlabPickaxe) {
			MineSlabCallback.EVENT.register((stack, world, state, pos, miner) -> {
				if (world.isClient)
					return new TypedActionResult<>(ActionResult.PASS, true);
				PlayerEntity player = (PlayerEntity) miner;
				if (player.isCreative())
					return new TypedActionResult<>(ActionResult.PASS, true);
				if (!(state.getBlock() instanceof SlabBlock)) 
					return new TypedActionResult<>(ActionResult.PASS, true);
				//System.out.println("slab!");
				if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
					return new TypedActionResult<>(ActionResult.PASS, true);
				//System.out.println("full slab!");
				Box box = new Box(pos);
			    Vec3d vec3d = player.getCameraPosVec(1);
			    Vec3d vec3d2 = player.getRotationVec(1);
			    Vec3d vec3d3 = vec3d.add(vec3d2.x * 10, vec3d2.y * 10, vec3d2.z * 10);
			    Optional<Vec3d> result = box.rayTrace(vec3d, vec3d3);
				Vec3d hit = result.get();
				double hitY = hit.getY();
				//System.out.println(hitY);
				double relativeY = hitY - (int)hitY;
				if (relativeY == 0) {
					if (hitY >= player.getPos().y + (double)player.getStandingEyeHeight()) 
						world.setBlockState(pos, state.with(SlabBlock.TYPE, SlabType.TOP));
					else 
						world.setBlockState(pos, state.with(SlabBlock.TYPE, SlabType.BOTTOM));
				}
				else if (relativeY >= 0.5)
					world.setBlockState(pos, state.with(SlabBlock.TYPE, SlabType.BOTTOM));
				else
					world.setBlockState(pos, state.with(SlabBlock.TYPE, SlabType.TOP));
			    if (!player.isCreative())
					stack.damage(1, (LivingEntity)miner, ((e) -> {
				         e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				      }));
				return new TypedActionResult<>(ActionResult.FAIL, true);
			});
		}
	}
}
