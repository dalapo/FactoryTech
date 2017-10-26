package dalapo.factech.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntitySluice;

public class GuiSluice extends GuiBasicMachine
{
	public GuiSluice(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te)
	{
		super(inventorySlotsIn, player, "sluice_gui", te);
		setBarCoords(60, 37);
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(FacGuiHelper.formatTexName(name)));
		if (!((TileEntitySluice)te).isValidLocation())
		{
			this.drawTexturedModalRect(guiLeft + 62, guiTop + 35, 195, 13, 30, 30);
		}
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		if (!((TileEntitySluice)te).isValidLocation())
		{
			fontRenderer.drawStringWithShadow("Invalid location!", 60, 70, 0xFFFF4040);
		}
	}
}