package dalapo.factech.gui.handbook;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiHandbookBase extends GuiScreen
{
	protected int background;
	protected int guiLeft;
	protected int guiTop;
	protected final int xSize = 256;
	protected final int ySize = 160;
	
	@Override
	public void initGui()
	{
//		Logger.info(String.format("Width: %s; Height: %s in base", width, height));
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		background = FactoryTech.random.nextInt(4);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		ResourceLocation guiTex = new ResourceLocation(NameList.MODID, "textures/gui/handbook_gui_" + background + ".png");
		mc.getTextureManager().bindTexture(guiTex);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}