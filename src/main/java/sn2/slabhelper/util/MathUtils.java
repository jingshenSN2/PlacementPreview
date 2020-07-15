package sn2.slabhelper.util;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class MathUtils {
	public static BlockState getNewState(BlockPos pos, PlayerEntity player, BlockState state) {
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
		return state;
	}
}
