package dalapo.factech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;

public class FacTechConfigManager {
	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_CLIENT = "client";
	private static final String CATEGORY_MACHINES = "machines";
	private static final String CATEGORY_PARTS = "machineParts";
	
	public static boolean genCopper = true;
	public static boolean genNickel = true;
	public static boolean convertParts = true;
	public static boolean allowMachineEnchanting = true;
	public static boolean disassemblePlayers = false;
	public static boolean doTesrs = true;
	public static String[] grateBiomes;
	
	// This is essentially a 3D ragged array. Fun times!
	public static List<float[][]> machineMultipliers = new ArrayList<float[][]>();
	
	public static void initMultipliers(Configuration cfg)
	{
		for (int i=0; i<MachineInfo.values().length; i++)
		{
			machineMultipliers.add(new float[MachineInfo.values()[i].numParts][2]);
			for (int j=0; j<machineMultipliers.get(i).length; j++)
			{
				for (int k=0; k<machineMultipliers.get(i)[j].length; k++)
				{
					String name = MachineInfo.values()[i].name() + ":" + j + ":" + (k == 0 ? "lifespan":"salvage");
					String descriptor = MachineInfo.values()[i].name + " Part " + j + (k == 0 ? ": Lifespan multiplier" : ": Salvage chance multiplier");
					float f = cfg.getFloat(name, CATEGORY_PARTS, 1.0F, 0.0F, 1000.0F, descriptor);
					machineMultipliers.get(i)[j][k] = f;
				}
			}
		}
	}
	
	public static float[][] getMachineMultiplier(Class<? extends TileEntityMachine> clazz)
	{
		return machineMultipliers.get(MachineInfo.getFromTEClass(clazz).ordinal());
	}
	
	private static enum MachineInfo
	{
		GRATE("River Grate", 1, TileEntitySluice.class),
		SAW("Chop Saw", 2, TileEntitySaw.class),
		GRINDSTONE("Grindstone", 1, TileEntityGrindstone.class),
		METALCUTTER("Metal Cutter", 2, TileEntityMetalCutter.class),
		OREDRILL("Drill Grinder", 3, TileEntityOreDrill.class),
		WOODCUTTER("Woodcutter", 2, TileEntityWoodcutter.class),
		MAGNETIZER("Magnetizer", 2, TileEntityMagnetizer.class),
		ELECFURNACE("Electric Furnace", 2, TileEntityHTFurnace.class),
		CENTRIFUGE("Centrifuge", 3, TileEntityCentrifuge.class),
		CRUCIBLE("Crucible", 2, TileEntityCrucible.class),
		POTIONMIXER("Potion Mixer", 2, TileEntityPotionMixer.class),
		CIRCUITSCRIBE("Circuit Scribe", 3, TileEntityCircuitScribe.class),
		AUTOMINER("Autominer", 3, TileEntityAutoMiner.class),
		ELECTROPLATER("Electroplater", 3, TileEntityElectroplater.class),
		DISASSEMBLER("Mob Disassembler", 3, TileEntityDisassembler.class),
		FLUIDDRILL("Fluid Drill", 3, TileEntityFluidDrill.class),
		AGITATOR("Agitator", 3, TileEntityAgitator.class),
		FRIDGE("Refrigerator", 3, TileEntityRefrigerator.class),
		COMPRESSOR("Compression Chamber", 3, TileEntityCompressionChamber.class),
		TEMPERER("Tempering Oven", 3, TileEntityTemperer.class),
		CORECHARGER("Core Charger", 4, TileEntityCoreCharger.class),
		SPAWNER("Biosynthesizer", 4, TileEntitySpawner.class),
		DISRUPTOR("Disruptor", 4, TileEntityDisruptor.class),
		DISPERSER("Neg. Ion Disperser", 4, TileEntityIonDisperser.class),
		TESLACOIL("Tesla Coil", 2, TileEntityTeslaCoil.class);

		private MachineInfo(String name, int numParts, Class<? extends TileEntityMachine> clazz)
		{
			type = clazz;
			this.name = name;
			this.numParts = numParts;
		}
		
		public static MachineInfo getFromTEClass(Class<? extends TileEntityMachine> clazz)
		{
			for (MachineInfo info : values())
			{
				if (info.type.equals(clazz)) return info;
			}
			return null;
		}
		
		public Class<? extends TileEntityMachine> type;
		public String name;
		public int numParts;
	}
	
	public static void readConfig()
	{
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initClientConfig(cfg);
			initMachineConfig(cfg);
			initMultipliers(cfg);
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
	
	public static void initClientConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_CLIENT, "Client configuration");
		doTesrs = cfg.getBoolean("doTESRs", CATEGORY_CLIENT, true, "Set to false to disable machine animations; this will make the mod uglier but may increase your framerate");
	}
	
	public static void initMachineConfig(Configuration cfg)
	{
		allowMachineEnchanting = cfg.getBoolean("allowGrinderEnchanting", CATEGORY_MACHINES, true, "Set to false to disable the Grindstone enchanting tools and weapons");
		disassemblePlayers = cfg.getBoolean("disassemblePlayers", CATEGORY_MACHINES, false, "Set to true to allow the Mob Disassembler to attack players");
		grateBiomes = cfg.getStringList("riverGrateWhitelist", CATEGORY_MACHINES, new String[] {"River"}, "Biomes that the River Grate is allowed to function in");
	}
}