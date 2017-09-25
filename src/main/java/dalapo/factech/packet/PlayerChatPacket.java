package dalapo.factech.packet;

import dalapo.factech.helper.FacChatHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PlayerChatPacket extends FacTechPacket {

	String message;
	
	public PlayerChatPacket(String msg)
	{
		message = msg;
	}
	public PlayerChatPacket()
	{
		message = "";
	}
	
	@Override
	protected void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient) {
		FacChatHelper.sendChatToPlayer(ep, ((PlayerChatPacket)msg).message);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readInt()];
		for (int i=0; i<bytes.length; i++)
		{
			bytes[i] = buf.readByte();
		}
		message = new String(bytes);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(message.length());
		buf.writeBytes(message.getBytes());
	}

}
