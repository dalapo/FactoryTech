package dalapo.factech.helper;

import java.util.HashMap;
import java.util.Map;

import dalapo.factech.FactoryTech;
import dalapo.factech.reference.StateList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class FacBlockHelper
{
	private FacBlockHelper() {}
	
	public static final Map<Pair<Item, Integer>, IPlantable> CROPS = new HashMap<Pair<Item, Integer>, IPlantable>();
	
	static {
		CROPS.put(new Pair<>(Items.WHEAT_SEEDS, 0), (IPlantable)Blocks.WHEAT);
		CROPS.put(new Pair<>(Items.MELON_SEEDS, 0), (IPlantable)Blocks.MELON_STEM);
		CROPS.put(new Pair<>(Items.PUMPKIN_SEEDS, 0), (IPlantable)Blocks.PUMPKIN_STEM);
		CROPS.put(new Pair<>(Items.BEETROOT_SEEDS, 0), (IPlantable)Blocks.BEETROOTS);
		CROPS.put(new Pair<>(Items.CARROT, 0), (IPlantable)Blocks.CARROTS);
		CROPS.put(new Pair<>(Items.POTATO, 0), (IPlantable)Blocks.POTATOES);
		CROPS.put(new Pair<>(Items.NETHER_WART, 0), (IPlantable)Blocks.NETHER_WART);
		CROPS.put(new Pair<>(Items.REEDS, 0), (IPlantable)Blocks.REEDS);
	}
	
	public static void updateBlock(World world, BlockPos pos)
	{
		if (FactoryTech.DEBUG_GENERAL) Logger.info("Updated block at " + pos);
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
	
	public static IBlockState rotateTo(World world, BlockPos pos, EnumFacing dir)
	{
		return world.getBlockState(pos).getBlock().getStateFromMeta(dir.ordinal());
	}
	
	public static IBlockState rotateOnPlane(World world, BlockPos pos, EnumFacing dir)
	{
		if (dir == EnumFacing.UP || dir == EnumFacing.DOWN) return world.getBlockState(pos);
		return world.getBlockState(pos).getBlock().getStateFromMeta(dir.ordinal());
	}
	
	public static EnumFacing getDirection(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getValue(StateList.directions);
	}
	
	public static EnumFacing nextRotation(World world, BlockPos pos, EnumFacing current, boolean plane)
	{
		int newRotation = 0;
		if (plane)
		{
			newRotation = (current.ordinal() + 1) % 4 + 2;
		}
		else
		{
			newRotation = (current.ordinal() + 1) % 6;
		}
		return EnumFacing.getFront(newRotation);
	}
	
	public static Block getBlock(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock();
	}
}