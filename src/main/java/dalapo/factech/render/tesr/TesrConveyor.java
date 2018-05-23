package dalapo.factech.render.tesr;

import java.util.Deque;

import org.lwjgl.opengl.GL11;

import dalapo.factech.block.BlockConveyor;
import dalapo.factech.helper.FacRenderHelper;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.tileentity.automation.TileEntityConveyor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TesrConveyor extends TesrOmnidirectional<TileEntityConveyor>
{
	@Override
	public void doRender(TileEntityConveyor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		
		Deque<ItemStack> stacks = te.getStacks(TesrElevator.auth);
        ItemStack andOneMore = te.getLegacy();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        int i = 0;
        for (ItemStack stack : stacks)
        {
        	if (!stack.isEmpty())
        	{
        		GlStateManager.pushMatrix();
        		GlStateManager.translate(1, 0.2, 1 - (i - partialTicks) / 20);
        		GlStateManager.scale(0.25, 0.25, 0.25);
        		FacTesrHelper.renderStack(stack);
        		GlStateManager.popMatrix();
        	}
        	i++;
        }
        GlStateManager.pushMatrix();
		GlStateManager.translate(1, 1 + (partialTicks / 20), 0.5);
		GlStateManager.scale(0.25, 0.25, 0.25);
		FacTesrHelper.renderStack(andOneMore);
		GlStateManager.popMatrix();
        GlStateManager.popMatrix();
		
		GlStateManager.enableLighting();
		RenderHelper.enableStandardItemLighting();
		FacRenderHelper.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (Minecraft.isAmbientOcclusionEnabled())
		{
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		}
		else
		{
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		World world = te.getWorld();
		
		// laaaaaaag
		for (double d=0; d<1; d+=0.125)
		{
			GlStateManager.pushMatrix();
			Tessellator v5 = Tessellator.getInstance();
			BufferBuilder builder = v5.getBuffer();
			IBlockState state = BlockRegistry.conveyor.getDefaultState().withProperty(BlockRegistry.conveyor.PART_ID, 1);
			BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

			IBakedModel model = dispatcher.getModelForState(state);
			builder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			GlStateManager.translate(-0.49, 0.03125, i-0.46875);
			GlStateManager.scale(0.98, 1, 1);
			long angle = System.currentTimeMillis() / 8 % 360;
			if (!Minecraft.getMinecraft().isGamePaused()) GlStateManager.rotate(angle, -1, 0, 0);
			GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY()-0.03125, -te.getPos().getZ()-0.03125);
			dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), builder, false);	
			v5.draw();
			GlStateManager.popMatrix();
		}
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
}