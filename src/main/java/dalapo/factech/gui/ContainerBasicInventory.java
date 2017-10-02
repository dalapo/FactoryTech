package dalapo.factech.gui;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBasicInventory extends Container {

	private int invRows;
	private int invCols;
	
	private TileEntityBasicInventory te;
	
	public ContainerBasicInventory(int rows, int cols, TileEntityBasicInventory te, IInventory playerInv)
	{
		invRows = rows;
		invCols = cols;
		this.te = te;
		
		int slot = 0;
		
		// TE inventory; will auto-centre if I got the math right
		for (int row=0; row<rows; row++)
		{
			for (int col=0; col<cols; col++, slot++)
			{
				// TODO: Uncentre the y-axis if necessary to prevent overlap w/ player inventory
				this.addSlotToContainer(new Slot(te, slot, (89 - (9 * cols)) + (col * 18), (71 - (18 * rows)) + (row * 18)));
			}
		}
		
		// Player inventory
		// Copypasta from modding tutorial
		for (int y = 0; y < 3; ++y)
		{
	        for (int x = 0; x < 9; ++x, slot++)
	        {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }
		
		// Player hotbar
		for (int i=0; i<9; i++)
		{
			// Copypasta from modding tutorial
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ep, int slot)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot s = (Slot)this.inventorySlots.get(slot);
		te.onInventoryChanged(slot); // Screw it
		if (s != null && s.getHasStack())
		{
			ItemStack temp = s.getStack();
			itemstack = temp.copy();
			
			if (slot < invRows * invCols)
			{
				if (!this.mergeItemStack(temp, invRows * invCols, this.inventorySlots.size(), false))
				{
					return ItemStack.EMPTY;
				}
			}
			
			if (!this.mergeItemStack(temp, 0, invRows * invCols, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (temp.isEmpty())
			{
				s.putStack(ItemStack.EMPTY);
			}
			else
			{
				s.onSlotChanged();
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clicktype, EntityPlayer ep)
	{
		super.slotClick(slotId, dragType, clicktype, ep);
		te.onInventoryChanged(slotId); // Screw it
		return ItemStack.EMPTY;
	}
	
	public int getRows()
	{
		return invRows;
	}
	
	public int getCols()
	{
		return invCols;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ep) {
		return te.isUsableByPlayer(ep);
	}

}