package dalapo.factech.packet;

import dalapo.factech.auxiliary.IInfoPacket;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineInfoRequestPacket extends FacTechPacket {
	BlockPos pos;
	
	public MachineInfoRequestPacket() {}
	public MachineInfoRequestPacket(int x, int y, int z)
	{
		pos = new BlockPos(x, y, z);
	}
	
	public MachineInfoRequestPacket(BlockPos bp)
	{
		pos = bp;
	}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient)
	{
//		System.out.println(String.format("MachineInfoRequestPacket actuallyDoHandle Thread: %s", Thread.currentThread()));
		if (!isClient)
		{
			IInfoPacket te = (IInfoPacket)world.getTileEntity(((MachineInfoRequestPacket)msg).pos);
			te.sendInfoPacket(ep);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
}