package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class FacRenderHelper
{
	private FacRenderHelper() {}
	
	public static void bindTexture(ResourceLocation loc)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
	}
}