package dalapo.factech.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityTemperer;

public class GuiTemperer extends GuiBasicMachine
{

	public GuiTemperer(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te)
	{
		super(inventorySlotsIn, player, "temperer_gui", te);
	}
	
	public void drawProgressBar()
	{
		int progress = te.getProgressScaled(58);
		drawTexturedModalRect(guiLeft + 54, guiTop + 40, 176, 0, progress, 6);
	}
	
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		FacGuiHelper.bindTex(name);
		int targetTimeScaled = (int)(((TileEntityTemperer)te).getActiveTime() / 120.0 * 58);
		if (targetTimeScaled > 0) drawTexturedModalRect(targetTimeScaled + 54, 38, 198, 7, 3, 12);
	}
}