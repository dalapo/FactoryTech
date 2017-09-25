package dalapo.factech.tileentity.automation;

import static dalapo.factech.FactoryTech.DEBUG_PACKETS;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.auxiliary.IHasFluid;
import dalapo.factech.auxiliary.IInfoPacket;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.packet.FluidInfoPacket;
import dalapo.factech.packet.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;

public class TileEntityPipe extends TileFluidHandler implements ITickable, IInfoPacket, IHasFluid {
	int flow;
	private EnumFacing prevFilled;
	
	public TileEntityPipe(int flow, int cap)
	{
		super();
		this.flow = flow;
		tank.setCapacity(cap);
	}
	
	public TileEntityPipe()
	{
		this(100, 1000);
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		if (DEBUG_PACKETS)
		{
		Logger.info("Entered getUpdateTag()");
		Thread.dumpStack();
		}
		NBTTagCompound nbt = super.getUpdateTag();
		return writeToNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		if (DEBUG_PACKETS)
		{
		Logger.info(String.format("Entered getUpdatePacket; thread = %s", Thread.currentThread()));
		Thread.dumpStack();
		}
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
	
	public void propagateFluid()
	{
		if (this.tank.getFluidAmount() == 0) return;
		int numConnected = 0;
		for (EnumFacing f : EnumFacing.VALUES)
		{
			if (f == prevFilled) continue;
			TileEntity te = world.getTileEntity(FacMathHelper.withOffset(getPos(), f));
			if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite())) numConnected++;
		}
		
		for (EnumFacing f : EnumFacing.VALUES)
		{
			if (f == prevFilled) continue;
			if (this.tank.getFluidAmount() == 0) break; // Temporary fix
			TileEntity te = world.getTileEntity(FacMathHelper.withOffset(getPos(), f));
			if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
			{
				IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite());
				FluidStack toTransfer = this.tank.drain(flow / numConnected, true); // Danger: Possible divide by zero
				int filled = tank.fill(toTransfer, true);
				if (toTransfer != null && filled < toTransfer.amount) this.tank.fill(new FluidStack(toTransfer.getFluid(), toTransfer.amount - filled), true);
				if (te instanceof TileEntityPipe)
				{
					((TileEntityPipe)te).prevFilled = f.getOpposite();
				}
				te.markDirty();
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		tank.writeToNBT(nbt);
		nbt.setInteger("flow", flow);
		if (prevFilled == null)
		{
			nbt.setInteger("direction", -1);
		}
		else
		{
			nbt.setInteger("direction", prevFilled.ordinal());
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt);
		flow = nbt.getInteger("flow");
		int i = nbt.getInteger("direction");
		if (i == -1) prevFilled = null;
		else prevFilled = EnumFacing.getFront(i);
	}
	
	@Override
	public void update() {
		propagateFluid();
	}

	@Override
	public void sendInfoPacket(EntityPlayer ep) {
		PacketHandler.sendToPlayer(new FluidInfoPacket(getPos(), new FluidTank[] {this.tank}, 1), (EntityPlayerMP)ep);
	}

	@Override
	public void overrideTank(FluidStack fs, int tank) {
		this.tank.setFluid(fs);
	}
}