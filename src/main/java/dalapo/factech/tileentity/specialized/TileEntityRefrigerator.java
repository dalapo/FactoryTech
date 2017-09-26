package dalapo.factech.tileentity.specialized;

import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;

public class TileEntityRefrigerator extends TileEntityFluidMachine {

	private int amountToDrain;
	
	public TileEntityRefrigerator() {
		super("fridge", 0, 3, 1, 1, 1, RelativeSide.BACK);
		setDisplayName("Refrigeration Unit");
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.PISTON, 0.2F, 1.15F, 0.7F, 5);
		partsNeeded[1] = new MachinePart(PartList.MOTOR, 0.3F, 1.1F, 0.75F, 4);
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_2, 0.2F, 1.3F, 0.5F, 8);
	}
	
	@Override
	protected boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	private ItemStack getRecipeOutput()
	{
		Set<Entry<FluidStack, ItemStack>> entries = MachineRecipes.REFRIGERATOR.entrySet();
		for (Entry<FluidStack, ItemStack> e : entries)
		{
			FluidStack in = e.getKey();
			if (tanks[0].getFluid() != null && tanks[0].getFluid().getFluid().equals(in.getFluid()) &&
					tanks[0].getFluidAmount() >= in.amount)
			{
				amountToDrain = in.amount;
				return e.getValue().copy();
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void getHasWork()
	{
		ItemStack result = getRecipeOutput();
		if (result.isEmpty())
		{
			hasWork = false;
			return;
		}
		else hasWork = true;
	}
	
	@Override
	protected boolean performAction()
	{
		if (doOutput(getRecipeOutput()))
		{
			tanks[0].drainInternal(amountToDrain, true);
			getHasWork();
			return true;
		}
		else
		{
			getHasWork();
			return false;
		}
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 200;
	}

}