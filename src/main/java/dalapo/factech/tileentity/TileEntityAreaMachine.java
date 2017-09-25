package dalapo.factech.tileentity;

public abstract class TileEntityAreaMachine extends TileEntityMachine
{
	private int range;
	
	public TileEntityAreaMachine(String name, int inSlots, int partSlots, int outSlots, RelativeSide partSide, int range)
	{
		super(name, inSlots, partSlots, outSlots, partSide);
		this.range = range;
	}
	
	public int getAdjustedRange()
	{
		if (this.installedUpgrade == 3) return 2 * range;
		else return range;
	}
}