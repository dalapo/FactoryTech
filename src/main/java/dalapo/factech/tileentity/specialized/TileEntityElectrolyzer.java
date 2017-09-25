package dalapo.factech.tileentity.specialized;

import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;

public class TileEntityElectrolyzer extends TileEntityFluidMachine {

	public TileEntityElectrolyzer() {
		super("electrolyzer", 1, 3, 1, 1, 1, RelativeSide.BOTTOM);
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.BATTERY, 0.15F, 1.2F);
		partsNeeded[1] = new MachinePart(PartList.WIRE, 0.1F, 1.1F);
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_1, 0.15F, 1.15F);
	}

	private ItemStack getResult(ItemStack is)
	{
		for (Entry<ItemStack, ItemStack> entry : MachineRecipes.ELECTROLYZER.entrySet())
		{
			ItemStack in = entry.getKey().copy();
			if (getInput().isItemEqual(in) && in.getCount() <= is.getCount()) return entry.getValue().copy();
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public void getHasWork()
	{
		if (tanks[0].getFluid() == null || !tanks[0].getFluid().getFluid().equals(ModFluidRegistry.h2so4))
		{
			hasWork = false;
			return;
		}
		if (tanks[0].getFluidAmount() < 200 || getInput().isEmpty() || getOutput().isEmpty())
		{
			hasWork = false;
			return;
		}
		ItemStack is = getResult(getInput());
		if (!is.isEmpty() && (getOutput().isEmpty() || (getOutput().isItemEqual(is) && getOutput().getCount() + is.getCount() <= 64)))
		{
			hasWork = true;
			return;
		}
		hasWork = false;
	}
	
	@Override
	protected boolean performAction() {
		if (doOutput(getResult(getInput())))
		{
			getInput().shrink(1);
			return true;
		}
		return false;
	}

	@Override
	public int getOpTime() {
		return 180;
	}

}