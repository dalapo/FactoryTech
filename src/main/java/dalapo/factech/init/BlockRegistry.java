package dalapo.factech.init;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.FactoryTech;
import dalapo.factech.block.BlockBase;
import dalapo.factech.block.BlockBricks;
import dalapo.factech.block.BlockComparatorExact;
import dalapo.factech.block.BlockConveyor;
import dalapo.factech.block.BlockElevator;
import dalapo.factech.block.BlockFluidGiver;
import dalapo.factech.block.BlockHatch;
import dalapo.factech.block.BlockInventoryDirectional;
import dalapo.factech.block.BlockItemRedis;
import dalapo.factech.block.BlockMachine;
import dalapo.factech.block.BlockMetal;
import dalapo.factech.block.BlockOre;
import dalapo.factech.block.BlockOreStorage;
import dalapo.factech.block.BlockPipe;
import dalapo.factech.block.BlockScaffold;
import dalapo.factech.block.BlockSmokestack;
import dalapo.factech.block.BlockStackMover;
import dalapo.factech.block.BlockTENoDir;
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
	
	public static BlockStackMover stackmover;
	public static BlockStackMover filtermover;
	public static BlockStackMover bulkmover;
	public static BlockInventoryDirectional autopuller;
	public static BlockConveyor conveyor;
	public static BlockElevator elevator;
	public static BlockHatch hatch;
	public static BlockPipe pipe;
	public static BlockTENoDir itemRedis;
	public static BlockTENoDir tank;
	public static BlockInventoryDirectional fluidPuller;
	public static BlockFluidGiver fluidDebug;
	public static BlockInventoryDirectional itemPusher;
//	public static BlockInventoryDirectional mecharm;
	public static BlockInventoryDirectional sequenceplacer;
	
