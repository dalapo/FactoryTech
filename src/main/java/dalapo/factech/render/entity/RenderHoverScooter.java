package dalapo.factech.render.entity;

import dalapo.factech.entity.EntityHoverScooter;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RenderHoverScooter extends Render<EntityHoverScooter>
{
	protected static ModelHoverScooter model = new ModelHoverScooter();
	public RenderHoverScooter(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHoverScooter entity) {
		// TODO Auto-generated method stub
		return new ResourceLocation("textures/entity/minecart.png");
	}

	@Override
	public void doRender(EntityHoverScooter entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180, 1, 0, 0);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.model.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
		/*Logger.info(String.format("%s, %s, %s", scooter.posX, scooter.posY, scooter.posZ));
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.bindEntityTexture(scooter);
		GlStateManager.translate(scooter.posX, scooter.posY, scooter.posZ);
		GlStateManager.rotate(entityYaw, 0, 1, 0);
		model.render(scooter, 0, -0.1F, 0, 0, 0, 0.0625F);

		super.doRender(scooter, x, y, z, entityYaw, partialTicks);
		GlStateManager.popMatrix();*/
	}
}