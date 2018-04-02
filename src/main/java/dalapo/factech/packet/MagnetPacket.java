package dalapo.factech.packet;

import javax.annotation.Nullable;

import static dalapo.factech.FactoryTech.DEBUG_PACKETS;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.specialized.TileEntityMagnet;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagnetPacket extends FacTechPacket {

	BlockPos pos;
	short strength;
	short cooldown;
	
	public MagnetPacket(TileEntityMagnet te)
	{
		pos = te.getPos();
		strength = te.getStrength();
		cooldown = te.getCooldown();
	}
	
	public MagnetPacket() {} // Mandatory default constructor is mandatory
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world,	EntityPlayer ep, boolean isClient)
	{
		if (isClient)
		{
			TileEntityMagnet te = (TileEntityMagnet)world.getTileEntity(((MagnetPacket)msg).pos);
			if (te != null) te.setFields(strength, cooldown);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		strength = buf.readShort();
		cooldown = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeShort(strength);
		buf.writeShort(cooldown);
	}

}
