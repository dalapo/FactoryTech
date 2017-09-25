package dalapo.factech.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import dalapo.factech.gui.widget.FacTechWidget;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GuiFacInventory extends GuiContainer {

	private List<FacTechWidget> widgets = new ArrayList<FacTechWidget>();
	
	public GuiFacInventory(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	public void bindTextureManager(ResourceLocation loc)
	{
		this.mc.getTextureManager().bindTexture(loc);
	}
	
	public void addWidget(FacTechWidget widget)
	{
//		Logger.info("Entered addWidget");
		widgets.add(widget);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//		Logger.info("drawGuiContainerBackgroundLayer in GuiFacInventory");
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
}