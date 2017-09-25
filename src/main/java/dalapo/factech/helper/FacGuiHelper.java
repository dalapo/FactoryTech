package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
	public static int getUIFromString(String str)
	{
		switch (str)
		{
		case "saw":
			return 0;
		case "wirecutter":
			return 1;
		case "metalmill":
			return 2;
			// etc.
		}
		return -1;
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
}