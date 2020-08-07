package sn2.slabhelper.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import sn2.slabhelper.HalfMinePlayerEntity;
import sn2.slabhelper.util.MathUtils;

@Environment(EnvType.CLIENT)
@Mixin(BlockRenderManager.class)
public class MixinBlockRenderManager {

	@Shadow
	private BlockModels models;
	@Shadow
	private BlockModelRenderer blockModelRenderer;
	@Shadow
	private final Random random = new Random();
	
	@Inject(method = "renderDamage", at = @At("HEAD"), cancellable = true)
	public void SLABHELPER$DAMAGE(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix,
			VertexConsumer vertexConsumer, CallbackInfo info) {
		if (state.getBlock() instanceof SlabBlock && state.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
			ClientPlayerEntity player = MinecraftClient.getInstance().player;
			if (player == null)
				return;
			if (!((HalfMinePlayerEntity) player).isHalfMine())
				return;
			SlabType type = MathUtils.getHitType(pos, player, state);
			if (type != null)
				state = state.with(SlabBlock.TYPE, type);
			if (state.getRenderType() == BlockRenderType.MODEL) {
				BakedModel bakedModel = this.models.getModel(state);
				long l = state.getRenderingSeed(pos);
				this.blockModelRenderer.render(world, bakedModel, state, pos, matrix, vertexConsumer, true, this.random,
						l, OverlayTexture.DEFAULT_UV);
			}
			info.cancel();
		}
	}

}
