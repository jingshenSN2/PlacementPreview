package sn2.slabhelper.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import sn2.slabhelper.config.SlabHelperConfig;

@Environment(EnvType.CLIENT)
@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin{

	@Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
	public void renderShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> info) {
		if (SlabHelperConfig.renderAdditionalEdges && state.get(SlabBlock.TYPE) == SlabType.DOUBLE)
		// A shape unioned by two half block
			info.setReturnValue( VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.999D, 16.0D), 
				Block.createCuboidShape(0.0D, 8.001D, 0.0D, 16.0D, 16.0D, 16.0D)));
	}

}
