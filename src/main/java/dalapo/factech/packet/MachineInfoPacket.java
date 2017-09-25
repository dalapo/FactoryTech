package dalapo.factech.packet;

import java.util.ArrayList;
import java.util.List;

import static dalapo.factech.FactoryTech.DEBUG_PACKETS;
import dalapo.factech.auxiliary.FluidHandlerWrapper;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class MachineInfoPacket extends FacTechPacket {
	BlockPos pos;
	int age;
	ArrayList<Boolean> parts = new ArrayList<Boolean>();
	
	public MachineInfoPacket() {}
	
	public MachineInfoPacket(TileEntityMachine te)
	{
		handler = new Handler();
		pos = te.getPos();
		age = te.age;
		for (int i=0; i<te.countPartSlots(); i++)
		{
			this.parts.add(te.hasPart(i));
		}
	}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient) {
		if (DEBUG_PACKETS) System.out.println(String.format("actuallyDoHandle Thread: %s", Thread.currentThread()));
		if (isClient)
		{
			TileEntityMachine teClient = (TileEntityMachine)world.getTileEntity(((MachineInfoPacket)msg).pos);
			teClient.age = ((MachineInfoPacket)msg).age;
			boolean[] b = new boolean[teClient.countPartSlots()];
			for (int i=0; i<b.length; i++)
			{
				b[i] = ((MachineInfoPacket)msg).parts.get(i);
			}
			teClient.copyParts(b);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		age = buf.readInt();
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		while (buf.isReadable())
		{
			parts.add(buf.readBoolean());
		}
	}
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(age);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		for (int i=0; i<parts.size(); i++)
		{
			buf.writeBoolean(parts.get(i));
		}
	}
}