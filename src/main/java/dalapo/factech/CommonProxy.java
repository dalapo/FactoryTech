package dalapo.factech;

import java.io.File;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.gui.FacTechGuiHandler;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.DictRegistry;
import dalapo.factech.init.FacEntityRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.init.RecipeRegistry;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.init.TileRegistry;
import dalapo.factech.init.WorldGenRegistry;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.reference.PartList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class CommonProxy {
	
	public static Configuration config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		File directory = evt.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "factorytech.cfg"));
		FacTechConfigManager.readConfig();
		TabRegistry.init();
		BlockRegistry.init();
		ItemRegistry.init();
		ModFluidRegistry.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(FactoryTech.instance, new FacTechGuiHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt)
	{
		DictRegistry.registerOreDictEntries();
		TileRegistry.init();
		RecipeRegistry.init();
		FacEntityRegistry.init();
		WorldGenRegistry.init();
		PacketHandler.registerMessages("factech");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt)
	{
		Logger.info("Entered postInit");
		
		MachineRecipes.addOreDictRecipes();
		MachineRecipes.importFurnaceRecipes();
		if (config.hasChanged()) config.save();
	}
}
