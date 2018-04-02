package dalapo.factech.tileentity.automation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import dalapo.factech.tileentity.TileEntityBasicInventory;

public class TileEntityCrate extends TileEntityBasicInventory
{
	public TileEntityCrate()
	{
		super("crate", 18);
		setDisplayName("Crate");
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
}
/*package dalapo.factech.tileentity.automation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityBasicInventory;

public class TileEntityCrate extends TileEntityBase
{
	private static final int SIZE = 18;
	
	private final ItemStackHandler storage = new ItemStackHandler(SIZE) {
		@Override
		public ItemStack insertItem(int slot, ItemStack is, boolean simulate)
		{
			return super.insertItem(slot, is, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			if (amount == 0) return ItemStack.EMPTY;
			validateSlotIndex(slot);
			
			return ItemStack.EMPTY; // TODO
		}
	};

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Crate");
	}
}*/
