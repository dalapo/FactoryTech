package dalapo.factech.init;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.FactoryTech;
import dalapo.factech.block.BlockBase;
import dalapo.factech.block.BlockBlockBreaker;
import dalapo.factech.block.BlockBottomHatch;
import dalapo.factech.block.BlockBricks;
import dalapo.factech.block.BlockComparatorExact;
import dalapo.factech.block.BlockConveyor;
import dalapo.factech.block.BlockDecoCoil;
import dalapo.factech.block.BlockElevator;
import dalapo.factech.block.BlockFluidGiver;
import dalapo.factech.block.BlockHatch;
import dalapo.factech.block.BlockInventoryDirectional;
import dalapo.factech.block.BlockInventorySensor;
import dalapo.factech.block.BlockItemRedis;
import dalapo.factech.block.BlockMachine;
import dalapo.factech.block.BlockMetal;
import dalapo.factech.block.BlockOre;
import dalapo.factech.block.BlockOreStorage;
import dalapo.factech.block.BlockPipe;
import dalapo.factech.block.BlockRSNotifier;
import dalapo.factech.block.BlockScaffold;
import dalapo.factech.block.BlockSmokestack;
import dalapo.factech.block.BlockInventoryDirectional;
import dalapo.factech.block.BlockTENoDir;
import dalapo.factech.block.BlockTank;
import dalapo.factech.helper.Logger;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistry {
	public static List<BlockBase> blocks = new ArrayList<BlockBase>();
	
	public static BlockInventoryDirectional stackmover;
	public static BlockInventoryDirectional filtermover;
	public static BlockInventoryDirectional bulkmover;
	public static BlockInventoryDirectional autopuller;
	public static BlockConveyor conveyor;
	public static BlockElevator elevator;
	public static BlockBottomHatch bottomHatch;
	public static BlockHatch hatch;
	public static BlockPipe pipe;
	public static BlockTENoDir itemRedis;
	public static BlockTENoDir tank;
	public static BlockInventoryDirectional fluidPuller;
	public static BlockFluidGiver fluidDebug;
	public static BlockInventoryDirectional itemPusher;
	public static BlockInventoryDirectional sequenceplacer;
	public static BlockBase redNotifier;
	public static BlockBlockBreaker blockbreaker;
	public static BlockInventorySensor invSensor;
	
//	public static BlockMachine wirecutter;
	public static BlockMachine potionmixer;
//	public static BlockMachine aerolyzer;
	public static BlockMachine htfurnace;
	public static BlockMachine propfurnace;
	public static BlockMachine disruptor;
	public static BlockMachine oredrill;
	public static BlockMachine metalCutter;
	public static BlockMachine autocrafter;
	public static BlockMachine circuitscribe;
	public static BlockMachine saw;
	public static BlockMachine crucible;
	public static BlockMachine compressionChamber;
	public static BlockMachine grindstone;
	public static BlockMachine centrifuge;
	public static BlockMachine magCentrifuge;
	public static BlockMachine fluiddrill;
	public static BlockMachine agitator;
	public static BlockMachine sluice;
	public static BlockMachine autominer;
	public static BlockMachine electroplater;
	public static BlockMachine charger;
	public static BlockMachine temperer;
	public static BlockMachine stabilizer;
	public static BlockMachine magnetizer;
	public static BlockMachine spawner;
	public static BlockMachine disassembler;
	public static BlockMachine refrigerator;
	public static BlockMachine woodcutter;
	public static BlockMachine ionDisperser;
	public static BlockMachine teslaCoil;
	public static BlockMachine deepdrill;
	public static BlockMachine planter;
	
	public static BlockTENoDir energizer;
	public static BlockTENoDir magnet;
	public static BlockTENoDir decocoil;
	public static BlockTENoDir watercollector;
	public static BlockTENoDir crate;
	public static BlockTENoDir buffercrate;
	public static BlockTENoDir interceptor;
	
	public static BlockBase ore;
	public static BlockBase oreblock;
	public static BlockBase bricks;
	public static BlockBase smokestack;
	public static BlockBase decorative_metal;
	public static BlockBase scaffold;
	
	public static void init()
	{
		Logger.info("Entered BlockRegistry.init");
		
		// Automation
		blocks.add(stackmover = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.WOOD, "stackmover", "stackmover", false, -1).setHasInformation());
		blocks.add(filtermover = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.IRON, "filtermover", "filtermover", false, 12).setHasInformation());
		blocks.add(bulkmover = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.IRON, "bulkmover", "bulkmover", false, 1).setHasInformation());
		blocks.add(autopuller = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.IRON, "autopuller", "autopuller", false).enableRotating().setHasInformation());
		blocks.add(itemPusher = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.IRON, "itempusher", "itempusher", true, 13).setHasInformation());
		blocks.add(sequenceplacer = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.WOOD, "sequenceplacer", "sequenceplacer", false, 9).setHasInformation());
