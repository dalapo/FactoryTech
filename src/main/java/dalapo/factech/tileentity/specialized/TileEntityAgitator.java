package dalapo.factech.tileentity.specialized;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dalapo.factech.auxiliary.MachinePart;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityAgitator extends TileEntityFluidMachine {
//	static List<AgitatorRecipe> MachineRecipes.AGITATOR = new ArrayList<AgitatorRecipe>();
	private int activeRecipe = -1;
	public TileEntityAgitator() {
		super("agitator", 1, 3, 1, 3, 2, RelativeSide.BOTTOM);
		setDisplayName("Agitator");
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && activeRecipe != -1;
	}
	
	/**
	 * Still called getHasWork to be invoked automatically but instead will calculate the active recipe
	 */
	@Override
	public void getHasWork()
	{
		for (int r=0; r<MachineRecipes.AGITATOR.size(); r++)
		{
			boolean flag = true;
			if (!FacStackHelper.areItemsEqualAllowEmpty(getInput(), MachineRecipes.AGITATOR.get(r).input, true))
			{
//				if (r == 0) Logger.info(String.format("Unequal items: %s, %s", getInput(), MachineRecipes.AGITATOR.get(r).input));
				flag = false;
			}
			for (int i=0; i<2; i++)
			{
				if (getTank(MachineRecipes.AGITATOR.get(r).getInputFluid(i)) == -1)
				{
//					if (r == 0) Logger.info(String.format("Fluid not found:  %s", MachineRecipes.AGITATOR.get(r).getInputFluid(i)));
					flag = false;
					break;
				}
				FluidStack output = MachineRecipes.AGITATOR.get(r).fluidOut;
				if ((output != null && tanks[2].getFluid() != null && !tanks[2].getFluid().isFluidEqual(output)) || (output != null && tanks[2].getFluidAmount() + output.amount > 10000)) flag = false;
			}
			if (flag)
			{
				activeRecipe = r;
				return;
			}
		}
		activeRecipe = -1;
	}
	
	private int getTank(FluidStack fluid)
	{
		if (fluid == null)
		{
			for (int i=0; i<2; i++)
			{
				if (tanks[i].getFluid() == null) return i;
			}
		}
		else
		{
			for (int i=0; i<2; i++)
			{
				if (tanks[i].getFluid() != null && tanks[i].getFluid().getFluid().equals(fluid.getFluid()) && tanks[i].getFluidAmount() >= fluid.amount) return i;
			}
		}
		return -1;
		/*
		for (int i=0; i<2; i++)
		{
			boolean nullFlag = false;
			if (tanks[i].getFluidAmount() == 0 || fluid == null || fluid.getFluid() == null) nullFlag = true;
			if (nullFlag)
			{
				if (tanks[i].getFluidAmount() == 0 && (fluid == null || fluid.getFluid() == null)) return i;
				else continue;
			}
			if (tanks[i].getFluid().getFluid().equals(fluid.getFluid()) && tanks[i].getFluidAmount() >= fluid.amount) return i;
		}
		*/
	}

	// Precondition: The Agitator has sufficient resources to complete an operation.
	@Override
	protected boolean performAction() {
		AgitatorRecipe recipe = MachineRecipes.AGITATOR.get(activeRecipe);
//		Logger.info(recipe);
		for (int i=0; i<2; i++)
		{
			int tank = getTank(recipe.getInputFluid(i));
			tanks[tank].drainInternal(recipe.getInputFluid(i) == null ? 0 : recipe.getInputFluid(i).amount, true);
		}
		getInput(0).shrink(recipe.input.getCount());
		
		doOutput(recipe.output.copy());
		
		FluidStack toFill = recipe.fluidOut == null ? null : recipe.fluidOut.copy();
		if (toFill != null)
		{
//			Logger.info(String.format("Adding %smB of %s to tank 2", toFill.amount, toFill.getLocalizedName()));
			tanks[2].fillInternal(recipe.fluidOut.copy(), true);
		}
		getHasWork();
		return true;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 120;
	}

	@Override
	public String getContainerName() {
		return "agitator";
	}
	
	public static class AgitatorRecipe
	{
		FluidStack fluidA;
		FluidStack fluidB;
		ItemStack input;
		ItemStack output;
		FluidStack fluidOut;
		
		public AgitatorRecipe(@Nullable ItemStack input, @Nullable ItemStack output, @Nullable FluidStack fluidOut, @Nonnull FluidStack fluidA, @Nullable FluidStack fluidB)
		{
			this.fluidA = fluidA;
			this.fluidB = fluidB;
			this.input = input != null ? input : ItemStack.EMPTY;
			this.output = output != null ? output : ItemStack.EMPTY;
			this.fluidOut = fluidOut;
		}
		
		public FluidStack getInputFluid(int slot)
		{
			if (slot == 1) return fluidB;
			else return fluidA;
		}
		
		public ItemStack getInputItem()
		{
			return input;
		}
		
		public FluidStack getOutputFluid()
		{
			return fluidOut;
		}
		
		public ItemStack getOutputItem()
		{
			return output;
		}

	}

	@Override
	public void onTankUpdate() {
		getHasWork();
	}
}
