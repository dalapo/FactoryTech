package dalapo.factech.tileentity.automation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import dalapo.factech.auxiliary.FluidHandlerWrapper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityTank extends TileEntityBase implements ITickable
{
	private FluidTank tank;
	
	public TileEntityTank()
	{
		tank = new FluidTank(24000);
		tank.setCanFill(true);
		tank.setCanDrain(true);
		tank.setTileEntity(this);
	}
	
	public FluidStack getTankContents()
	{
		return tank.getFluid();
	}
	
	public FluidTank getTank()
	{
		return tank;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		return super.getCapability(capability, facing);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (tank.getFluid() == null)
		{
			nbt.setString("name", "[empty]");
			nbt.setInteger("amount", 0);
		}
		else
		{
			nbt.setString("name", FluidRegistry.getFluidName(tank.getFluid()));
			nbt.setInteger("amount", tank.getFluidAmount());
		}
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("name") && nbt.hasKey("amount") && !nbt.getString("name").equals("[empty]"))
		{
			tank.setFluid(new FluidStack(FluidRegistry.getFluid(nbt.getString("name")), nbt.getInteger("amount")));
		}
		else if (nbt.getString("name").equals("[empty]"))
		{
			tank.setFluid(null);
		}
	}

	@Override
	public void update()
	{
		TileEntity te = world.getTileEntity(pos.down());
		if (te instanceof TileEntityTank && world.isBlockIndirectlyGettingPowered(pos) == 0)
		{
			TileEntityTank otherTank = (TileEntityTank)te;
			
			int toFill = FacMathHelper.getMin(24000 - otherTank.tank.getFluidAmount(), 100);
			if (toFill != 0 && tank.getFluidAmount() > 0 && FacStackHelper.canCombineFluids(tank.getFluid(), otherTank.tank.getFluid()))
			{
				otherTank.tank.fill(tank.drain(toFill, true), true);
			}
		}
	}
}