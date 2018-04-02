package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;

public class FacTesrHelper {
	private FacTesrHelper() {}
	
	public static TextureAtlasSprite getAtlasFromLocation(ResourceLocation loc)
	{
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(loc.toString());
	}
	
	public static void renderStack(ItemStack is)
	{
		Minecraft.getMinecraft().getRenderItem().renderItem(is, ItemCameraTransforms.TransformType.NONE);
	}
	
	public static void renderPart(PartList id, int variant)
	{
		renderStack(new ItemStack(ItemRegistry.machinePart, 1, id.getFloor() + variant));
	}
	
	public static void renderPart(PartList id)
	{
		renderPart(id, 0);
	}
}