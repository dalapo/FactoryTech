package dalapo.factech.helper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FacMathHelper {
	
	private FacMathHelper() {} // Prevent instantiation
	
	public static int getMin(int a, int b)
	{
		return (a < b ? a : b);
	}
	
	public static int getMax(int a, int b)
	{
		return (a > b ? a : b);
	}
	
	public static boolean isInRange(int x, int min, int max)
	{
		return (x >= min && x < max);
	}
	
	public static boolean isInRangeInclusive(int x, int min, int max)
	{
		return (x >= min && x <= max);
	}
	
	public static BlockPos withOffset(BlockPos init, EnumFacing dir)
	{
		return withOffsetAndDist(init, dir, 1);
	}
	
	public static BlockPos withOffsetAndDist(BlockPos init, EnumFacing dir, int dist)
	{
		switch (dir)
		{
		case UP:
			return init.up(dist);
		case DOWN:
			return init.down(dist);
		case NORTH:
			return init.north(dist);
		case SOUTH:
			return init.south(dist);
		case WEST:
			return init.west(dist);
		case EAST:
			return init.east(dist);
		}
		return init;
	}
	
	public static boolean matchAny(int[] a, int[] b)
	{
		for (int i=0; i<getMin(a.length, b.length); i++)
		{
			if (a[i] == b[i]) return true;
		}
		return false;
	}
	
	public static double absDist(int dx, int dy, int dz)
	{
		return MathHelper.sqrt(dx*dx + dy*dy + dz*dz);
	}
	
	public static EnumFacing getDirectionFromEntity(BlockPos clicked, EntityLivingBase elb)
	{
		return EnumFacing.getFacingFromVector(
				(float)(elb.posX - clicked.getX()),
				(float)(elb.posY - clicked.getY()),
				(float)(elb.posZ - clicked.getZ()));
	}
	
	public static EnumFacing getDirectionFromEntityXZ(BlockPos clicked, EntityLivingBase elb)
	{
		return EnumFacing.getFacingFromVector(
				(float)(elb.posX - clicked.getX()),
				0,
				(float)(elb.posZ - clicked.getZ()));
	}

	public static int sum(int[] arr)
	{
		int count = 0;
		for (int i : arr)
		{
			count += i;
		}
		return count;
	}
	
	public static int[][] identity(int size)
	{
		int[][] mtrx = new int[size][size];
		for (int i=0; i<mtrx.length; i++)
		{
			mtrx[i][i] = 1;
		}
		return mtrx;
	}
	
	public static float pyth(double a, double b)
	{
		return MathHelper.sqrt(a*a + b*b);
	}
	
	public static double pyth3D(double a, double b, double c)
	{
		return MathHelper.sqrt(a*a + b*b + c*c);
	}
	
	/**
	 * Rotates a point vector around a cardinal axis of arbitrary location.
	 * @param in
	 * The vector to rotate
	 * @param axisLoc
	 * The location of the axis
	 * @param axis
	 * The axis direction. 0=X, 1=Y, 2=Z
	 * @param angle
	 * The angle in radians to rotate the vector
	 * @return
	 * The rotated vector
	 */
	public static Vec3d rotateGeneral(Vec3d in, Vec3d axisLoc, int axis, float angle)
	{
		float radius = 0;
		float theta = 0;
		switch(axis)
		{
		case 0:
			radius = pyth(in.y-axisLoc.y, in.z-axisLoc.z);
			theta = (float)MathHelper.atan2(in.y-axisLoc.y, in.z-axisLoc.z) + angle;
			return new Vec3d(in.x, radius*MathHelper.sin(theta)+axisLoc.y, radius*MathHelper.cos(theta)+axisLoc.z);
		case 1:
			radius = pyth(in.z-axisLoc.z, in.x-axisLoc.x);
			theta = (float)MathHelper.atan2(in.z-axisLoc.z, in.x-axisLoc.x);
			return new Vec3d(radius*MathHelper.cos(theta)+axisLoc.x, in.y, radius*MathHelper.sin(theta)+axisLoc.z);
		case 2:
			radius = pyth(in.x-axisLoc.x, in.y-axisLoc.y);
			theta = (float)MathHelper.atan2(in.x-axisLoc.x, in.y-axisLoc.y);
			return new Vec3d(radius*MathHelper.sin(theta)+axisLoc.x, radius*MathHelper.cos(theta)+axisLoc.y, in.z);
		}
		return in;
	}
	
	public EnumFacing clockwise(EnumFacing init)
	{
		switch (init)
		{
		case NORTH:
			return EnumFacing.EAST;
		case EAST:
			return EnumFacing.SOUTH;
		case SOUTH:
			return EnumFacing.WEST;
		case WEST:
			return EnumFacing.NORTH;
			default:
				return EnumFacing.UP;
		}
	}
}