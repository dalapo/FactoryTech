package dalapo.factech;

import java.io.IOException;
import java.util.Scanner;

import dalapo.factech.gui.GuiHandbook;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.FacEntityRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.init.TileRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.render.BakedModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		super.preInit(evt);
		OBJLoader.INSTANCE.addDomain(NameList.MODID);
		ModelLoaderRegistry.registerLoader(new BakedModelLoader());
		FacEntityRegistry.registerEntityRenderers();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt)
	{
		super.init(evt);
		
		initHandbookPages();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt)
	{
		super.postInit(evt);
		BlockRegistry.initInvModels();
	}
	
	private void initHandbookPages()
	{
		String[] names = new String[] {"basic", "machine", "part", "tool", "automation", "resource", "misc"};
		for (int i=0; i<names.length; i++)
		{
			IResource textRes;
			try {
				textRes = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(NameList.MODID, "text/" + names[i] + ".txt"));
			}
			catch (IOException e)
			{
				Logger.error(String.format("Text file %s.txt not found; game is likely to crash", names[i]));
				return;
			}
			int numEntries = 0;
			Scanner file = new Scanner(textRes.getInputStream());
			while (file.hasNextLine())
			{
				if (file.nextLine().startsWith("$title")) numEntries++;
			}
			GuiHandbook.setPageCount(i, numEntries == 0 ? numEntries : numEntries - 1);
			file.close();
		}
	}
	@SubscribeEvent
	public void initTextures(TextureStitchEvent.Pre evt)
	{
		ModFluidRegistry.initTextures(evt);
	}
	
	@SubscribeEvent
	public void initBlockModels(ModelRegistryEvent evt)
	{
		BlockRegistry.initModels();
		ItemRegistry.initModels();
		TileRegistry.initTESRs();
	}
}