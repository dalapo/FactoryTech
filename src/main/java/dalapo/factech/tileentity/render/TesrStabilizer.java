package dalapo.factech.tileentity.render;

import net.minecraft.client.renderer.GlStateManager;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.tileentity.specialized.TileEntityStabilizer;

public class TesrStabilizer extends TesrMachine<TileEntityStabilizer>
{
	private double amplitude;
	private double stepTime;
	private double curTime;
	private double vel[] = new double[3];
	
	public TesrStabilizer(boolean directional)
	{
		super(directional);
		curTime = 0;
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRender(TileEntityStabilizer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if (te.isRunning())
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotate((System.currentTimeMillis()/25)%360, 0, 1, 0);
			GlStateManager.translate(0, 0.25, 0);
			GlStateManager.scale(0.5, 0.5, 0.5);
			FacTesrHelper.renderStack(te.getInput());
			GlStateManager.popMatrix();
		}
	}
}