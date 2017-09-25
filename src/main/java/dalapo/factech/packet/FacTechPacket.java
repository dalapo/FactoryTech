package dalapo.factech.packet;

import dalapo.factech.auxiliary.IFluidModel;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class FacTechPacket implements IMessage {

	protected World world;
	protected EntityPlayer player;
	
	protected Handler handler = new Handler();
	
	public FacTechPacket() {}
	
	protected abstract void actuallyDoHandle(FacTechPacket msg, World world, EntityPlayer ep, boolean isClient);
	@Override
	public abstract void fromBytes(ByteBuf buf);

	@Override
	public abstract void toBytes(ByteBuf buf);
	
	@SideOnly(Side.CLIENT)
	private final void doHandle(NetHandlerPlayClient nhpc)
	{
//		System.out.println(String.format("Client doHandle Thread: %s", Thread.currentThread()));
		actuallyDoHandle(this, (WorldClient)(Minecraft.getMinecraft().world), Minecraft.getMinecraft().player, true);
	}
	
	private final void doHandle(NetHandlerPlayServer nhps)
	{
//		System.out.println(String.format("Server doHandle Thread: %s", Thread.currentThread()));
		actuallyDoHandle(this, nhps.player.getEntityWorld(), nhps.player, false);
	}

	public static class Handler implements IMessageHandler<FacTechPacket, IMessage>
	{
		@Override
		public IMessage onMessage(final FacTechPacket message, final MessageContext ctx) {
			// Magic!
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (ctx.side.equals(Side.CLIENT)) message.doHandle(ctx.getClientHandler());
					else message.doHandle(ctx.getServerHandler());
				}
			});
			return null;
		}
	}
}