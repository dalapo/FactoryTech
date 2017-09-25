package dalapo.factech.packet;

import dalapo.factech.auxiliary.Wrenchable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchPacket extends FacTechPacket
{
	BlockPos pos;
	EnumFacing newDirection;
	
	public WrenchPacket(BlockPos posIn, EnumFacing directionIn)
	{
		pos = posIn;
		newDirection = directionIn;
	}
	
	public WrenchPacket() {}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient) {
		Wrenchable te = (Wrenchable)world.getBlockState(((WrenchPacket)msg).pos).getBlock();
		te.onWrenched(ep.isSneaking(), world, pos, newDirection);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = BlockPos.fromLong(buf.readLong());
		newDirection = EnumFacing.getFront(buf.readByte());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(pos.toLong());
		buf.writeByte((byte)newDirection.getIndex());
	}

}