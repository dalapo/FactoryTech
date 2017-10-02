package dalapo.factech.tileentity;

import dalapo.factech.auxiliary.FluidHandlerWrapper;
import dalapo.factech.auxiliary.IHasFluid;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.FluidInfoPacket;
import dalapo.factech.packet.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public abstract class TileEntityFluidMachine extends TileEntityMachine implements IHasFluid {

	protected FluidTank[] tanks;
	private int cutoff;
	public TileEntityFluidMachine(String name, int inSlots, int partSlots, int outSlots, int numTanks, int cutoff, RelativeSide partHatch) {
		super(name, inSlots, partSlots, outSlots, partHatch);
		tanks = new FluidTank[numTanks];
		this.cutoff = cutoff;
		for (int i=0; i<tanks.length; i++)
		{
			tanks[i] = new FluidTank(10000);
		}
	}
	
	@Override
	public void showChatInfo(EntityPlayer ep)
	{
		String str;
		for (int i=0; i<tanks.length; i++)
		{
			str = "Tank " + (i+1) + ": ";
			if (tanks[0].getFluid() == null) str += "Empty";
			else
			{
				str += tanks[0].getFluid().getLocalizedName() + ", " + tanks[0].getFluidAmount();
			}
			FacChatHelper.sendChatToPlayer(ep, str);
		}
		super.showChatInfo(ep);
	}
	
	@Override
	public void overrideTank(FluidStack fluid, int id)
	{
		if (FacMathHelper.isInRange(id, 0, tanks.length))
		{
			tanks[id].setFluid(fluid);
		}
	}
	
	
	
	public FluidStack getTankContents(int tank)
	{
		if (FacMathHelper.isInRange(tank, 0, tanks.length))
		{
			return tanks[tank].getFluid();
		}
		return null;
	}
	
	public FluidTank getTank(int i)
	{
		return tanks[i];
	}
	
	/*
	@Override
	public void sendInfoPacket(EntityPlayer ep)
	{
		super.sendInfoPacket(ep);
//		Logger.info("Sent regular info packet; now sending fluid info packet");
		FluidInfoPacket packet = new FluidInfoPacket(getPos(), tanks, tanks.length);
		PacketHandler.sendToPlayer(packet, (EntityPlayerMP)ep);
	}
	*/
	
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
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new FluidHandlerWrapper(tanks, cutoff, this));
		}
		return super.getCapability(capability, facing);
	}
	
	public void onTankUpdate()
	{
		FacBlockHelper.updateBlock(world, pos);
		getHasWork();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList tankList = new NBTTagList();
		for (FluidTank ft : tanks)
		{
			NBTTagCompound tankInfo = new NBTTagCompound();
			tankInfo.setString("name", ft.getFluid() == null ? "[empty]" : ft.getFluid().getFluid().getName());
			tankInfo.setInteger("amount", ft.getFluidAmount());
			tankList.appendTag(tankInfo);
		}
		nbt.setTag("tanks", tankList);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("tanks"))
		{
			NBTTagList tankList = nbt.getTagList("tanks", 10);
			for (int i=0; i<tankList.tagCount(); i++)
			{
				NBTTagCompound tankInfo = tankList.getCompoundTagAt(i);
				if (tankInfo.getString("name").equals("[empty]")) continue;
				tanks[i].setFluid(new FluidStack(FluidRegistry.getFluid(tankInfo.getString("name")), tankInfo.getInteger("amount")));
			}
		}
	}
}