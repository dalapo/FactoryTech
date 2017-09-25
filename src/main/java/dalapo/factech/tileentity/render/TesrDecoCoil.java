package dalapo.factech.tileentity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import dalapo.factech.tileentity.specialized.TileEntityDecoCoil;

public class TesrDecoCoil extends TesrMachine<TileEntityDecoCoil> {

	public TesrDecoCoil(boolean directional)
	{
		super(directional);
		setLightmapDisabled(true);
	}

	@Override
	protected String getModelName() {
		return null;
	}

	@Override
	public void doRender(TileEntityDecoCoil te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.color(0.75F, 0.75F, 1.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bb = tessellator.getBuffer();
		GL11.glLineWidth(4F);
		bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
//		bb.color(0.75F, 0.75F, 1.0F, 1.0F);
		
		tessellator.draw();
		GlStateManager.popMatrix();
	}

}
