package dalapo.factech.render.tesr;

import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityWoodcutter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class TesrWoodcutter extends TesrMachine<TileEntityWoodcutter>
{

	public TesrWoodcutter(boolean directional) {
		super(directional);
	}

	@Override
	public void doRender(TileEntityWoodcutter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		if (te.hasPart(0)) // Saw
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, 0.375);
			GlStateManager.rotate(90, 1, 0, 0);
			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
			{
				long angle = System.currentTimeMillis() % 360;
				GlStateManager.rotate(angle, 0, 0, -1);
			}
			FacTesrHelper.renderPart(PartList.SAW);
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}
}