package dalapo.factech.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;

public class FacTesrHelper {
	private FacTesrHelper() {}
	
	public static void renderStack(ItemStack is)
	{
		Minecraft.getMinecraft().getRenderItem().renderItem(is, ItemCameraTransforms.TransformType.NONE);
	}
	
	public static void renderPart(PartList id)
	{
		renderStack(new ItemStack(ItemRegistry.machinePart, 1, id.getFloor()));
	}
}