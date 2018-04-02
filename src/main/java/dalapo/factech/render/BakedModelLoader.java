package dalapo.factech.render;

import dalapo.factech.reference.NameList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class BakedModelLoader implements ICustomModelLoader {
	// TODO: Make this properly object-oriented instead of hardcoding everything
	// Seriously.
	public static final PipeModel PIPE_MODEL = new PipeModel();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		// NO-OP
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(NameList.MODID) &&
				"pipe".equals(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception
	{
		return PIPE_MODEL;
	}
}