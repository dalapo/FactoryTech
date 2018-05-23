package dalapo.factech.auxiliary;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PosWithDimension extends BlockPos
{
	private final int dimension;
	private final World world;
	
	public PosWithDimension(World world, int dim, int x, int y, int z)
	{
		super(x, y, z);
		dimension = dim;
		this.world = world;
	}
	
	public PosWithDimension(TileEntity te)
	{
		this(te.getWorld(), te.getWorld().provider.getDimension(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
	}
	
	public PosWithDimension(WorldServer world, int dimensionID, double d, double e, double f)
	{
		super((int)d, (int)e, (int)f);
		dimension = dimensionID;
		this.world = world;
	}

	public int getDimension()
	{
		return dimension;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public String toString()
	{
		return String.format("{%s, %s, %s}; DIM %s", getX(), getY(), getZ(), dimension);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof PosWithDimension)
		{
			PosWithDimension p = (PosWithDimension)o;
			return p.getX() == getX() && p.getY() == getY() && p.getZ() == getZ() && p.dimension == dimension;
		}
		return false;
	}
}
