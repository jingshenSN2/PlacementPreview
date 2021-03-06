package sn2.placementpreview.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailPlacementHelper;
import net.minecraft.block.enums.RailShape;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import sn2.placementpreview.PreviewPlayerEntity;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
	@Shadow
	public ClientPlayerEntity player;
	@Shadow
	public HitResult crosshairTarget;
	@Shadow
	private BlockRenderManager blockRenderManager;
	@Shadow
	public GameRenderer gameRenderer;
	@Shadow
	private BufferBuilderStorage bufferBuilders;
	@Shadow
	private RenderTickCounter renderTickCounter;
	
	@Inject(method = "render", at = @At("TAIL"))
	private void PREVIEW$RENDER(boolean tick, CallbackInfo info) {
		if (player == null)
			return;
		// Check function enabled
		if (!((PreviewPlayerEntity) player).isPreview())
			return;
		// Get the target block
		HitResult result = crosshairTarget;
		if (result.getType() != HitResult.Type.BLOCK)
			return;
		BlockPos pos = new BlockPos(result.getPos());
		// Get the placement result
		ItemStack stack = player.getMainHandStack();
		if (!(stack.getItem() instanceof BlockItem))
			return;
		ItemPlacementContext context = new ItemPlacementContext(player, player.getActiveHand(), stack, (BlockHitResult) result);
		if (!context.canPlace())
			return;
		// TODO: Wrong state for railblock
		BlockState state = ((BlockItem) stack.getItem()).getBlock().getPlacementState(context);
		// Check place pos is not the same as target pos(Means cannot place)
		if (!pos.equals(context.getBlockPos()))
			return;
		// Calculate the yaw and pitch of player
		float tickDelta = renderTickCounter.tickDelta;
	    float pitch = player.getPitch(tickDelta);
	    float yaw = player.getYaw(tickDelta);
	    // Prepare MatrixStack and VertexConsumer for the renderBlock method
		Camera camera = gameRenderer.getCamera();
	    RenderLayer renderLayer = RenderLayers.getBlockLayer(state);
	    VertexConsumer consumer = bufferBuilders.getEntityVertexConsumers().getBuffer(renderLayer);
		MatrixStack matrix = new MatrixStack();
		matrix.push();
		matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch));
	    matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(yaw + 180.0F));
	    // TODO: camera.getPos works not correctly when player is moving
		matrix.translate((double)pos.getX() - camera.getPos().x, (double)pos.getY() - camera.getPos().y, (double)pos.getZ() - camera.getPos().z);
		// Render the preview block
		// TODO: Can't change the alpha value of the render
		blockRenderManager.renderBlock(state, pos, player.world, matrix, consumer, true, new Random());
		matrix.pop();
	}
}
