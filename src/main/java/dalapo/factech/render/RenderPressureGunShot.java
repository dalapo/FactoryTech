package dalapo.factech.render;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import dalapo.factech.entity.EntityPressureGunShot;
import dalapo.factech.entity.EntityPressureGunShot.EnumPressureGun;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.PartList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RenderPressureGunShot extends Render<EntityPressureGunShot>
{

	public RenderPressureGunShot(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityPressureGunShot entity, double x, double y, double z, float f0, float f1)
	{
		this.bindEntityTexture(entity);
		
		if (entity.getFluid() == null) return;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.enableRescaleNormal();
		
//		GlStateManager.disableCull();
		GlStateManager.rotate(180.0F - renderManager.playerViewY, 0, 1.0F, 0);
		GlStateManager.rotate(-renderManager.playerViewX, 1.0F, 0, 0);
		

		TextureAtlasSprite sprite = FacFluidRenderHelper.getSprite(entity.getFluid(), false);
		
		if (sprite != null)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GlStateManager.scale(0.5, 0.5, 0.5);
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder renderer = tessellator.getBuffer();
			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(-0.25, -0.25, 0).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(0)).endVertex();
			renderer.pos(0.25, -0.25, 0).tex(sprite.getInterpolatedU(4), sprite.getInterpolatedV(0)).endVertex();
			renderer.pos(0.25, 0.25, 0).tex(sprite.getInterpolatedU(4), sprite.getInterpolatedV(4)).endVertex();
			renderer.pos(-0.25, 0.25, 0).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(4)).endVertex();
			tessellator.draw();
		}
		
//		GlStateManager.enableCull();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}
	
	@Override
	@Nullable
	protected ResourceLocation getEntityTexture(EntityPressureGunShot entity) {
		switch (entity.getProjType().name)
		{
		case "water":
			return FacFluidRenderHelper.getFluidTex(FluidRegistry.WATER, false);
		case "h2so4":
			return FacFluidRenderHelper.getFluidTex(ModFluidRegistry.h2so4, false);
		case "propane":
			return FacFluidRenderHelper.getFluidTex(FluidRegistry.LAVA, false);
			default:
				return null;
		}
	}

}
