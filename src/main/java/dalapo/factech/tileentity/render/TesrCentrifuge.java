package dalapo.factech.tileentity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityCentrifuge;

public class TesrCentrifuge extends TesrMachine<TileEntityCentrifuge>
{
	public TesrCentrifuge(boolean directional) {
		super(directional);
	}

	@Override
	protected String getModelName() {
		return "centrifuge_spin";
	}

	@Override
	public void doRender(TileEntityCentrifuge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		long angle = System.currentTimeMillis() % 360;
		if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
		{
			GlStateManager.rotate(angle, 0, 1, 0);
		}
		GlStateManager.translate(-te.getPos().getX()-0.5, -te.getPos().getY()-0.5, -te.getPos().getZ()-0.5);

		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		
		this.bindTexture(new ResourceLocation(NameList.MODID, "textures/blocks/model_tex_sheet.png"));
		if (Minecraft.isAmbientOcclusionEnabled())
		{
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		}
		else
		{
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		World world = te.getWorld();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bb = tessellator.getBuffer();
		bb.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		dispatcher.getBlockModelRenderer().renderModel(world, getBakedModel(), world.getBlockState(te.getPos()), te.getPos(), bb, true);
		
		tessellator.draw();
		GlStateManager.popMatrix();
		
		if (te.hasPart(0))
		{
			// Motor
		}
		if (te.hasPart(1))
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.2, -0.2, 0.2);
			GlStateManager.rotate(90, 1, 0, 0);
//			if (te.isRunning() && !Minecraft.getMinecraft().isGamePaused())
//			{
//				GlStateManager.rotate(System.currentTimeMillis() % 360, 0, 0, -1);
//			}
			GlStateManager.scale(0.5, 0.5, 0.5);
			FacTesrHelper.renderPart(PartList.GEAR);
			GlStateManager.popMatrix();
		}
		if (te.hasPart(2))
		{
			// Shaft
		}
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
	}
}