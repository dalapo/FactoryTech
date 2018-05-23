package dalapo.factech.auxiliary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChunkLoadRegistry implements LoadingCallback
{
	public static final ChunkLoadRegistry instance = new ChunkLoadRegistry();
	public static final Map<PosWithDimension, Ticket> ticketMap = new HashMap<>();
	private ChunkLoadRegistry() {}
	
	public void init()
	{
		ForgeChunkManager.setForcedChunkLoadingCallback(FactoryTech.instance, this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void unloadWorld(WorldEvent.Unload evt)
	{
		Logger.info("Unloading world " + evt.getWorld().provider.getDimension());
		Iterator<PosWithDimension> iterator = ticketMap.keySet().iterator();
		
		while (iterator.hasNext())
		{
			PosWithDimension pos = iterator.next();
			if (pos.getDimension() == evt.getWorld().provider.getDimension())
			{
				iterator.remove();
			}
		}
	}
	
	// Code taken from Reika's DragonAPI
	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world)
	{
		Logger.info("Entered ticketsLoaded");
		for (Ticket ticket : tickets)
		{
			NBTTagCompound nbt = ticket.getModData();
			int x = nbt.getInteger("tileX");
			int y = nbt.getInteger("tileY");
			int z = nbt.getInteger("tileZ");
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te instanceof IChunkLoader)
			{
				IChunkLoader clte = (IChunkLoader)te;
				PosWithDimension pos = new PosWithDimension(te);
				forceChunks(clte.getChunksToLoad(), ticket);
				ticketMap.put(pos, ticket);
			}
			else
			{
				ForgeChunkManager.releaseTicket(ticket);
			}
		}
	}
	
	public Ticket loadChunks(PosWithDimension pos, List<ChunkWithDimension> chunks)
	{
		Logger.info("Entered loadChunks with pos " + pos);
		Ticket ticket = ticketMap.get(pos);
		if (ticket == null)
		{
			ticket = ForgeChunkManager.requestTicket(FactoryTech.instance, pos.getWorld(), Type.NORMAL);
			NBTTagCompound nbt = ticket.getModData();
			nbt.setInteger("tileX", pos.getX());
			nbt.setInteger("tileY", pos.getY());
			nbt.setInteger("tileZ", pos.getZ());
			ticketMap.put(pos, ticket);
		}
		this.forceChunks(chunks, ticket);
		return ticket;
	}
	
	public void unloadChunks(PosWithDimension pos)
	{
		Ticket ticket = ticketMap.remove(pos);
		if (ticket == null)
		{
			Logger.warn("Null ticket. Full ticket list: " + ticketMap.toString());
		}
		Logger.info("Unloading chunks " + ticket.getChunkList());
		ForgeChunkManager.releaseTicket(ticket);
	}
	
	public void forceChunks(List<ChunkWithDimension> chunks, Ticket ticket)
	{
		Logger.info("Entered forceChunks: " + chunks);
		ImmutableSet<ChunkPos> toLoad = ticket.getChunkList();
		
		for (ChunkPos coord : toLoad)
		{
			if (!chunks.contains(coord))
			{
				Logger.info("Unforcing chunk " + coord);
				ForgeChunkManager.unforceChunk(ticket, coord);
			}
		}
		for (ChunkPos coord : chunks)
		{
			if (!toLoad.contains(coord))
			{
				Logger.info("Forcing chunk " + coord);
				ForgeChunkManager.forceChunk(ticket, coord);
			}
		}
	}
}