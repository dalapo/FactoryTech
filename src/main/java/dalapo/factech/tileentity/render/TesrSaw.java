package dalapo.factech.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.specialized.TileEntitySaw;

public class TesrSaw extends TesrMachine<TileEntitySaw> {

	public TesrSaw(boolean directional)
	{
		super(directional);
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return "blocks/saw_tesr.obj";
	}
	
	@Override
	public void doRender(TileEntitySaw te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		if (te.hasPart(0)) // Saw blade
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 0.375F, 0.25F);
			GlStateManager.scale(0.6, 0.6, 0.6);
			GlStateManager.rotate(180, 0, 1, 0);
			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
			{
				long angle = System.currentTimeMillis() % 360;
				GlStateManager.rotate(angle, -1, 0, 0);
			}
			GlStateManager.rotate(90, 0, 1, 0);
			FacTesrHelper.renderPart(PartList.SAW);
			GlStateManager.popMatrix();
		}
		if (te.hasPart(1)) // Gear
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 0F, 0F);
			GlStateManager.scale(0.6, 0.6, 0.6);
			GlStateManager.rotate(90, 0, 1, 0);
			FacTesrHelper.renderPart(PartList.GEAR);
			GlStateManager.popMatrix();
		}
		
		ItemStack is = te.getInput();
		if (!is.isEmpty())
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 0.1875F, 0.375F);
			GlStateManager.scale(0.25, 0.25, 0.25);
			FacTesrHelper.renderStack(is);
			GlStateManager.popMatrix();
		}
	}
}