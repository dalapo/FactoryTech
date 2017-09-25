package dalapo.factech.tileentity.automation;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityFluidPuller extends TileEntity implements ITickable {

	private EnumFacing front;
	public TileEntityFluidPuller()
	{
		super();	
	}
	@Override
	public void update() {
		if (world.getBlockState(getPos()).getBlock().equals(Blocks.BEDROCK)) return;
		if (front == null) front = world.getBlockState(pos).getValue(StateList.directions);
		
		TileEntity pulling = world.getTileEntity(FacMathHelper.withOffset(getPos(), front.getOpposite()));
		TileEntity pushing = world.getTileEntity(FacMathHelper.withOffset(getPos(), front));
		if (pulling != null && pulling.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, front) &&
				pushing != null && pushing.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, front.getOpposite()))
		{
			int flow = 100; // 100mB/tick; hardcoding is only temporary
			IFluidHandler pullTank = pulling.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, front);
			IFluidHandler pushTank = pushing.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, front.getOpposite());
			FluidStack toMove = pullTank.drain(flow, false);
			if (toMove == null) return;
			int space = pushTank.fill(toMove.copy(), false);
			if (space < toMove.amount) toMove.amount = space;
			if (space > 0)
			{
				pullTank.drain(space, true);
				pushTank.fill(toMove.copy(), true);
			}
		}
	}

}