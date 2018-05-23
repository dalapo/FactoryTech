package dalapo.factech.auxiliary;

import net.minecraft.util.math.ChunkPos;

public class ChunkWithDimension extends ChunkPos
{
	private int dimension;
	
	public ChunkWithDimension(int dim, int x, int z)
	{
		super(x, z);
		dimension = dim;
	}
	
	public int dim()
	{
		return dimension;
	}
	
	public int x()
	{
		return x;
	}
	
	public int z()
	{
		return z;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof ChunkWithDimension)
		{
			ChunkWithDimension c = (ChunkWithDimension)o;
			return this.x == c.x && this.z == c.z && this.dimension == c.dimension;
		}
		else if (o instanceof ChunkPos)
		{
			ChunkPos c = (ChunkPos)o;
			return this.x == c.x && this.z == c.z;
		}
		return false;
	}
}