package dalapo.factech.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityMetalCutter;

public class TesrMetalCutter extends TesrMachine<TileEntityMetalCutter>
{

	public TesrMetalCutter(boolean directional) {
		super(directional);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRender(TileEntityMetalCutter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		ItemStack is = te.getInput();
		GlStateManager.rotate(90, 1, 0, 0);
		FacTesrHelper.renderStack(is);
		
		if (te.hasPart(0))
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5, 0.5, 0.5);
			GlStateManager.translate(0, 0, -0.5);
			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
			{
				double time = (double)(System.currentTimeMillis() % 100);
				GlStateManager.translate(0, 0, time/200);
			}
			GlStateManager.rotate(90, 1, 0, 0);
			FacTesrHelper.renderPart(PartList.BLADE);
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}

}
