package dalapo.factech.render.tesr;

import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.automation.TileEntityBlockBreaker;
import net.minecraft.client.renderer.GlStateManager;

public class TesrBlockBreaker extends TesrMachine<TileEntityBlockBreaker>
{

	public TesrBlockBreaker(boolean directional)
	{
		super(directional);
	}

	@Override
	public void doRender(TileEntityBlockBreaker te, double x, double y, double z, float partialTicks, int destroyStage,	float alpha) 
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 0.25);
		GlStateManager.rotate(90, 0, -1, 0);
		FacTesrHelper.renderPart(PartList.DRILL);
		GlStateManager.rotate(90, 1, 0, 0);
		FacTesrHelper.renderPart(PartList.DRILL);
		GlStateManager.popMatrix();
	}
}