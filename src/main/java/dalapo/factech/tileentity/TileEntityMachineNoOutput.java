package dalapo.factech.tileentity;

public abstract class TileEntityMachineNoOutput extends TileEntityMachine {

	public TileEntityMachineNoOutput(String name, int inSlots, int partSlots, RelativeSide partHatch) {
		super(name, inSlots, partSlots, 0, partHatch); // Evil hack but whatever
	}
}