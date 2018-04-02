package dalapo.factech.auxiliary;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public interface IHasFluid {
	public FluidTank getTank(int tank);
	public void overrideTank(FluidStack fs, int tank);
}