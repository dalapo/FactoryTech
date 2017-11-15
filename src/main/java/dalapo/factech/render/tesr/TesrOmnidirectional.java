package dalapo.factech.render.tesr;

import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public abstract class TesrOmnidirectional<T extends TileEntityBase> extends TileEntitySpecialRenderer<T>
{
	public abstract void doRender(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha);
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
		EnumFacing direction = te.getWorld().getBlockState(te.getPos()).getValue(StateList.directions);
		switch (direction)
		{
		case EAST:
			GlStateManager.rotate(90, 0, 1, 0);
			break;
		case WEST:
			GlStateManager.rotate(270, 0, 1, 0);
			break;
		case NORTH:
			GlStateManager.rotate(180, 0, 1, 0);
			break;
		case SOUTH:
			break;
		case UP:
			GlStateManager.rotate(90, 1, 0, 0);
			break;
		case DOWN:
			GlStateManager.rotate(270, 1, 0, 0);
			break;
		default:
			break;
		}
		doRender(te, x, y, z, partialTicks, destroyStage, alpha);
		GlStateManager.popMatrix();
	}
}