package dalapo.factech.packet;

import dalapo.factech.tileentity.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineUpgradePacket extends FacTechPacket {

	public BlockPos pos;
	private byte upgrade;
	
	public MachineUpgradePacket(BlockPos pos, byte upgrade)
	{
		this.pos = pos;
		this.upgrade = upgrade;
	}
	
	public MachineUpgradePacket() {}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world,
			EntityPlayer ep, boolean isClient) {
		TileEntityMachine te = (TileEntityMachine)world.getTileEntity(((MachineUpgradePacket)msg).pos);
		te.installUpgrade(upgrade);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		upgrade = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeByte(upgrade);
	}

}
