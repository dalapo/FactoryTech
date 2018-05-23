package dalapo.factech.tileentity.automation;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.auxiliary.ChunkLoadRegistry;
import dalapo.factech.auxiliary.ChunkWithDimension;
import dalapo.factech.auxiliary.IChunkLoader;
import dalapo.factech.auxiliary.PosWithDimension;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class TileEntityPlaneShifter extends TileEntityBasicInventory implements IChunkLoader, ITickable
{
	private static final boolean actuallyLoadChunks = true;
	private int dimensionID;
	private boolean loaded;
	private List<ChunkWithDimension> cache = new ArrayList<>();
	private PosWithDimension dimPos;
	
	public TileEntityPlaneShifter()
	{
		super("planeshifter", 9);
	}
	
	public void shiftItems()
	{
		for (int i=0; i<9; i++)
		{
			if (world.getMinecraftServer().getWorld(dimensionID) != null)
			{
				ItemStack is = getStackInSlot(i);
				EntityItem ei = new EntityItem(world,pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, is);
				ei.changeDimension(dimensionID);
				world.spawnEntity(ei);
			}
		}
	}

	private void getDimensionPos()
	{
		if (world instanceof WorldServer)
		{
			Logger.info("Updating dimPos");
			double ratio = world.provider.getMovementFactor() / world.getMinecraftServer().getWorld(dimensionID).provider.getMovementFactor();
			dimPos =  new PosWithDimension(world.getMinecraftServer().getWorld(dimensionID), dimensionID, pos.getX()*ratio, pos.getY()*ratio, pos.getZ()*ratio);
		}
	}
	
	@Override
	public void update()
	{
		if (actuallyLoadChunks && !loaded && !world.isRemote)
		{
			loaded = true;
			getDimensionPos();
			ChunkLoadRegistry.instance.loadChunks(dimPos, getChunksToLoad());
		}
	}
	
	public void changeDimension(int newDim)
	{
		ChunkLoadRegistry.instance.unloadChunks(dimPos);
		getDimensionPos();
		dimensionID = newDim;
		loaded = false;
	}
	
	@Override
	public void invalidate()
	{
		ChunkLoadRegistry.instance.unloadChunks(new PosWithDimension(this));
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public List<ChunkWithDimension> getChunksToLoad()
	{
		Logger.info(dimPos == null);
		if (dimPos == null) return new ArrayList<>();
		if (cache.isEmpty())
		{
			double thisMovement = world.provider.getMovementFactor();
			double otherMovement = world.getMinecraftServer().getWorld(dimensionID).provider.getMovementFactor();
			double factor = thisMovement / otherMovement;
			Chunk chunk = world.getChunkFromBlockCoords(dimPos);
			cache.add(new ChunkWithDimension(dimensionID, chunk.x, chunk.z));
		}
		return cache;
	}

	public int getDimension()
	{
		return dimensionID;
	}
}