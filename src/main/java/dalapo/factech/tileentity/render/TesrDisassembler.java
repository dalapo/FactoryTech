package dalapo.factech.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityDisassembler;

public class TesrDisassembler extends TesrMachine<TileEntityDisassembler>
{

	public TesrDisassembler(boolean directional)
	{
		super(directional);
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRender(TileEntityDisassembler te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if (te.hasPart(0))
		{
			GlStateManager.translate(0, 0.25, 0);
			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
			{
				GlStateManager.rotate(System.currentTimeMillis()%360, 0, 0, 1);
			}
			FacTesrHelper.renderPart(PartList.SAW);
		}
	}

}
