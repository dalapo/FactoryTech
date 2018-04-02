package dalapo.factech.gui;

import net.minecraft.inventory.IInventory;
import dalapo.factech.gui.widget.WidgetFluidTank;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityFluidDrill;

public class GuiFluidDrill extends GuiBasicMachine
{

	public GuiFluidDrill(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te) {
		super(inventorySlotsIn, player, "fluiddrill_gui", te);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addWidget(new WidgetFluidTank(this, 98, 66, 16, 48, ((TileEntityFluidDrill)te).getTank(0), 0));
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		TileEntityFluidDrill tefd = (TileEntityFluidDrill)te;
		
		fontRenderer.drawString(tefd.isSulphur() ? "Sulphur" : "Propane", 8, 8, 0x404040);
		fontRenderer.drawString(Integer.toString(tefd.amountPer()) + " mB per operation", 8, 20, 0x404040);
	}
	
	@Override
	public void drawProgressBar()
	{
		int progress = te.getProgressScaled(36);
		this.drawTexturedModalRect(guiLeft + 54, guiTop + 59 - progress, 197, 38 - progress, 41, progress);
	}
}
