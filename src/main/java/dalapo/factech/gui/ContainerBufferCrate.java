package dalapo.factech.gui;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.automation.TileEntityBufferCrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBufferCrate extends Container
{
	private static final int ROWS = 3;
	private static final int COLS = 3;
	
	private TileEntityBufferCrate te;
	public ContainerBufferCrate(int rows, int cols, TileEntityBufferCrate te, IInventory playerInv)
	{
		this.te = te;
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int slot = 0;
		// TE inventory; will auto-centre if I got the math right
		for (int row=0; row<rows; row++)
		{
			for (int col=0; col<cols; col++, slot++)
			{
				this.addSlotToContainer(new SlotItemHandler(handler, slot, (89 - (9 * cols)) + (col * 18), (71 - (18 * rows)) + (row * 18)));
			}
		}
		
		for (int y = 0; y < 3; ++y)
		{
	        for (int x = 0; x < 9; ++x, slot++)
	        {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }
		
		for (int i=0; i<9; i++)
		{
			// Copypasta from modding tutorial
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clicktype, EntityPlayer ep)
	{
		Logger.info("Slot: " + slotId);
		if (slotId < ROWS * COLS)
		{
//			te.setFilter(slotId, ep.getActiveItemStack());
			return ep.getActiveItemStack();
		}
		else return super.slotClick(slotId, dragType, clicktype, ep);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ep, int slot)
	{
		// No shift-clicking for you!
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return false;
	}
}