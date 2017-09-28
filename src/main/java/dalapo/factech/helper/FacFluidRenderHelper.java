package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FacFluidRenderHelper {

	private FacFluidRenderHelper() {}
	
	public static ResourceLocation getFluidTex(Fluid fluid, boolean isFlowing)
	{
		if (fluid == null)
		{
			Logger.info("Returning null from getFluidTex(Fluid)");
			return null;
		}
		if (isFlowing) return fluid.getFlowing();
		else return fluid.getStill();
	}
	
	public static ResourceLocation getFluidTex(FluidStack fluid, boolean isFlowing)
	{
		if (fluid == null || fluid.getFluid() == null)
		{
			Logger.info("Returning null from getFluidTex(FluidStack)");
			return null;
		}
		if (isFlowing) return fluid.getFluid().getFlowing(fluid);
		else return fluid.getFluid().getStill(fluid);
	}
	
	public static TextureAtlasSprite getSprite(Fluid fluid, boolean isFlowing)
	{
		ResourceLocation loc = getFluidTex(fluid, isFlowing);
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(loc.toString());
	}
	
	public static TextureAtlasSprite getSprite(FluidStack fluid, boolean isFlowing)
	{
		ResourceLocation loc = getFluidTex(fluid, isFlowing);
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(loc.toString());
	}
	
	public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), 0.0).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), 0.0).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), 0.0).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), 0.0).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }
	
	public static void drawFluid(FluidStack fluid, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) return;
		TextureAtlasSprite fluidSprite = FacFluidRenderHelper.getSprite(fluid, false);
		if (fluidSprite == null) return;
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		FacGuiHelper.setColor(fluid.getFluid().getColor());
		
		drawTexturedModalRect(x, y + height - (int)(height * 16), fluidSprite, width, (int)(height * 16));
	}
}
