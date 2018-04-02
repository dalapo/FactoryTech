package dalapo.factech.tileentity.automation;

import javax.annotation.Nonnull;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityBufferCrate extends TileEntityCrate
{
	private static final int SIZE = 18;
	
//	private final BufferItemHandler storage = new BufferItemHandler(SIZE);
	
	public TileEntityBufferCrate()
	{
		super();
		setDisplayName("Buffer Crate");
//		for (int i=0; i<SIZE; i++)
//		{
//			storage.filter[i] = ItemStack.EMPTY;
//		}
	}
	
//	public void setFilter(int slot, ItemStack is)
//	{
//		storage.filter[slot] = is.copy();
//		if (storage.getStackInSlot(slot).getCount() > 0)
//		{
//			EntityItem ei = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), is);
//			world.spawnEntity(ei);
//			storage.setStackInSlot(0, ItemStack.EMPTY);
//		}
//	}
	
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	// See, this is why I shouldn't be writing code past midnight.
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new InvWrapper(this) {
				
				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate)
				{
					if (amount == 0)
			            return ItemStack.EMPTY;

			        ItemStack stackInSlot = getInv().getStackInSlot(slot);

			        if (stackInSlot.getCount() <= 1)
			            return ItemStack.EMPTY;

			        if (simulate)
			        {
			            if (stackInSlot.getCount()-1 < amount)
			            {
			            	ItemStack toReturn = stackInSlot.copy();
			            	toReturn.shrink(1);
			                return toReturn;
			            }
			            else
			            {
			                ItemStack toReturn = stackInSlot.copy();
			                toReturn.setCount(amount);
			                return toReturn;
			            }
			        }
			        else
			        {
			            int m = Math.min(stackInSlot.getCount()-1, amount);

			            ItemStack decrStackSize = getInv().decrStackSize(slot, m);
			            getInv().markDirty();
			            return decrStackSize;
			        }
				}
				
				public void onContentsChanged(int slot)
        		{
        			onInventoryChanged(slot);
        			markDirty();
        		}
        	});
		}
		return null;
	}
	
	/*
	private static class BufferItemHandler extends ItemStackHandler
	{
		private final ItemStack[] filter = new ItemStack[SIZE];
		
		public BufferItemHandler(int size)
		{
			super(size); // me
		}
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			if (amount == 0)
	            return ItemStack.EMPTY;

	        validateSlotIndex(slot);

	        ItemStack existing = this.stacks.get(slot);

	        if (existing.getCount() <= 1)
	            return ItemStack.EMPTY;

	        int toExtract = Math.min(amount, existing.getMaxStackSize());

	        if (existing.getCount() - 1 <= toExtract)
	        {
	            if (!simulate)
	            {
	                this.stacks.get(slot).setCount(1);
	                onContentsChanged(slot);
	            }
	            existing.shrink(1);
	            return existing;
	        }
	        else
	        {
	            if (!simulate)
	            {
	                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
	                onContentsChanged(slot);
	            }
	            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
	        }
		}
		
		@Override
		public ItemStack getStackInSlot(int slot)
		{
			validateSlotIndex(slot);
			if (stacks.get(slot).isEmpty())
			{
				ItemStack empty = filter[slot].copy();
				ReflectionHelper.setPrivateValue(ItemStack.class, empty, 0, 2);
				return empty;
			}
			else return stacks.get(slot);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack is, boolean simulate)
		{
			if (is.isEmpty()) return ItemStack.EMPTY;
			
			validateSlotIndex(slot);
			
			ItemStack existing = getStackInSlot(slot);
			if (existing == ItemStack.EMPTY) // Check for an actual empty ItemStack
			{
				if (!simulate)
				{
					stacks.set(slot, is);
					filter[slot] = is.copy(); // Stack size is arbitrary
				}
				return ItemStack.EMPTY;
			}
			else if (is.isItemEqual(existing))
			{
				if (stacks.get(slot).isEmpty())
				{
					if (!simulate)
					{
						stacks.set(slot, is.copy());
					}
					return ItemStack.EMPTY;
				}
				else
				{
					int limit = is.getMaxStackSize();
					if (is.getCount() + stacks.get(slot).getCount() <= limit)
					{
						if (!simulate) stacks.get(slot).grow(is.getCount());
						return ItemStack.EMPTY;
					}
					else
					{
						if (!simulate) stacks.get(slot).setCount(limit);
						is.setCount(is.getCount() + existing.getCount() - limit);
						return is;
					}
				}
			}
			return is;
		}
	}
	*/

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}