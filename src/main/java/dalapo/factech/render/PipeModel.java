package dalapo.factech.render;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import dalapo.factech.reference.NameList;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.model.animation.IClip;

public class PipeModel implements IModel {

	@Override
	public Collection<ResourceLocation> getDependencies() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		// TODO Auto-generated method stub
		return ImmutableSet.of(new ResourceLocation(NameList.MODID, "blocks/pipe"));
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new PipeBakedModel(state, format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public Optional<? extends IClip> getClip(String name) {
		return null;
	}

	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel smoothLighting(boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel gui3d(boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel uvlock(boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) {
		// TODO Auto-generated method stub
		return null;
	}

}
