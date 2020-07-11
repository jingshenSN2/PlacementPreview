package sn2.slabhelper.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sn2.slabhelper.SlabHelperConfig;

public class ItemSlabPickaxe extends PickaxeItem {
	
	public ItemSlabPickaxe(Settings settings) {
		super(new ToolSlab(), 0, -2.8F, settings);
	}
	
	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
	     tooltip.add(new TranslatableText("item.slabhelper.slab_pickaxe.tooltip"));
	}

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
	      return (state.getBlock() instanceof SlabBlock) ? this.miningSpeed : 1.0F;
	}
	
	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (world.isClient)
			return true;
		PlayerEntity player = (PlayerEntity) miner;
		if (player.isCreative())
			return true;
		if (!(state.getBlock() instanceof SlabBlock)) 
			return true;
		//System.out.println("slab!");
		if (state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
			return true;
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
			if (hitY >= player.getEyeY()) 
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
		return true;
	}

}
