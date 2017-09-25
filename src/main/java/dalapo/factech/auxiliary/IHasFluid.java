package dalapo.factech.auxiliary;

import net.minecraftforge.fluids.FluidStack;

public interface IHasFluid {
	public void overrideTank(FluidStack fs, int tank);
}