package dalapo.factech.tileentity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import dalapo.factech.render.tesr.TesrElevator.TESRELEV;
import dalapo.factech.tileentity.automation.TileEntityElevator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityItemQueue extends TileEntityBase implements ITickable
{
	protected static int CAPACITY = 20;
	protected static boolean canItemsBePushed = true;
	
	private int ticks = 0;
	
	protected LinkedList<ItemStack> stacks = new LinkedList<ItemStack>(); // But not a stack of stacks!
	protected LinkedList<ItemStack> scheduled = new LinkedList<ItemStack>();
	protected TileEntityItemQueue cachedTarget;
	@SideOnly(Side.CLIENT)
	private ItemStack legacy = ItemStack.EMPTY;
	
	protected abstract void cacheTileEntity();
	protected abstract void ejectItem(ItemStack toEject);
	
	public TileEntityItemQueue()
	{
		for (int i=0; i<CAPACITY; i++)
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
		cacheTileEntity();
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
			if (cachedTarget != null)
			{
				cachedTarget.scheduleItemStack(toEject);
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
	public int getCapacity()
	{
		return CAPACITY;
	}
}