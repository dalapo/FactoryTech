package dalapo.factech.gui;

import java.awt.Point;

import net.minecraft.inventory.IInventory;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;

public class GuiSaw extends GuiBasicMachine
{
	public GuiSaw(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te)
	{
		super(inventorySlotsIn, player, "saw_gui", te);
	}

	@Override
	protected void drawProgressBar()
	{
		int progress = te.getProgressScaled(21);
		this.drawTexturedModalRect(guiLeft + 35, guiTop + 16 + progress, 176, 137, 16, 16);
	}
}