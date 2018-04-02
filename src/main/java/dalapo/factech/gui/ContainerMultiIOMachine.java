package dalapo.factech.gui;

import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerMultiIOMachine extends Container {
	TileEntityMachine te;
	IInventory player;
	int inSlots;
	int outSlots;
	int partSlots;
	
	public ContainerMultiIOMachine(TileEntityMachine te, int inputs, int outputs, int parts, IInventory player)
	{
		this.partSlots = parts;
		this.te = te;
		this.player = player;
		
		int slot = 0;
		addSlotToContainer(new Slot(te, slot++, 35, 35)); // Input
		
		for (int i=0; i<partSlots; i++, slot++)
		{
			// Parts
			addSlotToContainer(new Slot(te, slot, 152, 8 + (i*18)));
		}
		
		// Output
		addSlotToContainer(new Slot(te, slot++, 89, 35));
		
		// Player inventory and hotbar
		for (int y = 0; y < 3; ++y)
		{
	        for (int x = 0; x < 9; ++x, slot++)
	        {
	            this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

		for (int i=0; i<9; i++)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return true;
	}
}