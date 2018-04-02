package dalapo.factech.gui;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.automation.TileEntityBufferCrate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GuiBufferCrate extends GuiFacInventory
{
	int invRows;
	int invCols;
	String texName;
	IInventory playerInv;
	private TileEntityBufferCrate te;
	
	public GuiBufferCrate(Container inventorySlotsIn, TileEntityBufferCrate te, IInventory playerInv)
	{
		super(inventorySlotsIn);
		this.te = te;
		this.playerInv = playerInv;
	}

	@Override
	public TileEntityBasicInventory getTileEntity()
	{
		return te;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		FacGuiHelper.bindTex(texName);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}