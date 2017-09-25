package dalapo.factech.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class FacBlockHelper
{
	private FacBlockHelper() {}
	
	public static void updateBlock(World world, BlockPos pos)
	{
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
}