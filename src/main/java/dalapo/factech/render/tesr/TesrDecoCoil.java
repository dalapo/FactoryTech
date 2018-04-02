package dalapo.factech.render.tesr;

import java.util.Set;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
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
		te.render(partialTicks, x, y, z);
	}
}