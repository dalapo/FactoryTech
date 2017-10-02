package dalapo.factech.packet;

import dalapo.factech.helper.Logger;
import static dalapo.factech.FactoryTech.DEBUG_PACKETS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int packetID = 0;
	public static SimpleNetworkWrapper INSTANCE;
	
	public static int nextID()
	{
		return packetID++;
	}
	
	public static void registerMessages(String channelName)
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages()
	{
		registerMessage(MachineInfoRequestPacket.Handler.class, MachineInfoRequestPacket.class, Side.SERVER);
//		registerMessage(MachineInfoPacket.Handler.class, MachineInfoPacket.class, Side.CLIENT);
		registerMessage(FluidInfoPacket.Handler.class, FluidInfoPacket.class, Side.CLIENT);
		registerMessage(ItemRedisPacket.Handler.class, ItemRedisPacket.class, Side.SERVER);
		registerMessage(ItemRedisPacket.Handler.class, ItemRedisPacket.class, Side.CLIENT);
		registerMessage(MagnetPacket.Handler.class, MagnetPacket.class, Side.CLIENT);
		registerMessage(StabilizerPacket.Handler.class, StabilizerPacket.class, Side.CLIENT);
		registerMessage(CircuitScribePacket.Handler.class, CircuitScribePacket.class, Side.SERVER);
		registerMessage(WrenchPacket.Handler.class, WrenchPacket.class, Side.SERVER);
		// Register all packets here
		// registerMessage(PacketClass.Handler.class, PacketClass.class, Side.<SERVER, CLIENT>);
	}
	
	public static void registerMessage(Class handlerClass, Class messageClass, Side side)
	{
		INSTANCE.registerMessage(handlerClass, messageClass, nextID(), side);
	}
	
	public static void sendToServer(FacTechPacket packet)
	{
		if (DEBUG_PACKETS)
		{
			Logger.info(String.format("Sending %s to the server", packet.getClass().getName()));
			Thread.dumpStack();
		}
		PacketHandler.INSTANCE.sendToServer(packet);
	}
	
	public static void sendToAll(FacTechPacket packet)
	{
		if (DEBUG_PACKETS)
		{
			Logger.info(String.format("Sending %s to everyone", packet.getClass().getName()));
			Thread.dumpStack();
		}
		INSTANCE.sendToAll(packet);
	}
	
	public static void sendToPlayer(FacTechPacket packet, EntityPlayer ep)
	{
		if (DEBUG_PACKETS)
		{
			Logger.info("Sending " + packet.getClass().getName() + " to " + ep.getName());
			Thread.dumpStack();
		}
		PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)ep);
	}
}