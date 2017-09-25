package dalapo.factech.gui;

import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerCrucible extends ContainerBasicMachine {

	public ContainerCrucible(int partSlots, TileEntityMachine te, IInventory player) {
		super(partSlots, te, player, 35, 35, 999, 999);
	}
	
	public ContainerCrucible(TileEntityMachine te, IInventory player)
	{
		this(te.countPartSlots(), te, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}