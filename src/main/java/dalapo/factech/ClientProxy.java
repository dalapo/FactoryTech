package dalapo.factech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dalapo.factech.config.MachineDefaults;
import dalapo.factech.gui.handbook.GuiHandbook;
import dalapo.factech.gui.handbook.HandbookEntry;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.FacEntityRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.init.TileRegistry;
import dalapo.factech.reference.MachineInfoList;
import dalapo.factech.reference.NameList;
import dalapo.factech.render.BakedModelLoader;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
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
	String language;
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
		language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
		initHandbookPages();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt)
	{
		super.postInit(evt);
		language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
		BlockRegistry.initInvModels();
	}
	
	void initHandbookPages()
	{
		Logger.info(language);
		String[] names = new String[] {"basic", "machine", "part", "tool", "automation", "resource", "misc"};
		for (int i=0; i<names.length; i++)
		{
			GuiHandbook.entries.add(new ArrayList<>());
			IResource textRes;
			try {
				textRes = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(NameList.MODID, "text/" + language + "/" + names[i] + ".txt"));
			}
			catch (IOException e)
			{
				Logger.error(String.format("Language %s not found in text folder; defaulting to English", language));
				try {
					textRes = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(NameList.MODID, "text/en_us/" + names[i] + ".txt"));
				}
				catch (IOException ex)
				{
					Logger.error(String.format("WARNING: Text file %s.txt not found in text directory; game is likely to crash", names[i]));
					return;
				}
			}
			int numEntries = 0;
			Scanner file = new Scanner(textRes.getInputStream());
			String workingTitle = "";
			String page = "";
			String machineClass = "";
			List<String> text = new ArrayList<String>();
			List<IRecipe> recipes = new ArrayList<IRecipe>();
			while (file.hasNextLine())
			{
				String s = file.nextLine();
				if (s.startsWith("#")) continue;
				if (s.startsWith("$title"))
				{
					numEntries++;
					workingTitle = s.substring(7);
				}
				else if (s.startsWith("$recipe"))
				{
					recipes.add(CraftingManager.getRecipe(new ResourceLocation(NameList.MODID, s.substring(8))));
				}
				else if (s.startsWith("$machine"))
				{
					machineClass = s.substring(9);
				}
				else if (!s.startsWith("$"))
				{
					page += s + '\n';
				}
				else if (s.startsWith("$page"))
				{
					text.add(page);
					page = "";
				}
				else if (s.startsWith("$brief"))
				{
					int firstFlag = s.indexOf('%');
					int secondFlag = s.indexOf('%', firstFlag+1);
					String machineName = s.substring(firstFlag + 1, secondFlag);
					MachineInfoList.dictionary.put(machineName, s.substring(secondFlag + 2));
				}
				else if (s.equals("$end"))
				{
					text.add(page);
					GuiHandbook.entries.get(i).add(new HandbookEntry(workingTitle, text, recipes, machineClass));
					page = "";
					machineClass = "";
					text.clear();
					recipes.clear();
				}
			}
			GuiHandbook.setPageCount(i, numEntries == 0 ? numEntries : numEntries - 1);
			file.close();
		}
		
		GuiHandbook.initBackgrounds();
	}
	@SubscribeEvent
	public void initTextures(TextureStitchEvent.Pre evt)
	{
		ModFluidRegistry.initTextures(evt);
	}
	
	@SubscribeEvent
	public void initModels(ModelRegistryEvent evt)
	{
		BlockRegistry.initModels();
		ItemRegistry.initModels();
		TileRegistry.initTESRs();
	}
}