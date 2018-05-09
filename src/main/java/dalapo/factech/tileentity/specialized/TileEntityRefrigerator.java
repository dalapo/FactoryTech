package dalapo.factech.tileentity.specialized;

import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;

public class TileEntityRefrigerator extends TileEntityFluidMachine {

	private int amountToDrain;
	
	public TileEntityRefrigerator() {
		super("fridge", 0, 3, 1, 1, 1, RelativeSide.BACK);
		setDisplayName("Refrigeration Unit");
	}

	@Override
	protected boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	private ItemStack getRecipeOutput()
	{
		for (MachineRecipe<FluidStack, ItemStack> e : MachineRecipes.REFRIGERATOR)
		{
			if (hasBadParts() && !e.worksWithBad()) continue;
			FluidStack in = e.input();
			if (tanks[0].getFluid() != null && tanks[0].getFluid().getFluid().equals(in.getFluid()) &&
					tanks[0].getFluidAmount() >= in.amount)
			{
				amountToDrain = in.amount;
				return e.output().copy();
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
		return 140;
	}

}