package dalapo.factech.packet;

import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.automation.TileEntityInventorySensor;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SwitchTogglePacket extends FacTechPacket
{
	private BlockPos pos;
	private byte id;
	
	public SwitchTogglePacket(BlockPos pos, byte id)
	{
		this.pos = pos;
		this.id = id;
	}
	
	public SwitchTogglePacket() {}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient)
	{
		SwitchTogglePacket packet = (SwitchTogglePacket)msg;
		TileEntityBasicInventory inv = (TileEntityBasicInventory)world.getTileEntity(packet.pos);
		inv.toggleField(packet.id);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = BlockPos.fromLong(buf.readLong());
		id = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(pos.toLong());
		buf.writeByte(id);
	}

}