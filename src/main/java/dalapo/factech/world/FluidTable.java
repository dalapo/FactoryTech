package dalapo.factech.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidTable {
	
	private long seed;
	private static final int propaneWeight = 5;
	private static final int sulphurWeight = 15;
	
	public FluidTable(World world)
	{
		seed = world.getSeed();
	}
	
	public int getPropaneValue(BlockPos pos)
	{
		Random random = new Random(seed);
		return 0;
	}
	
	public int getSulphurValue(BlockPos pos)
	{
		return 0;
	}
}