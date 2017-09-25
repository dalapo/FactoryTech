package dalapo.factech.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import dalapo.factech.block.BlockPipe;
import dalapo.factech.reference.NameList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class PipeBakedModel implements IBakedModel {
	public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation(NameList.MODID + ":pipe");

	private TextureAtlasSprite sprite;
	private VertexFormat format;
	public PipeBakedModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> textureGetter)
	{
		this.format = format;
		sprite = textureGetter.apply(new ResourceLocation(NameList.MODID, "blocks/pipe"));
	}
	
	private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z,
			float u, float v)
	{
		for (int e=0; e<format.getElementCount(); e++)
		{
			switch(format.getElement(e).getUsage())
			{
			case POSITION:
				builder.put(e, (float)x, (float)y, (float)z, 1.0F);
				break;
			case COLOR:
				builder.put(e, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
			case UV:
				if (format.getElement(e).getIndex() == 0)
				{
					u = sprite.getInterpolatedU(u);
					v = sprite.getInterpolatedV(v);
					builder.put(e, u, v, 0.0F, 1.0F);
				}
				break;
			case NORMAL:
				builder.put(e, (float)normal.x, (float)normal.y, (float)normal.z, 0F);
				break;
				default:
					builder.put(e);
					break;
			}
		}
	}
	
	private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite)
	{
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
		
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		
		putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0);
		putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16);
		putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16);
		putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0);
		
		return builder.build();
	}
	
	private BakedQuad createQuad(List<Vec3d> vectors, TextureAtlasSprite sprite)
	{
		if (vectors.size() < 4) return null;
		return createQuad(vectors.get(0), vectors.get(1), vectors.get(2), vectors.get(3), sprite);
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (side != null) return Collections.EMPTY_LIST;
		
		IExtendedBlockState stateEx = (IExtendedBlockState)state;
		Boolean north = stateEx.getValue(BlockPipe.NORTH);
		Boolean south = stateEx.getValue(BlockPipe.SOUTH);
		Boolean east = stateEx.getValue(BlockPipe.EAST);
		Boolean west = stateEx.getValue(BlockPipe.WEST);
		Boolean up = stateEx.getValue(BlockPipe.UP);
		Boolean down = stateEx.getValue(BlockPipe.DOWN);
		List<BakedQuad> quads = new ArrayList<>();
		double o = 0.4;
		List<Vec3d> general = new ArrayList<>();
		
		// Code copy-pasted from McJty's tutorial.
		// Is there not a way to generalize this?
		// Yes there is!
		// TODO: Make an abstract model (read: collection of points) and rotate it around the relevant axis,
		if (up) {
            quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
            quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1, 1 - o), new Vec3d(o, 1, o), new Vec3d(o, 1 - o, o), sprite));
            quads.add(createQuad(new Vec3d(o, 1, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
            quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(o, 1, 1 - o), sprite));
        } else {
            quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
        }

        if (down) {
            quads.add(createQuad(new Vec3d(1 - o, 0, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 0, 1 - o), sprite));
            quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, o, o), new Vec3d(o, 0, o), sprite));
            quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, 0, o), new Vec3d(o, 0, o), sprite));
            quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(1 - o, 0, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
        } else {
            quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
        }

        if (east) {
            quads.add(createQuad(new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1, 1 - o, o), new Vec3d(1 - o, 1 - o, o), sprite));
            quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1, o, o), new Vec3d(1, o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
            quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1, 1 - o, o), new Vec3d(1, o, o), new Vec3d(1 - o, o, o), sprite));
            quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1, o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
        } else {
            quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
        }

        if (west) {
            quads.add(createQuad(new Vec3d(0, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(0, 1 - o, o), sprite));
            quads.add(createQuad(new Vec3d(0, o, o), new Vec3d(o, o, o), new Vec3d(o, o, 1 - o), new Vec3d(0, o, 1 - o), sprite));
            quads.add(createQuad(new Vec3d(0, 1 - o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), new Vec3d(0, o, o), sprite));
            quads.add(createQuad(new Vec3d(0, o, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(0, 1 - o, 1 - o), sprite));
        } else {
            quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), sprite));
        }

        if (north) {
            quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 0), new Vec3d(o, 1 - o, 0), sprite));
            quads.add(createQuad(new Vec3d(o, o, 0), new Vec3d(1 - o, o, 0), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
            quads.add(createQuad(new Vec3d(1 - o, o, 0), new Vec3d(1 - o, 1 - o, 0), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), sprite));
            quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, 1 - o, 0), new Vec3d(o, o, 0), sprite));
        } else {
            quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
        }
        if (south) {
            quads.add(createQuad(new Vec3d(o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
            quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, o, 1), new Vec3d(o, o, 1), sprite));
            quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, o, 1), sprite));
            quads.add(createQuad(new Vec3d(o, o, 1), new Vec3d(o, 1 - o, 1), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
        } else {
            quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
        }

        return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGui3d() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	@Deprecated
	public ItemCameraTransforms getItemCameraTransforms() {
		// TODO Auto-generated method stub
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(
			TransformType cameraTransformType) {
		// TODO Auto-generated method stub
		return null;
	}
}