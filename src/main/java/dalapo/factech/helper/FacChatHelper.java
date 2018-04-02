package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class FacChatHelper {
	private FacChatHelper() {}
	
	public static void sendMessage(String msg)
	{
		if (FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT))
		{
			if (Minecraft.getMinecraft().player != null)
			{
				Minecraft.getMinecraft().player.sendMessage(new TextComponentString(msg));
//				sendChatToPlayer(Minecraft.getMinecraft().thePlayer, msg);
			}
		}
	}
	
	public static void sendCoords(String label, int x, int y, int z)
	{
		String str = label + String.format("(%s, %s, %s)", x, y, z);
		sendMessage(str);
	}
	
	public static void sendCoords(String label, BlockPos pos)
	{
		sendCoords(label, pos.getX(), pos.getY(), pos.getZ());
	}
	
	public static void sendChatToPlayer(EntityPlayer ep, String msg)
	{
		String[] parts = msg.split("\\n");
		for (int i=0; i<parts.length; i++)
		{
			ITextComponent chatPart = new TextComponentString(parts[i]);
			ep.sendMessage(chatPart);
		}
	}
	
	public static void sendStatusMessage(EntityPlayer ep, String msg, boolean actionBar)
	{
		String[] parts = msg.split("\\n");
		for (int i=0; i<parts.length; i++)
		{
			ITextComponent statusPart = new TextComponentString(parts[i]);
			ep.sendStatusMessage(statusPart, actionBar);
		}
	}
}
