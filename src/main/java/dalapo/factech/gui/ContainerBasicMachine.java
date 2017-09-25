package dalapo.factech.gui;

import java.awt.Point;

import javax.annotation.Nullable;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Pair;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBasicMachine extends Container {

	TileEntityMachine te;
	IInventory player;
	int inSlots;
	int partSlots;
	int outSlots;
	
	int playerInvOffset;
	
	public boolean[] partsGot;
	public int progress;
	
	protected void addPlayerInv(int slot)
	{
		for (int y = 0; y < 3; ++y)
		{
	        for (int x = 0; x < 9; ++x, slot++)
	        {
	            this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + playerInvOffset + y * 18));
	        }
	    }

		for (int i=0; i<9; i++)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142 + playerInvOffset));
		}
	}
	
	public ContainerBasicMachine(int partSlots, int playerOffset, TileEntityMachine te, IInventory player, Pair<Integer, Integer>[] inCoords, Pair<Integer, Integer>[] outCoords)
	{
		this.partSlots = partSlots;
		this.te = te;
		this.player = player;
		playerInvOffset = playerOffset;
		partsGot = new boolean[te.countPartSlots()];
		
		int slot = 0;
		inSlots = inCoords.length;
		
		for (int i=0; i<inSlots; i++)
		{
			addSlotToContainer(new Slot(te, slot++, inCoords[i].a, inCoords[i].b));
		}
		
		for (int i=0; i<partSlots; i++)
		{
			addSlotToContainer(new Slot(te, slot++, 152, 8 + (i*18)));
		}
		
		outSlots = outCoords.length;
		if (outSlots > 0)
		for (int i=0; i<outSlots; i++)
		{
			addSlotToContainer(new Slot(te, slot++, outCoords[i].a, outCoords[i].b));
		}
		addPlayerInv(slot);
	}
	
	public ContainerBasicMachine(int partSlots, TileEntityMachine te, IInventory player, Pair<Integer, Integer>[] inCoords, Pair<Integer, Integer>[] outCoords)
	{
		this(partSlots, 0, te, player, inCoords, outCoords);
	}

	public ContainerBasicMachine(int partSlots, TileEntityMachine te, IInventory player, int inX, int inY, int outX, int outY)
	{
		this(partSlots, te, player, new Pair[] {new Pair<Integer, Integer>(inX, inY)}, new Pair[] {new Pair<Integer, Integer>(outX, outY)});
	}
	
	public ContainerBasicMachine(int partSlots, TileEntityMachine te, IInventory player)
	{
		this(partSlots, te, player, new Pair[] {}, new Pair[] {});
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ep, int slot)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot s = this.getSlot(slot);
		
		if (s != null && s.getHasStack())
		{
			ItemStack temp = s.getStack();
			itemstack = temp.copy();
			
			if (slot < partSlots + 2)
			{
				if (!mergeItemStack(temp, partSlots + 2, inventorySlots.size(), false))
				{
					this.te.getHasWork();
					return ItemStack.EMPTY;
				}
			}
			else return ItemStack.EMPTY;
			
			if (temp.isEmpty()) return ItemStack.EMPTY;
			else s.onSlotChanged();
		}
		this.te.getHasWork();
		return itemstack;
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i=0; i<this.listeners.size(); i++)
		{
			IContainerListener listener = listeners.get(i);
			if (progress != te.age)
			{
				listener.sendWindowProperty(this, 0, te.age);
			}
			for (int j=0; j<te.countPartSlots(); j++)
			{
				if (partsGot[j] != te.hasPart(j))
				{
					listener.sendWindowProperty(this, j + 1, te.hasPart(j) ? 1 : 0);
				}
			}
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer ep) {
		return te.isUsableByPlayer(ep);
	}
}
