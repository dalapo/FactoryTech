package dalapo.factech.packet;

import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRedisPacket extends FacTechPacket {

	BlockPos pos;
	int[] vals = new int[5];
	int side;
	int change;
	boolean toggle;
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		for (int i=0; i<5; i++)
		{
			vals[i] = buf.readInt();
		}
		side = buf.readInt();
		change = buf.readInt();
		toggle = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		for (int i : vals)
		{
			buf.writeInt(i);
		}
		buf.writeInt(side);
		buf.writeInt(change);
		buf.writeBoolean(toggle);
	}
	
	public ItemRedisPacket (TileEntityItemRedis te, int s, int c, boolean toggle)
	{
		handler = new Handler();
		pos = te.getPos();
		side = s;
		change = c;
		this.toggle = toggle;
	}
	
	public ItemRedisPacket ()
	{
		// Apparently this is a thing that you need
	}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket message, World world, EntityPlayer ep, boolean isToClient)
	{
		if (!isToClient)
		{
			TileEntityItemRedis te = (TileEntityItemRedis)world.getTileEntity(((ItemRedisPacket)message).pos);
			if (!(te instanceof TileEntityItemRedis)) throw new RuntimeException("Minecraft updates broke the server!");
			((TileEntityItemRedis)te).changeRatio(((ItemRedisPacket)message).side, ((ItemRedisPacket)message).change);
			if (((ItemRedisPacket)message).toggle) te.toggleSplit(); // Server-side
			te.markDirty();
			for (int i=0; i<5; i++)
			{
				vals[i] = te.getRatio(i);
			}
			((ItemRedisPacket)message).toggle = te.shouldSplit();
			PacketHandler.sendToPlayer(message, (EntityPlayerMP)ep);
		}
		else
		{
			TileEntityItemRedis te = (TileEntityItemRedis)world.getTileEntity(((ItemRedisPacket)message).pos);
			if (!(te instanceof TileEntityItemRedis)) throw new RuntimeException("Minecraft updates broke the client!");
			for (int i=0; i<5; i++)
			{
				te.setRatio(i, vals[i]);

				Logger.info(vals[i]);
			}
			if (((ItemRedisPacket)message).change == 0) FacChatHelper.sendMessage(toggle ? "Now splitting stacks" : "Now keeping stacks together");
			te.markDirty();
		}
	}
	
	public int getID()
	{
		return 1;
	}
}