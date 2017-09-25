package dalapo.factech.packet;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.specialized.TileEntityCircuitScribe;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CircuitScribePacket extends FacTechPacket {

	BlockPos pos;
	byte pattern;
	
	public CircuitScribePacket(TileEntityCircuitScribe te)
	{
		pos = te.getPos();
		pattern = (byte)te.getPattern();
	}
	
	public CircuitScribePacket() {}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world,
			EntityPlayer ep, boolean isClient) {
		Logger.info("CircuitScribePacket actuallyDoHandle");
		TileEntityCircuitScribe te = (TileEntityCircuitScribe)world.getTileEntity(((CircuitScribePacket)msg).pos);
		te.setPattern(((CircuitScribePacket)msg).pattern);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		pattern = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeByte(pattern);
	}
}