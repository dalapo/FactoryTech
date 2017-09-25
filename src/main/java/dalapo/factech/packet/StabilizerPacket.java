package dalapo.factech.packet;

import dalapo.factech.tileentity.specialized.TileEntityStabilizer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StabilizerPacket extends FacTechPacket {

	BlockPos pos;
	short stability;
	short[] strength = new short[4];
	
	public StabilizerPacket(TileEntityStabilizer te)
	{
		pos = te.getPos();
		stability = te.getStability();
		for (int i=0; i<4; i++)
		{
			strength[i] = te.getStrength(i);
		}
	}
	
	public StabilizerPacket() {}
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient) {
		TileEntity te = world.getTileEntity(((StabilizerPacket)msg).pos);
		if (te instanceof TileEntityStabilizer)
		{
			((TileEntityStabilizer)te).updateValues(((StabilizerPacket)msg).stability, ((StabilizerPacket)msg).strength);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		stability = buf.readShort();
		for (int i=0; i<4; i++)
		{
			strength[i] = buf.readShort();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeShort(stability);
		for (int i=0; i<4; i++)
		{
			buf.writeShort(strength[i]);
		}
	}

}
