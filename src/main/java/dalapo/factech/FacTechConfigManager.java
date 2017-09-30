package dalapo.factech;

import java.util.HashMap;
import java.util.Map;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;

public class FacTechConfigManager {
	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_MACHINES = "machines";
	
	public static boolean genCopper = true;
	public static boolean genNickel = true;
	
	public static Map<String, Float> salvageChances = new HashMap<>();
	public static Map<String, Integer> minOperations = new HashMap<>();
	
	// TODO: Use arrays for multiple parts
	private static enum MachineDefaults {
		sluice("River Grate", 0.8F, 8);
		String name;
		float salvageChance;
		int minOperations;
		private MachineDefaults(String name, float salvageChance, int minOperations)
		{
			this.name = name;
			this.salvageChance = salvageChance;
			this.minOperations = minOperations;
		}
	}
	private static final String[] machineNames = {
		"River Grate",
		"Chop Saw",
		"Grindstone",
		"Metal Cutter",
		"Drill Grinder",
		"Woodcutter",
		"Magnetizer",
		"Electric Furnace",
		"Centrifuge",
		"Crucible",
		"Potion Mixer",
		"Circuit Scribe",
		"Autominer",
		"Industrial Electroplater",
		"Mob Disassembler",
		"Fluid Drill",
		"Fluid Agitator",
		"Refrigeration Unit",
		"Compression Chamber",
		"Core Charger",
		"Stabilization Table",
		"Biosynthesis Unit",
		"Mob Disruptor",
		"Negative Ion Disperser",
		"Tesla Coil"
	};
	
	public static void readConfig()
	{
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initMachineConfig(cfg);
		}
		catch (Exception e)
		{
			Logger.error("Error reading config: " + e);
		}
		finally
		{
			if (cfg.hasChanged()) cfg.save();
		}
	}
	
	public static void initGeneralConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		genCopper = cfg.getBoolean("genCopper", CATEGORY_GENERAL, true, "Set to false to disable copper ore generation");
		genNickel = cfg.getBoolean("genNickel", CATEGORY_GENERAL, true, "Set to false to disable nickel ore generation");
	}
	
	private static void addToMaps(String machine, float salvageChance, int minOps)
	{
		salvageChances.put(machine, salvageChance);
		minOperations.put(machine, minOps);
	}
	
	public static void initMachineConfig(Configuration cfg)
	{
		
	}
}