package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
}
