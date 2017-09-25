package dalapo.factech.tileentity;

import javax.annotation.Nonnull;

import dalapo.factech.auxiliary.IInfoPacket;
import dalapo.factech.helper.FacMathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityBasicInventory extends TileEntityBase implements IInventory, IInfoPacket {

	protected String name;
	protected String displayName;
	private ItemStack[] inventory;
	public TileEntityBasicInventory(String name, int slots)
	{
		super();
		this.name = name;
		displayName = name;
		inventory = new ItemStack[slots];
		for (int i=0; i<slots; i++)
		{
			inventory[i] = ItemStack.EMPTY;
		}
	}
	
	protected void setDisplayName(String str)
	{
		displayName = str;
	}
	
	// N.B. This method should be overridden if necessary
	protected void onInventoryChanged(int slot) {} 
	
	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

	// Code pulled from CoFH's TileInventory class in Thermal Expansion
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this instanceof ISidedInventory && facing != null)
            {
            	return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new SidedInvWrapper((ISidedInventory)this, facing) {
					public void onContentsChanged(int slot)
            		{
            			TileEntityBasicInventory.this.onInventoryChanged(slot);
            			TileEntityBasicInventory.this.markDirty();
            		}
            	});
            }
            else
            {
            	return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new InvWrapper(this) {
					public void onContentsChanged(int slot)
            		{
            			TileEntityBasicInventory.this.onInventoryChanged(slot);
            			TileEntityBasicInventory.this.markDirty();
            		}
            	});
            }
        }
        return super.getCapability(capability, facing);
    }
    
	public int getSizeInventory() {
		return inventory.length;
	}
	
	public boolean isEmpty() {
		for (int i=0; i<getSizeInventory(); i++)
		{
			if (inventory[i].isEmpty()) return false;
		}
		return true;
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString(displayName);
	}
	
	public ItemStack getStackInSlot(int index) {
		if (!FacMathHelper.isInRange(index, 0, getSizeInventory())) return ItemStack.EMPTY;
		return inventory[index];
	}

	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack is = getStackInSlot(index).copy();
		if (count > is.getCount()) count = is.getCount();
		is.setCount(count);
		getStackInSlot(index).shrink(count);
		onInventoryChanged(index); // TODO: Change so that calling from onInventoryChanged does not recurse
		markDirty();
		return is;
	}
	

	public ItemStack removeStackFromSlot(int index)
	{
		return decrStackSize(index, getStackInSlot(index).getCount());
	}
	
	public int containsItem(@Nonnull ItemStack is, int minSlot, int maxSlot)
	{
		if (minSlot > maxSlot)
		{
			minSlot = minSlot + maxSlot;
			maxSlot = minSlot - maxSlot;
			minSlot = minSlot - maxSlot;
		}
		for (int i=minSlot; i<maxSlot; i++)
		{
			if (inventory[i].isItemEqual(is)) return i;
		}
		return -1;
	}
	public int containsItem(@Nonnull ItemStack is)
	{
		for (int i=0; i<getSizeInventory(); i++)
		{
			if (getStackInSlot(i).isItemEqual(is)) return i;
		}
		return -1;
	}
	

	public void setInventorySlotContents(int index, ItemStack stack) {
		if (FacMathHelper.isInRange(index, 0, getSizeInventory()))
		{
			inventory[index] = stack;
		}
		onInventoryChanged(index);
	}

	public int getInventoryStackLimit() {
		return 64; // None of this stacking parts into the buffer and walking away crap
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO:
		// Buffer: Item is on the list of needed parts
		// Input: Item can be processed in the machine
		// Output: No
		return true;
	}

	public void clear() {
		for (int i=0; i<inventory.length; i++)
		{
			inventory[i] = ItemStack.EMPTY;
		}
		onInventoryChanged(0);
	}

	public String getName() {
		return name;
	}
	

	public boolean hasCustomName() {
		return false;
	}
	

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList inv = new NBTTagList();
		for (int i=0; i<inventory.length; i++)
		{
			NBTTagCompound stackTag = new NBTTagCompound();
			stackTag = inventory[i].serializeNBT();
			inv.appendTag(stackTag);
		}
		nbt.setTag("inventory", inv);
		return nbt;
	}

	public int getSlotLimit(int slot) {
		return 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("inventory"))
		{
			NBTTagList inv = nbt.getTagList("inventory", 10);
			for (int i=0; i<inv.tagCount(); i++)
			{
				inventory[i] = new ItemStack(inv.getCompoundTagAt(i));
			}
		}
	}
}