//		blocks.add(exactComparator = new BlockComparatorExact(Material.CIRCUITS, "comparatorEx"));
		blocks.add(watercollector = (BlockTENoDir) new BlockTENoDir(Material.WOOD, "watercollector").setHasInformation().setHardness(2F));
		blocks.add(crate = (BlockTENoDir) new BlockTENoDir(Material.WOOD, "crate", 7).setHasInformation().setHardness(2F));
		blocks.add(conveyor = (BlockConveyor) new BlockConveyor(Material.IRON, "conveyor", true).setHasInformation().setHardness(1F));
		blocks.add(buffercrate = (BlockTENoDir)new BlockTENoDir(Material.IRON, "buffercrate", 7).setHasInformation().setHardness(2.5F));
		blocks.add(elevator = (BlockElevator) new BlockElevator(Material.IRON, "elevator", true).setHasInformation());
		blocks.add(bottomHatch = (BlockBottomHatch) new BlockBottomHatch(Material.WOOD, "bottomhatch", true).setHasInformation());
		blocks.add(hatch = (BlockHatch) new BlockHatch(Material.IRON, "hatch").setHasInformation().setHardness(2F));
		blocks.add(redNotifier = (BlockRSNotifier)new BlockRSNotifier(Material.WOOD, "rednotifier").setHasInformation().setHardness(2F));
		blocks.add(blockbreaker = (BlockBlockBreaker)new BlockBlockBreaker(Material.ROCK, "blockbreaker", "blockbreaker", false).setHasInformation().setHardness(2F));
		blocks.add(invSensor = (BlockInventorySensor)new BlockInventorySensor(Material.IRON, "inventorysensor", "inventorysensor", false, 11).setHasInformation().setHardness(2F));
		blocks.add(interceptor = (BlockTENoDir)new BlockTENoDir(Material.IRON, "interceptor", 1).setHasInformation().setHardness(2F));
//		blocks.add(mecharm = new BlockInventoryDirectional(Material.IRON, "mecharm", "mecharm", false, 5));
		
		// Machines
		blocks.add(oredrill = (BlockMachine) new BlockMachine(Material.IRON, "oredrill", "oredrill", 0).setHasInformation());
		blocks.add(metalCutter = (BlockMachine) new BlockMachine(Material.IRON, "metalcutter", "metalcutter", 0).setHasInformation());
		blocks.add(autocrafter = (BlockMachine) new BlockMachine(Material.IRON, "autocrafter", "autocrafter", 2).setHasInformation());
		blocks.add(circuitscribe = (BlockMachine)new BlockMachine(Material.IRON, "circuitscribe", "circuitscribe", 0).setHasInformation());
		blocks.add(saw = (BlockMachine) new BlockMachine(Material.ROCK, "saw", "saw", 0).setHasInformation());
		blocks.add(pipe = new BlockPipe(Material.IRON, "pipe"));
		blocks.add(itemRedis = (BlockTENoDir) new BlockItemRedis(Material.ROCK, "itemredis").setHasInformation());
		blocks.add(tank = (BlockTENoDir) new BlockTank(Material.IRON, "tankblock").setHasInformation());
		blocks.add(crucible = (BlockMachine) new BlockMachine(Material.IRON, "crucible", "crucible", 0).setHasInformation());
		blocks.add(compressionChamber = (BlockMachine) new BlockMachine(Material.IRON, "compressor", "compressor", 0).setHasInformation());
