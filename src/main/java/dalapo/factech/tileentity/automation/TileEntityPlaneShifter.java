package dalapo.factech.tileentity.automation;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.auxiliary.ChunkLoadRegistry;
import dalapo.factech.auxiliary.ChunkWithDimension;
import dalapo.factech.auxiliary.IChunkLoader;
import dalapo.factech.auxiliary.PosWithDimension;
import dalapo.factech.helper.FacEntityHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class TileEntityPlaneShifter extends TileEntityBasicInventory implements IChunkLoader, ITickable, ActionOnRedstone
{
	private static final boolean actuallyLoadChunks = true;
	private int dimensionID;
	private boolean loaded;
	private boolean isPowered;
	private List<ChunkWithDimension> cache = new ArrayList<>();
	private PosWithDimension dimPos;
	
	public TileEntityPlaneShifter()
	{
		super("planeshifter", 9);
		setDisplayName("Plane Shifter");
	}
	
	public void shiftItems()
	{
		World dimension = world.getMinecraftServer().getWorld(dimensionID);
		if (dimension != null)
		{
			for (int i=0; i<9; i++)
			{
				ItemStack is = removeStackFromSlot(i);
				EntityItem ei = new EntityItem(dimension, dimPos.getX()+0.5, dimPos.getY()+0.5, dimPos.getZ()+0.5, is);
				FacEntityHelper.stopEntity(ei);
				dimension.spawnEntity(ei);
			}
		}
	}

	private void getDimensionPos()
	{
		if (world instanceof WorldServer)
		{		
			double ratio = world.provider.getMovementFactor() / world.getMinecraftServer().getWorld(dimensionID).provider.getMovementFactor();
			dimPos =  new PosWithDimension(world.getMinecraftServer().getWorld(dimensionID), dimensionID, pos.getX()*ratio, pos.getY(), pos.getZ()*ratio);
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
	
	@Override
	public void invalidate()
	{
		ChunkLoadRegistry.instance.unloadChunks(dimPos);
	}
	
	public void changeDimension(int newDim)
	{
		ChunkLoadRegistry.instance.unloadChunks(dimPos);
		dimensionID = newDim;
		getDimensionPos();
		loaded = false;
	}
	
//	@Override
//	public void invalidate()
//	{
//		if (!world.isRemote) ChunkLoadRegistry.instance.unloadChunks(new PosWithDimension(this));
//	}
	
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

	@Override
	public void onRedstoneSignal(boolean isSignal, EnumFacing side)
	{
		if (world.isBlockPowered(pos))
		{
			if (!isPowered && isSignal)
			{
				isPowered = true;
				shiftItems();
			}
		}
		else if (!isSignal) isPowered = false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dimension", dimensionID);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		dimensionID = nbt.getInteger("dimension");
	}
}