//	public static BlockMachine wirecutter;
	public static BlockMachine potionmixer;
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
	public static BlockMachine fluiddrill;
	public static BlockMachine agitator;
	public static BlockMachine sluice;
	public static BlockMachine autominer;
	public static BlockMachine electroplater;
	public static BlockMachine charger;
	public static BlockMachine stabilizer;
	public static BlockMachine magnetizer;
	public static BlockMachine spawner;
	public static BlockMachine disassembler;
	public static BlockMachine refrigerator;
	public static BlockMachine woodcutter;
	public static BlockMachine ionDisperser;
	public static BlockMachine teslaCoil;
	
	public static BlockTENoDir energizer;
	public static BlockTENoDir magnet;
	public static BlockTENoDir decocoil;
	public static BlockTENoDir watercollector;
	public static BlockTENoDir crate;
	
	public static BlockBase ore;
	public static BlockBase oreblock;
	public static BlockBase bricks;
	public static BlockBase smokestack;
	public static BlockBase decorative_metal;
	public static BlockBase scaffold;
	
	public static void init()
	{
		Logger.info("Entered BlockRegistry.init");
		blocks.add(potionmixer = new BlockMachine(Material.ROCK, "potionmixer", "potionmixer"));
		blocks.add(htfurnace = new BlockMachine(Material.IRON, "htfurnace", "htfurnace"));
		blocks.add(propfurnace = new BlockMachine(Material.IRON, "propfurnace", "propfurnace"));
		blocks.add(disruptor = new BlockMachine(Material.IRON, "disruptor", "disruptor"));
		blocks.add(stackmover = new BlockStackMover(Material.WOOD, "stackmover", "stackmover", false, 0));
		blocks.add(filtermover = new BlockStackMover(Material.IRON, "filtermover", "stackmover", false, 1));
		blocks.add(bulkmover = new BlockStackMover(Material.IRON, "bulkmover", "stackmover", false, 2));
		blocks.add(autopuller = new BlockInventoryDirectional(Material.IRON, "autopuller", "autopuller", true));
		blocks.add(itemPusher = new BlockInventoryDirectional(Material.IRON, "itempusher", "itempusher", true) {
			@Override
			public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
			{
				super.onBlockActivated(world, pos, state, ep, hand, side, hitX, hitY, hitZ);
				ep.openGui(FactoryTech.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		});
		blocks.add(sequenceplacer = new BlockInventoryDirectional(Material.WOOD, "sequenceplacer", "sequenceplacer", false, 9));
//		blocks.add(exactComparator = new BlockComparatorExact(Material.CIRCUITS, "comparatorEx"));
		blocks.add(watercollector = new BlockTENoDir(Material.WOOD, "watercollector"));
		blocks.add(crate = new BlockTENoDir(Material.WOOD, "crate", 7));
		blocks.add(conveyor = new BlockConveyor(Material.IRON, "conveyor", true));
		blocks.add(elevator = new BlockElevator(Material.IRON, "elevator", true));
		blocks.add(hatch = new BlockHatch(Material.IRON, "hatch"));
//		blocks.add(mecharm = new BlockInventoryDirectional(Material.IRON, "mecharm", "mecharm", false, 5));
		blocks.add(oredrill = new BlockMachine(Material.IRON, "oredrill", "oredrill", 0));
		blocks.add(metalCutter = new BlockMachine(Material.IRON, "metalcutter", "metalcutter", 0));
		blocks.add(autocrafter = new BlockMachine(Material.IRON, "autocrafter", "autocrafter", 2));
		blocks.add(circuitscribe = (BlockMachine)new BlockMachine(Material.IRON, "circuitscribe", "circuitscribe", 0));
		blocks.add(saw = new BlockMachine(Material.ROCK, "saw", "saw", 0));
		blocks.add(pipe = new BlockPipe(Material.IRON, "pipe"));
		blocks.add(itemRedis = new BlockItemRedis(Material.ROCK, "itemredis"));
		blocks.add(tank = new BlockTENoDir(Material.IRON, "tankblock"));
		blocks.add(crucible = new BlockMachine(Material.IRON, "crucible", "crucible", 0));
		blocks.add(compressionChamber = new BlockMachine(Material.IRON, "compressor", "compressor", 0));
//		blocks.add(fluidDebug = new BlockFluidGiver(Material.ROCK, "fluiddebug"));
		blocks.add(fluidPuller = new BlockInventoryDirectional(Material.IRON, "fluidpuller", "fluidpuller", false));
		blocks.add(grindstone = new BlockMachine(Material.IRON, "grindstone", "grindstone", 0));
		blocks.add(centrifuge = new BlockMachine(Material.IRON, "centrifuge", "centrifuge", 0));
		blocks.add(fluiddrill = new BlockMachine(Material.IRON, "fluiddrill", "fluiddrill", 0));
		blocks.add(agitator = new BlockMachine(Material.IRON, "agitator", "agitator", 0));
		blocks.add(refrigerator = new BlockMachine(Material.IRON, "fridge", "fridge", 0));
		blocks.add(sluice = new BlockMachine(Material.WOOD, "sluice", "sluice", 0));
		blocks.add(autominer = new BlockMachine(Material.IRON, "autominer", "miner", 0));
		blocks.add(woodcutter = new BlockMachine(Material.ROCK, "woodcutter", "woodcutter", 0));
		blocks.add(ionDisperser = new BlockMachine(Material.IRON, "iondisperser", "iondisperser", 0));
		blocks.add(teslaCoil = new BlockMachine(Material.IRON, "teslacoil", "teslacoil", 0));
		blocks.add(electroplater = new BlockMachine(Material.IRON, "electroplater", "electroplater", 0));
		blocks.add(charger = new BlockMachine(Material.IRON, "charger", "charger", 0));
		blocks.add(stabilizer = new BlockMachine(Material.IRON, "stabilizer", "stabilizer", 0));
		blocks.add(magnetizer = new BlockMachine(Material.IRON, "magnetizer", "magnetizer", 0));
		blocks.add(energizer = new BlockTENoDir(Material.IRON, "energizer", 8));
		blocks.add(magnet = new BlockTENoDir(Material.IRON, "magnetblock"));
		blocks.add(spawner = new BlockMachine(Material.ROCK, "spawner", "spawner", 0));
		blocks.add(disassembler = new BlockMachine(Material.IRON, "disassembler", "disassembler", 0, false));
		blocks.add(ore = new BlockOre("ore"));
		blocks.add(oreblock = new BlockOreStorage("oreblock"));
		blocks.add(bricks = (BlockBricks)new BlockBricks(Material.ROCK, "bricks").setHardness(2.0F).setResistance(10.0F));
		blocks.add(smokestack = (BlockSmokestack)new BlockSmokestack(Material.ROCK, "smokestack").setHardness(2.0F).setResistance(10.0F));
		blocks.add(decorative_metal = (BlockMetal)new BlockMetal(Material.IRON, "metal").setHardness(3.0F).setResistance(12.0F));
		blocks.add(scaffold = (BlockBase)new BlockScaffold(Material.IRON, "scaffold"));
//		blocks.add(decocoil = new BlockTENoDir(Material.IRON, "decocoil"));
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
}