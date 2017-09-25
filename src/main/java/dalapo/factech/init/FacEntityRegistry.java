package dalapo.factech.init;

import dalapo.factech.FactoryTech;
import dalapo.factech.entity.EntityPressureGunShot;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import dalapo.factech.render.RenderPressureGunShot;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FacEntityRegistry
{
	private FacEntityRegistry() {}
	
	public static void init()
	{
		EntityRegistry.registerModEntity(new ResourceLocation(NameList.MODID, "pressureshot"), EntityPressureGunShot.class, "pressureshot", 0, FactoryTech.instance, 64, 1, true, 0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderers()
	{
		Logger.info("Registering entity renderers");
		RenderingRegistry.registerEntityRenderingHandler(EntityPressureGunShot.class, new IRenderFactory<EntityPressureGunShot>()
		{
			public Render<EntityPressureGunShot> createRenderFor(RenderManager manager)
			{
				return new RenderPressureGunShot(manager);
			}
		});
	}
}