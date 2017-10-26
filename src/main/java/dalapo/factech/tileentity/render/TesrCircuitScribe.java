package dalapo.factech.tileentity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.tileentity.specialized.TileEntityCircuitScribe;

public class TesrCircuitScribe extends TesrMachine<TileEntityCircuitScribe>
{
	private int time = 0;
	private double posX = 0;
	private double posY = 0;
	private double vx = 0.01;
	private double vy = 0.01;
	
	public TesrCircuitScribe(boolean directional) {
		super(true);
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRender(TileEntityCircuitScribe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if (FacStackHelper.matchStacks(te.getStackInSlot(0), ItemRegistry.circuitIntermediate, 8))
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.translate(0, 0, -0.03125);
			FacTesrHelper.renderStack(new ItemStack(ItemRegistry.circuitIntermediate, 1, 8));
			GlStateManager.popMatrix();
		}
		if (te.isRunning())
		{
			GlStateManager.pushMatrix();
			GlStateManager.color(0.8F, 0.1F, 0.1F);
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.disableLighting();
			Tessellator v5 = Tessellator.getInstance();
			BufferBuilder builder = v5.getBuffer();
			GL11.glLineWidth(4.0F);
			builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
			builder.pos(0, 0.45, 0).endVertex();
			if (!Minecraft.getMinecraft().isGamePaused())
			{
				posX += vx;
				posY += vy;
				time++;
			}
//			Logger.info(String.format("(%s, %s)", posX, posY));
			if (time > 100 || posX*posX>0.1 || posY*posY>0.2)
			{
				posX=(FactoryTech.random.nextDouble()-0.5)/2;
				posY=(FactoryTech.random.nextDouble()-0.5)/2;
				vx=(FactoryTech.random.nextDouble()-0.50)/50;
				vy=(FactoryTech.random.nextDouble()-0.50)/50;
				time = 0;
			}
			builder.pos(posX, 0, posY).endVertex();
			v5.draw();
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}
	}
}