package dalapo.factech.tileentity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.render.tesr.TesrElevator.TESRELEV;
import dalapo.factech.tileentity.automation.TileEntityElevator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityItemQueue extends TileEntityBase implements ITickable
{
	protected static boolean canItemsBePushed = true;
	
	private int ticks = 0;
	
	protected LinkedList<ItemStack> stacks = new LinkedList<ItemStack>(); // But not a stack of stacks!
	protected LinkedList<ItemStack> scheduled = new LinkedList<ItemStack>();
	private BlockPos targetPos;
	@SideOnly(Side.CLIENT)
	private ItemStack legacy = ItemStack.EMPTY;
	
	public abstract BlockPos getTarget();
	protected abstract void ejectItem(ItemStack toEject);
	
	public TileEntityItemQueue()
	{
		for (int i=0; i<getCapacity(); i++)
		{
			stacks.addFirst(ItemStack.EMPTY);
		}
	}
	
	public boolean allowPush()
	{
		return canItemsBePushed;
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		targetPos = getTarget();
	}
	
	public LinkedList<ItemStack> getStacks(TESRELEV auth)
	{
		return stacks;
	}
	
	public ItemStack yank(int index)
	{
		ItemStack is = stacks.get(index).copy();
		stacks.set(index, ItemStack.EMPTY);
		return is;
	}
	
	public ItemStack peek(int index)
	{
		return stacks.get(index).copy(); // no editing for you
	}
	
	@SideOnly(Side.CLIENT)
	public ItemStack getLegacy()
	{
		return legacy;
	}
	
	public void scheduleItemStack(ItemStack itemstack)
	{
		Logger.info(pos + ": scheduling ItemStack " + itemstack);
		scheduled.add(itemstack);
	}
	
	@Override
	public void update()
	{
		if (scheduled.isEmpty()) stacks.add(ItemStack.EMPTY);
		else stacks.add(scheduled.remove());
	
		ItemStack toEject = stacks.remove();
		if (!toEject.isEmpty())
		{
			TileEntity te = world.getTileEntity(targetPos);
			if (te instanceof TileEntityItemQueue)
			{
				((TileEntityItemQueue)te).scheduleItemStack(toEject);
				FacBlockHelper.updateBlock(world, pos);
				FacBlockHelper.updateBlock(world, targetPos);
			}
			else if (!world.isRemote)
			{
				ejectItem(toEject);
			}
		}
		if (world.isRemote) legacy = toEject; // .copy()?
	}
	

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("ticks", ticks);
		NBTTagList list = new NBTTagList();
		for (ItemStack is : stacks)
		{
			list.appendTag(is.serializeNBT());
		}
		nbt.setTag("items", list);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		ticks = nbt.getInteger("ticks");
		NBTTagList list = nbt.getTagList("items", 10);
		for (NBTBase tag : list)
		{
			if (!(tag instanceof NBTTagCompound))
			{
				throw new RuntimeException("Somehow a non-itemstack has been stored in this list. Somebody should probably be fired over this.");
			}
			stacks.remove();
			stacks.add(new ItemStack((NBTTagCompound)tag));
		}
	}
	
	@Override
	public void invalidate()
	{
		for (ItemStack is : stacks)
		{
			if (!world.isRemote && !is.isEmpty()) world.spawnEntity(new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, is));
		}
		super.invalidate();
	}
	
	public int getCapacity()
	{
		return 20;
	}
}