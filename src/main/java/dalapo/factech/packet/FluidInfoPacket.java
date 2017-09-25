package dalapo.factech.packet;

import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dalapo.factech.auxiliary.IHasFluid;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidInfoPacket extends FacTechPacket {

	FluidStack[] fluids;
	BlockPos pos;
	
	public FluidInfoPacket() {}
	
	public FluidInfoPacket(BlockPos pos, FluidTank[] tanks, int num)
	{
		this.pos = pos;
		fluids = new FluidStack[num];
		for (int i=0; i<num; i++)
		{
			fluids[i] = tanks[i].getFluid();
//			if (fluids[i] == null || fluids[i].getFluid() == null) Logger.info(String.format("%s: Null", i));
//			else Logger.info(fluids[i].getFluid().getUnlocalizedName());
		}
	}
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world,	EntityPlayer ep, boolean isClient) {
		if (isClient)
		{
//			Logger.info("FluidInfoPacket actuallyDoHandle");
			IHasFluid te = (IHasFluid)world.getTileEntity(pos);
			for (int i=0; i<((FluidInfoPacket)msg).fluids.length; i++)
			{
				te.overrideTank(((FluidInfoPacket)msg).fluids[i], i);
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		fluids = new FluidStack[buf.readInt()];
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		for (int i=0; i<fluids.length; i++)
		{
			int length = buf.readInt();
			if (length == -1)
			{
				fluids[i] = null;
			}
			else
			{
				byte[] arr = new byte[length];
				for (int j=0; j<length; j++)
				{
					arr[j] = buf.readByte();
				}
				String str = new String(arr);
//				Logger.info(str);
				fluids[i] = new FluidStack(FluidRegistry.getFluid(str), buf.readInt());
//				Logger.info(fluids[i].getFluid());
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(fluids.length);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		for (int i=0; i<fluids.length; i++)
		{
			if (fluids[i] == null)
			{
				buf.writeInt(-1);
			}
			else
			{
				buf.writeInt(fluids[i].getFluid().getName().length());
				buf.writeBytes(fluids[i].getFluid().getName().getBytes());
				buf.writeInt(fluids[i].amount);
			}
		}
	}
}