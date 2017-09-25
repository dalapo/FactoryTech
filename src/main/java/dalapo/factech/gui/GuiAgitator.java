package dalapo.factech.gui;

import net.minecraft.inventory.IInventory;
import dalapo.factech.gui.widget.WidgetFluidTank;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityAgitator;

public class GuiAgitator extends GuiBasicMachine {

	private TileEntityAgitator agitator;
	public GuiAgitator(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te) {
		super(inventorySlotsIn, player, "agitator_gui", te);
		agitator = (TileEntityAgitator)te;
	}

	@Override
	protected void drawProgressBar()
	{
		int progress = te.getProgressScaled(20);
		this.drawTexturedModalRect(guiLeft + 50, guiTop + 27, 176, 2, progress, 7);
		this.drawTexturedModalRect(guiLeft + 50, guiTop + 35, 176, 2, progress, 7);
	}
	
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		addWidget(new WidgetFluidTank(this, 8, 66, 16, 3, agitator.getTank(0)));
		addWidget(new WidgetFluidTank(this, 26, 66, 16, 3, agitator.getTank(1)));
		addWidget(new WidgetFluidTank(this, 80, 66, 16, 3, agitator.getTank(2)));
	}
}