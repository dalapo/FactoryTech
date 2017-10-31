package dalapo.factech.tileentity.specialized;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import dalapo.factech.tileentity.TileEntityMachine;

// Apparently, electrorefining doesn't work on iron or nickel. Oh well. I'm not above bending the rules a bit.
public class TileEntityElectroplater extends TileEntityFluidMachine {
	
	private int outputNum = 0;
	public TileEntityElectroplater() {
		super("electroplater", 1, 3, 1, 1, 1, RelativeSide.BACK);
	}

	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
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
		for (Entry<ItemStack, ItemStack> entry : MachineRecipes.ELECTROPLATER.entrySet())
		{
			if (FacStackHelper.matchStacksWithWildcard(getInput(), entry.getKey(), true) && getOutput().isItemEqual(entry.getValue()) && getOutput().getCount() < 64)
			{
				hasWork = true;
				outputNum = entry.getValue().getCount();
				return;
			}
		}
		hasWork = false;
	}
	
	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.BATTERY, 0.2F, 1.5F, 0.8F*kValue[0][1], (int)(8*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.WIRE, 0.4F, 1.2F, 1F*kValue[1][1], (int)(2*kValue[1][0]));
		partsNeeded[2] = new MachinePart(PartList.MIXER, 0.1F, 1.1F, (int)(16*kValue[2][0]));
	}

	@Override
	protected boolean performAction() {
		tanks[0].drainInternal(500, true);
		getOutput().grow(outputNum);
		getInput().shrink(1);
		getHasWork();
		return true;
	}

	@Override
	public int getOpTime() {
		// 20 seconds. Electrowinning is slow.
		return 400;
	}

	
}
