package dalapo.factech.tileentity.specialized;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;

public class TileEntityCompressionChamber extends TileEntityFluidMachine {

	private int activeRecipe = -1;
	public TileEntityCompressionChamber() {
		super("cchamber", 1, 2, 1, 1, 1, RelativeSide.BACK);
		setDisplayName("Compression Chamber");
	}
	
	private boolean validateFluidTank()
	{
		if (tanks[0].getFluid() == null) return true;
		Fluid fluid = tanks[0].getFluid().getFluid();
		return tanks[0].getFluidAmount() >= 1000;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && activeRecipe != -1;
	}
	
	@Override
	public void getHasWork()
	{
		List<CompressorRecipe> recipes = MachineRecipes.COMPRESSOR;
		boolean recipeFound = false;
		for (int i=0; i<recipes.size(); i++)
		{
			CompressorRecipe cr = recipes.get(i);
			
			if (cr.getFluidIn() != null && tanks[0].getFluid() != null)
			{
				if (cr.getFluidIn().getFluid() != tanks[0].getFluid().getFluid() || cr.getFluidIn().amount > tanks[0].getFluidAmount()) continue;
			}
			else if ((cr.getFluidIn() == null) != (tanks[0].getFluid() == null)) continue;
			
			if (cr.getItemIn().isItemEqual(getInput()) && FacStackHelper.canCombineStacks(cr.getItemOut(), getOutput()))
			{
				recipeFound = true;
				activeRecipe = i;
				break;
			}
		}
		if (getInput().isEmpty() || recipeFound == false)
		{
			activeRecipe = -1;
		}
	}

	@Override
	protected boolean performAction() {
		CompressorRecipe cr = MachineRecipes.COMPRESSOR.get(activeRecipe);
		if (cr.getFluidIn() != null)
		{
			tanks[0].drainInternal(cr.getFluidIn().amount, true);
		}
		if (doOutput(cr.getItemOut()))
		{
			getInput().shrink(cr.getItemIn().getCount());
			getHasWork();
		}
		return true;
	}

	@Override
	public int getOpTime() {
		return 100;
	}
	
	public static class CompressorRecipe
	{
		private FluidStack fluidIn;
		private ItemStack itemIn;
		private ItemStack itemOut;
		
		public CompressorRecipe(ItemStack itemIn, FluidStack fluidIn, ItemStack itemOut)
		{
			this.fluidIn = fluidIn;
			this.itemIn = itemIn;
			this.itemOut = itemOut;
		}
		
		public ItemStack getItemIn()
		{
			return itemIn.copy();
		}
		
		public ItemStack getItemOut()
		{
			return itemOut.copy();
		}
		
		public FluidStack getFluidIn()
		{
			if (fluidIn == null) return null;
			else return fluidIn.copy();
		}
	}
}
