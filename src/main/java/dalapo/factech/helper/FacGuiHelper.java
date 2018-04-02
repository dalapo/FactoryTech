package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class FacGuiHelper {
	private FacGuiHelper() {}
	
	public static String formatTexName(String baseName)
	{
		return "factorytech:textures/gui/" + baseName + ".png";
	}
	
	public static void bindTex(String name)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(formatTexName(name)));
	}
	
	public static void setColor(int color)
	{
		float r = (float)(color >> 16 & 255) / 255.0F;
		float g = (float)(color >> 8 & 255) / 255.0F;
		float b = (float)(color & 255) / 255.0F;
		GlStateManager.color(r, g, b, 1);
	}
	
	public static void drawFluidTank(GuiContainer gui, int x, int y, int width, int height, int maxVal, FluidStack fluid)
	{
		if (fluid == null || fluid.amount <= 0) return;

		TextureAtlasSprite icon = FacFluidRenderHelper.getSprite(fluid, false);
		if (icon == null) return;
		
		int fillLevel = height * fluid.amount / maxVal;	
	}
	
	public static ResourceLocation getItemTex(ItemStack is)
	{
		return new ResourceLocation(is.getUnlocalizedName());
	}
	
	public static void renderItemStack(ItemStack is, int x, int y)
	{
		RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(is, x, y);
		Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, is, x, y, null);
	}
	
	public static void drawTexturedModalRect(int x, int y, double z, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), z).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), z).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), z).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), z).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
	
	/**
	 * Copy-pasted from GuiContainer for universal accessibility.
	 */
	public static boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY)
    {
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }
	
	public static void renderToolTip(GuiScreen parent, String text, int x, int y)
    {
        parent.drawHoveringText(text, x, y);
    }
}