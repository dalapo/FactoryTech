package dalapo.factech.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dalapo.factech.gui.widget.FacTechWidget;
import dalapo.factech.gui.widget.IClickable;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public abstract class GuiFacInventory extends GuiContainer {

	protected List<FacTechWidget> widgets = new ArrayList<FacTechWidget>();
	
	public GuiFacInventory(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	// Expectation: subclasses will have a TEBI object
	public abstract TileEntityBasicInventory getTileEntity();
	
	public void initGui()
	{
		super.initGui();
		for (FacTechWidget widget : widgets)
		{
			widget.init();
		}
	}
	
	public float getZLevel()
	{
		return zLevel;
	}
	
	public void bindTextureManager(ResourceLocation loc)
	{
		this.mc.getTextureManager().bindTexture(loc);
	}
	
	public GuiFacInventory addWidget(FacTechWidget widget)
	{
		widgets.add(widget);
		return this;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		for (FacTechWidget widget : widgets)
		{
			widget.draw();
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void renderHoveredToolTip(int mouseX, int mouseY)
	{
		super.renderHoveredToolTip(mouseX, mouseY);
		int mmX = mouseX - guiLeft;
		int mmY = mouseY - guiTop;
		for (FacTechWidget widget : widgets)
		{
			if (widget.isPointInBounds(mmX, mmY)) FacGuiHelper.renderToolTip(this, widget.getTooltip(), mouseX, mouseY);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int mmX = mouseX - guiLeft;
		int mmY = mouseY - guiTop;
		for (int i=0; i<widgets.size(); i++)
		{
			FacTechWidget widget = widgets.get(i);
			if (widget.isPointInBounds(mmX, mmY)) widget.handle(mouseButton, isShiftKeyDown());
		}
	}
}