package dalapo.factech.tileentity.specialized;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Pair;
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
		for (Pair<ItemStack, ItemStack> entry : MachineRecipes.ELECTROPLATER)
		{
			if (FacStackHelper.matchStacksWithWildcard(getInput(), entry.a, true) && getOutput().isItemEqual(entry.b) && getOutput().getCount() < 64)
			{
				hasWork = true;
				outputNum = entry.b.getCount();
				return;
			}
		}
		hasWork = false;
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
		// 16 seconds. Electrowinning is slow.
		return 320;
	}

	
}
