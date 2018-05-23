package dalapo.factech.init;

import dalapo.factech.FactoryTech;
import dalapo.factech.entity.EntityHoverScooter;
import dalapo.factech.entity.EntityPressureGunShot;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import dalapo.factech.render.RenderPressureGunShot;
import dalapo.factech.render.entity.RenderHoverScooter;
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
		int id = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(NameList.MODID), EntityPressureGunShot.class, "pressuregunshot", id++, FactoryTech.instance, 64, 3, false);
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHoverScooter.class, new IRenderFactory<EntityHoverScooter>()
		{
			public Render<EntityHoverScooter> createRenderFor(RenderManager manager)
			{
				return new RenderHoverScooter(manager);
			}
		});
	}
}