package dalapo.factech.render.tesr;

import java.util.function.Function;

import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.PartList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntitySaw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public abstract class TesrMachine<T extends TileEntityBase> extends TileEntitySpecialRenderer<T> {

	private IModel model;
	private IBakedModel bakedModel;
	protected boolean isDirectional;
	
	public TesrMachine(boolean directional)
	{
		isDirectional = directional;
	}
	
	// Should be overridden if necessary
	protected String getModelName()
	{
		return null;
	}
	
	protected IBakedModel getBakedModel()
	{
		if (getModelName() == null) return null;
		try {
			IModel model = OBJLoader.INSTANCE.loadModel(new ResourceLocation(NameList.MODID, String.format("models/block/%s.obj", getModelName())));
			IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK, FacTesrHelper::getAtlasFromLocation);
			return bakedModel;
		}
		catch (Exception e)
		{
			throw new RuntimeException();
		}
	}
	
	public abstract void doRender(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha);
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
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
				return;
			}
		}
		doRender(te, x, y, z, partialTicks, destroyStage, alpha);
		GlStateManager.popMatrix();
	}
}