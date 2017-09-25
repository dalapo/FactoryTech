package dalapo.factech.packet;

import dalapo.factech.tileentity.TileEntityMachine;

public class PacketFactory {
	
	private PacketFactory() {}
	
	public static PacketFactory instance = new PacketFactory();
	
	public void syncToServer(TileEntityMachine tile)
	{
		PacketHandler.sendToServer(new MachineInfoRequestPacket(tile.getPos()));
	}
}