package dalapo.factech.tileentity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import dalapo.factech.tileentity.specialized.TileEntityAutoMiner;

@SideOnly(Side.CLIENT)
public class TesrMiner extends TesrMachine<TileEntityAutoMiner>
{
//	private boolean hasRenderedFirstTime = false;
	public TesrMiner(boolean directional)
	{
		super(directional);
	}

	@Override
	public boolean isGlobalRenderer(TileEntityAutoMiner te)
	{
		return true;
	}
	
	@Override
	protected String getModelName() {
		return null;
	}

	@Override
	public void doRender(TileEntityAutoMiner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.color(255.0F, 0.0F, 0.0F);
//		if (!hasRenderedFirstTime)
//		{
//			Thread.currentThread().dumpStack();
//			hasRenderedFirstTime = true;
//		}
		Tessellator t = Tessellator.getInstance();
		BufferBuilder bb = t.getBuffer();
		bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		bb.pos(1, 1, 1).endVertex();
		bb.pos(1, 2, 1).endVertex();
		bb.pos(1, 2, 1).endVertex();
		bb.pos(2, 2, 1).endVertex();
		bb.pos(2, 2, 1).endVertex();
		bb.pos(2, 2, 2).endVertex();
		t.draw();
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}
}