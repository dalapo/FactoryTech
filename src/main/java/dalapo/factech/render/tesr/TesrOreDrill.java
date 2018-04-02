package dalapo.factech.render.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityOreDrill;

public class TesrOreDrill extends TesrMachine<TileEntityOreDrill> {

	public TesrOreDrill(boolean directional) {
		super(directional);
	}

	@Override
	protected String getModelName() {
		return "";
	}

	@Override
	public void doRender(TileEntityOreDrill te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te.hasPart(1)) // Drill
		{
			boolean flag = false;
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0.25F, 0);
			GlStateManager.scale(0.6F, 0.6F, 0.6F);
			GlStateManager.rotate(270, 0, 0, 1);
			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
			{
				flag = true;
				GlStateManager.pushMatrix();
				GlStateManager.rotate(System.currentTimeMillis() % 360, 1, 0, 0);
			}
			FacTesrHelper.renderPart(PartList.DRILL);
			GlStateManager.rotate(90, 1, 0, 0);
			FacTesrHelper.renderPart(PartList.DRILL);
			if (flag) GlStateManager.popMatrix(); // Spin
			GlStateManager.popMatrix(); // Translate
		}
		ItemStack is = te.getInput();
		if (!is.isEmpty())
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, 0);
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			FacTesrHelper.renderStack(is);
			GlStateManager.popMatrix();
		}
	}

}