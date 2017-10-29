package dalapo.factech.render.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityMagnetizer;

public class TesrMagnetizer extends TesrMachine<TileEntityMagnetizer>
{

	public TesrMagnetizer(boolean directional)
	{
		super(directional);
	}

	@Override
	protected String getModelName()
	{
		return null;
	}

	@Override
	public void doRender(TileEntityMagnetizer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if (te.hasPart(0))
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, -0.5);
			GlStateManager.scale(0.5, 0.5, 0.5);
			GlStateManager.rotate(-90, 0, 1, 0);
			GlStateManager.rotate(90, 1, 0, 0);
			FacTesrHelper.renderPart(PartList.MOTOR);
			GlStateManager.popMatrix();
		}
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8, 0.8, 0.8);
		GlStateManager.translate(0, 0, 0.25);
		GlStateManager.rotate(90, 0, 1, 0);
		if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
		{
			GlStateManager.rotate(System.currentTimeMillis() % 360, 1, 0, 0);
		}
		FacTesrHelper.renderStack(te.getInput());
		GlStateManager.popMatrix();
	}

}
