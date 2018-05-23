package dalapo.factech.tileentity.automation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Spliterator;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.render.tesr.TesrElevator.TESRELEV;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityItemQueue;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityElevator extends TileEntityItemQueue
{
	@Override
	public void cacheTileEntity()
	{
		TileEntity te = world.getTileEntity(pos.up());
		if (te instanceof TileEntityElevator)
		{
			cachedTarget = (TileEntityElevator)te;
		}
	}
	
	@Override
	protected void ejectItem(ItemStack toEject)
	{
		EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, toEject);
		ei.motionX = 0;
		ei.motionY = 0.2;
		ei.motionZ = 0;
		world.spawnEntity(ei);
	}
}