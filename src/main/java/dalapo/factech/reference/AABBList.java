package dalapo.factech.reference;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class AABBList
{
	private AABBList() {}
	
	public static final AxisAlignedBB EMPTY = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	public static final AxisAlignedBB FLAT = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
	public static final AxisAlignedBB BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	public static final AxisAlignedBB TOP_IN = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	public static final AxisAlignedBB SHRUNK = new AxisAlignedBB(0.0625D, 0.0625D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
	public static final AxisAlignedBB BOTTOM_IN = new AxisAlignedBB(0.0D, 0.625, 0.0D, 1.0D, 1.0D, 1.0D);
	
	public static final AxisAlignedBB getCube(BlockPos centre, int range)
	{
		return new AxisAlignedBB(centre.getX()-range, centre.getY()-range, centre.getZ()-range, centre.getX()+range, centre.getY()+range, centre.getZ()+range);
	}
}