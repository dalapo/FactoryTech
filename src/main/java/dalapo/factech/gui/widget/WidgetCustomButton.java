package dalapo.factech.gui.widget;

import dalapo.factech.helper.FacGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class WidgetCustomButton extends GuiButton
{
	protected String filename;
	private GuiScreen parent;
	public WidgetCustomButton(GuiScreen parent, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String file) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.parent = parent;
		filename = file;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (enabled)
		{
			GlStateManager.pushMatrix();
			mc.renderEngine.bindTexture(new ResourceLocation(FacGuiHelper.formatTexName(filename)));
			GlStateManager.color(1F, 1F, 1F, 1F);
			this.drawTexturedModalRect(x, y, 0, 0, width, height);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int isHovering = this.hovered ? 16777120 : 0xFFFFFFFF;
            this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, isHovering);
			GlStateManager.popMatrix();
		}
	}
}
