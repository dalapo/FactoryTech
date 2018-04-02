package dalapo.factech.packet;

import dalapo.factech.auxiliary.IHasFluid;
import dalapo.factech.tileentity.TileEntityBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidTankEmptyPacket extends FacTechPacket
{
	private BlockPos pos;
	private int tankID;
	
	public FluidTankEmptyPacket() {}
	
	public FluidTankEmptyPacket(TileEntityBase te, int tankID)
	{
		if (!(te instanceof IHasFluid)) throw new IllegalArgumentException("Attempted to empty a tank that doesn't exist!");
		pos = te.getPos();
		this.tankID = tankID;
	}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient)
	{
		FluidTankEmptyPacket packet = (FluidTankEmptyPacket)msg;
		IHasFluid te = (IHasFluid)world.getTileEntity(packet.pos);
		te.getTank(packet.tankID).setFluid(null);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		tankID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(tankID);
	}

}