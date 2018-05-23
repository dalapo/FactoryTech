package dalapo.factech.render.tesr;

import dalapo.factech.block.BlockBase;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityAnimatedModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class TesrAnimatedModel extends TileEntitySpecialRenderer<TileEntityAnimatedModel>
{
	private boolean isDirectional;
	public TesrAnimatedModel(boolean directional)
	{
		this.isDirectional = directional;
	}
	
	@Override
	public void render(TileEntityAnimatedModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.5, y, z+0.5);
		if (isDirectional)
		{
			try {
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
					GlStateManager.rotate(-90, 1, 0, 0);
					break;
				case DOWN:
					GlStateManager.rotate(90, 1, 0, 0);
				default:
					break;
				}
			}
			catch (IllegalArgumentException e)
			{
				GlStateManager.popMatrix();
				return;
			}
		}
		
		IAnimatedModel block = (IAnimatedModel)te.getBlockType();
		block.animate(this, te, x, y, z, partialTicks, destroyStage, alpha);
		GlStateManager.popMatrix();
	}
}