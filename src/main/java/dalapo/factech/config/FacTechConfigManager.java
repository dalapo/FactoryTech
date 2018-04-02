package dalapo.factech.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalapo.factech.CommonProxy;
import dalapo.factech.auxiliary.MachinePart;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class FacTechConfigManager {
	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_CLIENT = "client";
	private static final String CATEGORY_MACHINES = "machines";
	private static final String CATEGORY_PARTS = "machineParts";
	
	public static int maxDecoCoilRange = 12;
	public static boolean genCopper = true;
	public static boolean genNickel = true;
	public static boolean convertParts = true;
	public static boolean allowMachineEnchanting = true;
	public static boolean restrictWaterCollectorBiomes = true;
	public static boolean disassemblePlayers = false;
	public static int maxPartStackSize = 4;
	public static boolean doTesrs = true;
	public static int maxWoodcutterRecursions = 256;
	public static float quarryChunkChance = 0.167F;
	public static int copperMin = 0;
	public static int copperMax = 48;
	public static int nickelMin = 0;
	public static int nickelMax = 24;
	public static float fluidDrillMultiplier = 1.0F;
	public static String grateBiomes;
	public static Map<Class<? extends TileEntityMachine>, MachinePart[]> allParts = new HashMap<>();
	
	public static String encodeMachinePart(MachinePart part)
	{
		return String.format("%s:%s:%s:%s:%s", part.id.getName(), part.getMinOperations(), part.getBaseChance(), part.getIncrease(), part.getSalvageChance());
	}
	
	private static MachinePart decodeMachinePart(String part) throws NumberFormatException, ArrayIndexOutOfBoundsException
	{
		String[] arr = part.split(":");
		PartList id = PartList.getPartFromString(arr[0]);
		int min = Integer.parseInt(arr[1]);
		float base = Float.parseFloat(arr[2]);
		float inc = Float.parseFloat(arr[3]);
		float salv = Float.parseFloat(arr[4]);
		return new MachinePart(id, base, inc, salv, min);
	}
	
	public static void initMultipliers(Configuration cfg)
	{
		cfg.setCategoryComment("tileentityagitator", "Usage: minUses:breakChance:increase:salvageChance");
		for (MachineDefaults def : MachineDefaults.values())
		{
			String name = def.name();
			MachinePart[] parts = new MachinePart[def.partsNeeded.length];
			for (int i=0; i<def.partsNeeded.length; i++)
			{
				// Syntax: "GEAR:6,0.2,1.4,0.7" - minUses, breakChance, increase, salvageChance
				String encoded = cfg.getString(def.partsNeeded[i].id.getName(), def.name(), encodeMachinePart(def.partsNeeded[i]), "");
				MachinePart part = decodeMachinePart(encoded);
				parts[i] = part;
			}
			allParts.put(def.clazz, parts);
		}
	}
	
	public static void readConfig()
	{
		Configuration cfg = CommonProxy.config;
		Configuration cfgMachine = CommonProxy.machineConfig;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initClientConfig(cfg);
			initMachineConfig(cfg);
			initMultipliers(cfgMachine);
		}
		catch (Exception e)
		{
			Logger.error("Error reading config: " + e);
		}
		finally
		{
			if (cfg.hasChanged()) cfg.save();
			if (cfgMachine.hasChanged()) cfgMachine.save();
		}
	}
	
	public static void initGeneralConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		genCopper = cfg.getBoolean("genCopper", CATEGORY_GENERAL, true, "Set to false to disable copper ore generation");
		copperMin = cfg.getInt("copperMin", CATEGORY_GENERAL, 0, 0, 256, "Minimum y-level that copper can generate");
		copperMax = cfg.getInt("copperMax", CATEGORY_GENERAL, 48, 0, 256, "Maximum y-level that copper can generate");
		genNickel = cfg.getBoolean("genNickel", CATEGORY_GENERAL, true, "Set to false to disable nickel ore generation");
		nickelMin = cfg.getInt("nickelMin", CATEGORY_GENERAL, 0, 0, 256, "Minimum y-level that nickel can generate");
		nickelMax = cfg.getInt("nickelMax", CATEGORY_GENERAL, 24, 0, 256, "Minimum y-level that nickel can generate");
		fluidDrillMultiplier = cfg.getFloat("fluidDrillMultiplier", CATEGORY_GENERAL, 1, 0.01F, 10, "Fluid Extraction Drill output multiplier");
		maxDecoCoilRange = cfg.getInt("maxDecoCoilRange", CATEGORY_GENERAL, 12, 1, Integer.MAX_VALUE, "Maximum distance in blocks that decorative coils can connect to each other");
	}
	
	public static void initClientConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_CLIENT, "Client configuration");
		doTesrs = cfg.getBoolean("doTESRs", CATEGORY_CLIENT, true, "Set to false to disable machine animations; this will make the mod uglier but may increase your framerate");
	}
	
	public static void initMachineConfig(Configuration cfg)
	{
		allowMachineEnchanting = cfg.getBoolean("allowGrinderEnchanting", CATEGORY_MACHINES, true, "Set to false to disable the Grindstone enchanting tools and weapons");
		disassemblePlayers = cfg.getBoolean("disassemblePlayers", CATEGORY_MACHINES, false, "Set to true to allow the Mob Disassembler to attack players");
		grateBiomes = cfg.getString("riverGrateWhitelist", CATEGORY_MACHINES, "7", "A list of biome IDs the River Grate is allowed to function in, separated by commas. Enter \"all\" instead to allow every biome.");
		restrictWaterCollectorBiomes = cfg.getBoolean("restrictWaterCollectorBiomes", CATEGORY_MACHINES, true, "Set to false to allow the Water Collector to run in any biome");
		maxWoodcutterRecursions = cfg.getInt("maxWoodcutterRecursions", CATEGORY_MACHINES, 256, 1, 65536, "Maximum number of logs the Woodcutter can break at once. Higher values may cause lag spikes or crashes.");
		maxPartStackSize = cfg.getInt("maxPartStackSize", CATEGORY_MACHINES, 4, 1, 64, "Maximum stack size for parts");
		quarryChunkChance = cfg.getFloat("quarryChunkChance", CATEGORY_MACHINES, 0.167F, 0, 1, "Chance per block broken for Mining Machines to output an Ore Chunk");
		
		String[] stacks = cfg.getStringList("deepDrillCustomOres", CATEGORY_MACHINES, new String[] {}, "Add extra ores to the Terraneous Extractor table according to their Ore Dict entry and weight. Coal ore has weight 2.0, Diamond has weight 0.1. Example: oreTin:1.5");
		for (String str : stacks)
		{
			String[] entry = str.split(":");
			if (!OreDictionary.getOres(entry[0]).isEmpty())
			{
				try {
					MachineRecipes.DEEP_DRILL.add(new Pair<ItemStack, Double>(OreDictionary.getOres(entry[0]).get(0), Double.parseDouble(entry[1])));
				}
				catch (NumberFormatException e) { continue; }
			}
		}
	}
}