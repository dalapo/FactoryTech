package dalapo.factech.auxiliary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.tileentity.TileEntityFluidMachine;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

// YO DAWG I HEARD YOU LIKED WRAPPERS SO WE PUT A WRAPPER AROUND ANOTHER WRAPPER
// SO YOU CAN WRAP DATA WHILE YOU WRAP DATA
public class FluidHandlerWrapper implements IFluidHandler {

	FluidTank[] tanks;
	int outputCutoff;
	TileEntityFluidMachine parent;
	
	public FluidHandlerWrapper(FluidTank[] tanks, int outCut, TileEntityFluidMachine parent)
	{
		super();
		this.tanks = tanks;
		if (outCut < tanks.length && outCut >= 0) outputCutoff = outCut;
		else outputCutoff = tanks.length;
		// Can only fill the inputs; can only drain the outputs
		for (int i=0; i<outputCutoff; i++)
		{
			tanks[i].setCanDrain(false);
		}
		for (int i=outputCutoff; i<tanks.length; i++)
		{
			tanks[i].setCanFill(false);
		}
		this.parent = parent;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] properties = new IFluidTankProperties[tanks.length];
		for (int i=0; i<tanks.length; i++)
		{
			properties[i] = new FluidTankPropertiesWrapper(tanks[i]);
		}
		return properties;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		int target = -1;
		int amt = 0;
		for (int i=0; i<tanks.length; i++)
		{
			FluidTank tank = tanks[i];
			if ((tank.getFluid() == null || tank.getFluid().isFluidEqual(resource)) && tank.canFill())
			{
				parent.onTankUpdate();
				return tank.fill(resource, doFill);
			}
			/*
			FluidTank tank = tanks[i];
			if (tank.canFill() && ((tank.getFluid() == null) || (tank.getFluid().isFluidEqual(resource) && tank.getFluidAmount() < tank.getCapacity())))
			{
				target = i;
				amt = FacMathHelper.getMin(resource.amount, tank.getCapacity() - tank.getFluidAmount());
				break;
			}
			*/
		}
		for (int i=0; i<tanks.length; i++)
		{
			FluidTank tank = tanks[i];
			if (tank.getFluid() == null && tank.canFill())
			{
				parent.onTankUpdate();
				return tank.fill(resource, doFill);
			}
		}
		
		return 0;
		
		/*
		if (target == -1)
		{
			for (int i=0; i<tanks.length; i++)
			{
				FluidTank tank = tanks[i];
				if (tank.getFluidAmount() == 0)
				{
					target = i;
					amt = FacMathHelper.getMin(resource.amount, tank.getCapacity());
					break;
				}
			}
		}
		
		if (target != -1)
		{
			FluidTank tank = tanks[target];
			FluidStack toFill = resource.copy();
			toFill.amount = amt;
			tank.fill(toFill, doFill);
		}
		return amt;
		*/
	}

	@Override
	@Nullable
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		int start = outputCutoff < tanks.length ? outputCutoff : 0;
		FluidStack fs = null;
		for (int i=start; i<tanks.length; i++)
		{
			if (tanks[i].getFluid().isFluidEqual(resource) && tanks[i].canDrain())
			{
				fs = tanks[i].drain(resource, doDrain);
				break;
			}
		}
		parent.onTankUpdate();
		return fs;
	}

	@Override
	@Nullable
	public FluidStack drain(int maxDrain, boolean doDrain) {
		int start = outputCutoff < tanks.length ? outputCutoff : 0;
		FluidStack fs = null;
		for (int i=start; i<tanks.length; i++)
		{
			if (tanks[i].canDrain() && tanks[i].getFluidAmount() > 0)
			{
				fs = tanks[i].drain(maxDrain, doDrain);
				break;
			}
		}
		parent.onTankUpdate();
		return fs;
	}
}