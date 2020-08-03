package sn2.slabhelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import sn2.slabhelper.HalfMinePlayerEntity;
import sn2.slabhelper.config.SlabHelperConfig;
import sn2.slabhelper.util.MathUtils;

@Mixin(ServerPlayerInteractionManager.class)
public class MixinServerPlayerInteractionManager {

	@Shadow
	public ServerWorld world;
	@Shadow
	public ServerPlayerEntity player;
	@Shadow
	private GameMode gameMode;

	@Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
	public void SLABHELPER$TRYBREAK(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockState blockState = this.world.getBlockState(pos);
		if (!this.player.getMainHandStack().getItem().canMine(blockState, this.world, pos, this.player))
			return;
		if (blockState.getBlock() instanceof SlabBlock && SlabHelperConfig.halfmining
				&& blockState.get(SlabBlock.TYPE) == SlabType.DOUBLE && ((HalfMinePlayerEntity) player).isHalfMine()) {
			BlockEntity blockEntity = this.world.getBlockEntity(pos);
			Block block = blockState.getBlock();
			if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode))
				return;
			else {
				block.onBreak(this.world, pos, blockState, this.player);
				/*
				 * boolean bl = this.world.removeBlock(pos, false); if (bl) {
				 * block.onBroken(this.world, pos, blockState); }
				 */
				BlockState newState = MathUtils.getNewState(pos, player, blockState); // get the state after half-mining
				this.world.setBlockState(pos, newState);
				if (player.isCreative()) {
					info.setReturnValue(true);
					return;
				} else {
					ItemStack itemStack = this.player.getMainHandStack();
					ItemStack itemStack2 = itemStack.copy();
					boolean bl2 = this.player.isUsingEffectiveTool(blockState);
					itemStack.postMine(this.world, blockState, pos, this.player);
					if (bl2) {
						block.afterBreak(this.world, this.player, pos, newState, blockEntity, itemStack2);
					}
					info.setReturnValue(true);
					return;
				}
			}
		}
	}

}
