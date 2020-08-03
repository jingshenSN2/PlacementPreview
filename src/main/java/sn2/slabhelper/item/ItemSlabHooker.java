package sn2.slabhelper.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sn2.slabhelper.config.SlabHelperConfig;

public class ItemSlabHooker extends Item {

	private int effective_range = 0;
	private boolean effect_other_slab = false;

	public ItemSlabHooker(ToolMaterial material, int range, boolean effect_other_slab) {
		super(new Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(material.getDurability()));
		this.effective_range = range;
		this.effect_other_slab = effect_other_slab;
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(new TranslatableText("item.slabhelper.slab_hooker.tooltip"));
		tooltip.add(new TranslatableText("item.slabhelper.slab_hooker.tooltip.range", effective_range * 2 + 1,
				effective_range * 2 + 1)
						.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(152 + 251 * 256 + 152 * 256 * 256))));
		if (!this.effect_other_slab && this.effective_range != 0)
			tooltip.add(new TranslatableText("item.slabhelper.slab_hooker.tooltip.onlyeffectonsame")
					.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(34 + 34 * 256 + 178 * 256 * 256))));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		// exceptions
		World world = context.getWorld();
		if (world.isClient)
			return ActionResult.PASS;
		PlayerEntity player = context.getPlayer();
		if (SlabHelperConfig.useHookerOnlySurvivalMode && player.isCreative())
			return ActionResult.PASS;
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		// deal with center slab block
		if (!(state.getBlock() instanceof SlabBlock))
			return ActionResult.PASS;
		if (state.get(SlabBlock.TYPE) == SlabType.DOUBLE)
			return ActionResult.PASS;

		SlabType baseType = state.get(SlabBlock.TYPE);
		SlabType toType = (baseType == SlabType.TOP) ? SlabType.BOTTOM : SlabType.TOP;
		int damage = 0;
		for (int i = -effective_range; i <= +effective_range; i++) {
			for (int j = -effective_range; j <= +effective_range; j++) {
				if (this.changeState(world, pos.add(i, 0, j), toType, player, state.getBlock()))
					++damage;
			}
		}
		// damage item
		player.addExhaustion(0.005F);
		context.getStack().damage(damage, (LivingEntity) player, ((e) -> {
			e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
		}));
		return ActionResult.PASS;
	}

	private boolean changeState(World world, BlockPos pos, SlabType toType, PlayerEntity player, Block baseBlock) {
		BlockState state = world.getBlockState(pos);
		if (!this.effect_other_slab && !state.getBlock().is(baseBlock))
			return false;
		if (!(state.getBlock() instanceof SlabBlock))
			return false;
		if (state.get(SlabBlock.TYPE) == SlabType.DOUBLE)
			return false;
		if (state.get(SlabBlock.TYPE) != toType) {
			world.setBlockState(pos, state.with(SlabBlock.TYPE, toType));
			if (toType == SlabType.TOP) {
				Box box = new Box(pos);
				world.getEntities(null, box).forEach(e -> {
					e.setBoundingBox(e.getBoundingBox().offset(new Vec3d(0, 0.5, 0)));
					e.moveToBoundingBoxCenter();
				});
				;
			}
			return true;
		}
		return false;
	}
}