//		blocks.add(fluidDebug = new BlockFluidGiver(Material.ROCK, "fluiddebug"));
		blocks.add(fluidPuller = (BlockInventoryDirectional) new BlockInventoryDirectional(Material.IRON, "fluidpuller", "fluidpuller", false).enableRotating().setHasInformation());
		blocks.add(grindstone = (BlockMachine) new BlockMachine(Material.IRON, "grindstone", "grindstone", 0).setHasInformation());
		blocks.add(centrifuge = (BlockMachine) new BlockMachine(Material.IRON, "centrifuge", "centrifuge", 0).setHasInformation());
		blocks.add(magCentrifuge = (BlockMachine) new BlockMachine(Material.IRON, "magcent", "magcent", 0).setHasInformation());
		blocks.add(fluiddrill = (BlockMachine) new BlockMachine(Material.IRON, "fluiddrill", "fluiddrill", 0).setHasInformation());
		blocks.add(agitator = (BlockMachine) new BlockMachine(Material.IRON, "agitator", "agitator", 0).setHasInformation());
		blocks.add(refrigerator = (BlockMachine) new BlockMachine(Material.IRON, "fridge", "fridge", 0).setHasInformation());
		blocks.add(sluice = (BlockMachine) new BlockMachine(Material.WOOD, "sluice", "sluice", 0).setHasInformation());
		blocks.add(autominer = (BlockMachine) new BlockMachine(Material.IRON, "autominer", "miner", 0).setHasInformation());
		blocks.add(woodcutter = (BlockMachine) new BlockMachine(Material.ROCK, "woodcutter", "woodcutter", 0).setHasInformation());
		blocks.add(ionDisperser = (BlockMachine) new BlockMachine(Material.IRON, "iondisperser", "iondisperser", 0).setHasInformation());
		blocks.add(teslaCoil = (BlockMachine) new BlockMachine(Material.IRON, "teslacoil", "teslacoil", 0).setHasInformation());
		blocks.add(electroplater = (BlockMachine) new BlockMachine(Material.IRON, "electroplater", "electroplater", 0).setHasInformation());
		blocks.add(charger = (BlockMachine) new BlockMachine(Material.IRON, "charger", "charger", 0).setHasInformation());
		blocks.add(temperer = (BlockMachine) new BlockMachine(Material.IRON, "temperer", "temperer", 0).setHasInformation());
		blocks.add(stabilizer = (BlockMachine) new BlockMachine(Material.IRON, "stabilizer", "stabilizer", 0).setHasInformation());
		blocks.add(magnetizer = (BlockMachine) new BlockMachine(Material.IRON, "magnetizer", "magnetizer", 0).setHasInformation());
		blocks.add(energizer = (BlockTENoDir) new BlockTENoDir(Material.IRON, "energizer", 8).setHasInformation());
		blocks.add(magnet = (BlockTENoDir) new BlockTENoDir(Material.IRON, "magnetblock").setHasInformation());
		blocks.add(spawner = (BlockMachine) new BlockMachine(Material.ROCK, "spawner", "spawner", 0).setHasInformation());
		blocks.add(disassembler = (BlockMachine) new BlockMachine(Material.IRON, "disassembler", "disassembler", 0, false).setHasInformation());
		blocks.add(potionmixer = (BlockMachine) new BlockMachine(Material.ROCK, "potionmixer", "potionmixer").setHasInformation());
		blocks.add(htfurnace = (BlockMachine) new BlockMachine(Material.IRON, "htfurnace", "htfurnace").setHasInformation());
		blocks.add(propfurnace = (BlockMachine) new BlockMachine(Material.IRON, "propfurnace", "propfurnace").setHasInformation());
		blocks.add(disruptor = (BlockMachine) new BlockMachine(Material.IRON, "disruptor", "disruptor").setHasInformation());
		blocks.add(deepdrill = (BlockMachine) new BlockMachine(Material.IRON, "deepdrill", "deepdrill").setHasInformation());
		blocks.add(planter = (BlockMachine) new BlockMachine(Material.WOOD, "planter", "planter").setHasInformation());
//		blocks.add(aerolyzer = new BlockMachine(Material.IRON, "aerolyzer", "aerolyzer"));
		
		// Deco & Resources
		blocks.add(ore = new BlockOre("ore"));
		blocks.add(oreblock = new BlockOreStorage("oreblock"));
		blocks.add(bricks = (BlockBricks)new BlockBricks(Material.ROCK, "bricks").setHardness(2.0F).setResistance(10.0F));
		blocks.add(smokestack = (BlockSmokestack)new BlockSmokestack(Material.ROCK, "smokestack").setHardness(2.0F).setResistance(10.0F));
		blocks.add(decorative_metal = (BlockMetal)new BlockMetal(Material.IRON, "metal").setHardness(3.0F).setResistance(12.0F));
		blocks.add(scaffold = (BlockBase)new BlockScaffold(Material.IRON, "scaffold"));
		blocks.add(decocoil = new BlockDecoCoil(Material.IRON, "decocoil"));
	}

	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		for (BlockBase b : blocks)
		{
			b.initModel();
		}
//		pipe.initInvModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initInvModels()
	{
		pipe.initInvModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initTooltips()
	{
		for (BlockBase b : blocks)
		{
			if (b.getHasInformation())
			{
				
			}
		}
	}
}