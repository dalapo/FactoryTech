package dalapo.factech.tileentity.render;

import java.util.function.Function;

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

public abstract class TesrMachine<T extends TileEntityBase> extends TileEntitySpecialRenderer<T> {

	private IModel model;
	private IBakedModel bakedModel;
	protected boolean isDirectional;
	
	public TesrMachine(boolean directional)
	{
		isDirectional = directional;
	}
	
	protected abstract String getModelName();
	
	protected IBakedModel getBakedModel()
	{
		if (getModelName() == null) return null;
		if (model == null)
		{
			try
			{
				model = OBJLoader.INSTANCE.loadModel(new ResourceLocation(NameList.MODID, String.format("models/block/%s.obj", getModelName())));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK, new Function<ResourceLocation, TextureAtlasSprite>() {
				@Override
				public TextureAtlasSprite apply(ResourceLocation location)
				{
					return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
				}
			});
		return bakedModel;
	}
	
	public abstract void doRender(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha);
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
		if (isDirectional)
		{
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
			default:
				break;
			}
		}
		doRender(te, x, y, z, partialTicks, destroyStage, alpha);
		GlStateManager.popMatrix();
	}
}