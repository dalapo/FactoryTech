package dalapo.factech.auxiliary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.google.common.collect.Maps;

import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityAgitator;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;
import dalapo.factech.tileentity.specialized.TileEntityCompressionChamber.CompressorRecipe;
import dalapo.factech.tileentity.specialized.TileEntityTemperer.TempererRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;

public class MachineRecipes {
	// In lieu of good coding, have a bunch of manually defined and filled static HashMaps.
	// Convention: Key = input, Value = output.
	public static Map<ItemStack, ItemStack[]> CENTRIFUGE = new ConcurrentHashMap<ItemStack, ItemStack[]>();
	public static Map<ItemStack, ItemStack[]> MAGNET_CENTRIFUGE = new ConcurrentHashMap<ItemStack, ItemStack[]>();
	public static Map<ItemStack, ItemStack> SAW = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> ROLLER = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> METALCUTTER = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> GRINDSTONE = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> MAGNETIZER = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, FluidStack> CRUCIBLE = new ConcurrentHashMap<ItemStack, FluidStack>();
//	public static Map<ItemStack, ItemStack> ELECTROPLATER = new ConcurrentHashMap<>();
	public static Map<ItemStack, ItemStack> HTFURNACE = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> OREDRILL = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> CIRCUIT_SCRIBE = new ConcurrentHashMap<ItemStack, ItemStack>();
	public static Map<FluidStack, ItemStack> REFRIGERATOR = new ConcurrentHashMap<FluidStack, ItemStack>();
	
	public static Map<Class<? extends EntityLivingBase>, List<ItemStack>> DISASSEMBLER = new HashMap<>();
	public static Map<ItemStack, Block> PLANTER = new HashMap<ItemStack, Block>();
	// Lists for complex recipes
	
	public static List<Pair<ItemStack, ItemStack>> ELECTROPLATER = new ArrayList<>();
	public static List<CompressorRecipe> COMPRESSOR = new ArrayList<CompressorRecipe>();
	public static List<AgitatorRecipe> AGITATOR = new ArrayList<AgitatorRecipe>();
	public static List<TempererRecipe> TEMPERER = new ArrayList<TempererRecipe>();
	public static List<Pair<ItemStack, Double>> DEEP_DRILL = new ArrayList<Pair<ItemStack, Double>>();
	
	public static void initRecipes()
	{
		Logger.info("Entered initRecipes()");
//		TileEntityAgitator.fillRecipes();
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 1), new ItemStack(Items.IRON_NUGGET, 6), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 2), new ItemStack(ItemRegistry.oreProduct, 3, 4), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 5), new ItemStack(ItemRegistry.ingot, 3, 2), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 6), new ItemStack(ItemRegistry.circuitIntermediate, 1, 8), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 11), new ItemStack(Items.IRON_NUGGET, 6), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.minedOre, 1, 4), new ItemStack(Items.IRON_INGOT), 6);
		
		for (int i=0; i<4; i++)
		{
			SAW.put(new ItemStack(Blocks.LOG, 1, i), new ItemStack(Blocks.PLANKS, 6, i));
		}
		for (int i=0; i<2; i++)
		{
			SAW.put(new ItemStack(Blocks.LOG2, 1, i), new ItemStack(Blocks.PLANKS, 6, i+4));
		}
		
		SAW.put(new ItemStack(Blocks.PLANKS, 1, 32767), new ItemStack(Items.STICK, 3));
		SAW.put(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 32767), new ItemStack(ItemRegistry.circuitIntermediate, 6, 8));
		
		GRINDSTONE.put(new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor()), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.getFloor()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 4), new ItemStack(ItemRegistry.machinePart, 1, PartList.DRILL.getFloor()));
		GRINDSTONE.put(new ItemStack(Blocks.GLASS), new ItemStack(ItemRegistry.machinePart, 1, PartList.LENS.getFloor()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 3), new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.getFloor()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 0), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.getFloor()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor()+1), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.getFloor()+1));
		
		CRUCIBLE.put(new ItemStack(Blocks.ICE, 1), new FluidStack(FluidRegistry.WATER, 1000));
		CRUCIBLE.put(new ItemStack(Blocks.COBBLESTONE, 1), new FluidStack(FluidRegistry.LAVA, 50));
		CRUCIBLE.put(new ItemStack(Blocks.NETHERRACK, 1), new FluidStack(FluidRegistry.LAVA, 500));
		CRUCIBLE.put(new ItemStack(Items.GLOWSTONE_DUST, 1), new FluidStack(ModFluidRegistry.glowstone, 100));
		
		METALCUTTER.put(new ItemStack(Items.IRON_INGOT), new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor()));
		
		MAGNETIZER.put(new ItemStack(Items.IRON_INGOT), new ItemStack(ItemRegistry.machinePart, 1, PartList.MAGNET.getFloor()));
		
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 0), new ItemStack(Items.IRON_INGOT, 2)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 1), new ItemStack(Items.GOLD_INGOT, 2)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 2), new ItemStack(ItemRegistry.ingot, 2, 0)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 3), new ItemStack(ItemRegistry.ingot, 2, 1)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(Items.IRON_INGOT)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.oreProduct, 1, 7), new ItemStack(Items.GOLD_INGOT)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(Blocks.SAND), new ItemStack(Items.QUARTZ)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.minedOre, 1, 2), new ItemStack(Items.GOLD_INGOT, 1)));
		ELECTROPLATER.add(new Pair<>(new ItemStack(ItemRegistry.minedOre, 1, 2), new ItemStack(ItemRegistry.ingot, 2, 0)));
		
		OREDRILL.put(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		OREDRILL.put(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));
		OREDRILL.put(new ItemStack(Blocks.IRON_ORE), new ItemStack(ItemRegistry.oreProduct, 2, 0));
		OREDRILL.put(new ItemStack(Blocks.GOLD_ORE), new ItemStack(ItemRegistry.oreProduct, 2, 1));
		OREDRILL.put(new ItemStack(Blocks.REDSTONE_ORE), new ItemStack(Items.REDSTONE, 10));
		OREDRILL.put(new ItemStack(Blocks.LAPIS_ORE), new ItemStack(Items.DYE, 12, 4));
		OREDRILL.put(new ItemStack(Blocks.DIAMOND_ORE), new ItemStack(Items.DIAMOND, 2));
		OREDRILL.put(new ItemStack(Items.DYE, 1, 4), new ItemStack(ItemRegistry.oreProduct, 1, 12));
		OREDRILL.put(new ItemStack(Blocks.LAPIS_BLOCK), new ItemStack(ItemRegistry.oreProduct, 6, 12));
		OREDRILL.put(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ItemRegistry.intermediate, 1, 0));
		OREDRILL.put(new ItemStack(ItemRegistry.minedOre, 1, 0), new ItemStack(ItemRegistry.minedOre, 2, 2));
		OREDRILL.put(new ItemStack(ItemRegistry.minedOre, 1, 1), new ItemStack(ItemRegistry.minedOre, 2, 3));
		
		// Only the input matters here; the recipe is overridden in performAction()
		CIRCUIT_SCRIBE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 8), ItemStack.EMPTY);
		
		MAGNET_CENTRIFUGE.put(new ItemStack(Blocks.GRAVEL, 4), new ItemStack[] {new ItemStack(Items.FLINT, 2), new ItemStack(Items.IRON_NUGGET, 2), new ItemStack(ItemRegistry.oreProduct, 2, 5)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 0), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(ItemRegistry.oreProduct, 1, 5)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 1), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 7), new ItemStack(ItemRegistry.oreProduct, 1, 4)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 2), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 8), new ItemStack(Items.GOLD_NUGGET)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 3), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 9), new ItemStack(Items.IRON_NUGGET)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 4, 10), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 3, 8), new ItemStack(ItemRegistry.oreProduct, 1, 9)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.minedOre, 1, 4), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(ItemRegistry.oreProduct, 1, 9)});
		CENTRIFUGE.put(new ItemStack(Blocks.SAND, 4, 1), new ItemStack[] {new ItemStack(Blocks.SAND, 4, 0), new ItemStack(Items.REDSTONE, 1)});
		MAGNET_CENTRIFUGE.put(new ItemStack(ItemRegistry.minedOre, 1, 2), new ItemStack[] {new ItemStack(Blocks.GRAVEL), new ItemStack(ItemRegistry.minedOre, 1, 4)});
		MAGNET_CENTRIFUGE.put(new ItemStack(ItemRegistry.minedOre, 1, 3), new ItemStack[] {new ItemStack(Items.REDSTONE, 6), new ItemStack(Items.DYE, 2, 4)});
		
		REFRIGERATOR.put(new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(Blocks.ICE));
		REFRIGERATOR.put(new FluidStack(FluidRegistry.LAVA, 1000), new ItemStack(Blocks.OBSIDIAN));
		REFRIGERATOR.put(new FluidStack(ModFluidRegistry.propane, 1000), new ItemStack(Items.COAL));
		REFRIGERATOR.put(new FluidStack(ModFluidRegistry.glowstone, 400), new ItemStack(Blocks.GLOWSTONE));
		
		AGITATOR.add(new AgitatorRecipe(ItemStack.EMPTY, ItemStack.EMPTY, new FluidStack(ModFluidRegistry.h2so4, 1000), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(ModFluidRegistry.sulphur, 1000)));
		AGITATOR.add(new AgitatorRecipe(ItemStack.EMPTY, new ItemStack(Blocks.STONE), null, new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.LAVA, 1)));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.SAND), new ItemStack(Items.CLAY_BALL), null, new FluidStack(FluidRegistry.WATER, 1000), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Items.REDSTONE), new ItemStack(Items.BLAZE_POWDER), null, new FluidStack(ModFluidRegistry.sulphur, 250), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 3), ItemStack.EMPTY, new FluidStack(ModFluidRegistry.energite, 200), new FluidStack(ModFluidRegistry.glowstone, 100), new FluidStack(ModFluidRegistry.h2so4, 100)));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.LEAVES, 1, 32767), new ItemStack(Items.SLIME_BALL), null, new FluidStack(FluidRegistry.WATER, 250), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.LEAVES2, 1, 32767), new ItemStack(Items.SLIME_BALL), null, new FluidStack(FluidRegistry.WATER, 250), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), null, new FluidStack(ModFluidRegistry.h2so4, 200), null));
		for (int i=0; i<16; i++)
		{
			AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.CONCRETE_POWDER, 1, i), new ItemStack(Blocks.CONCRETE, 1, i), null, new FluidStack(FluidRegistry.WATER, 100), null));
		}
		
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), null, new ItemStack(ItemRegistry.tank, 1, 1)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ItemRegistry.tank, 1, 2)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.propane, 1000), new ItemStack(ItemRegistry.tank, 1, 3)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.h2so4, 1000), new ItemStack(ItemRegistry.tank, 1, 4)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.sulphur, 1000), new ItemStack(ItemRegistry.tank, 1, 5)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.glowstone, 1000), new ItemStack(ItemRegistry.tank, 1, 6)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.energite, 1000), new ItemStack(ItemRegistry.tank, 1, 7)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 1), new FluidStack(ModFluidRegistry.energite, 250), new ItemStack(ItemRegistry.coreUnfinished, 1, 99)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 2), new FluidStack(ModFluidRegistry.h2so4, 250), new ItemStack(ItemRegistry.machinePart, 1, PartList.BATTERY.getFloor())));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 2), new FluidStack(ModFluidRegistry.energite, 250), new ItemStack(ItemRegistry.machinePart, 1, PartList.BATTERY.getFloor() + 1)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.machinePart, 1, PartList.MOTOR.getFloor()), new FluidStack(ModFluidRegistry.energite, 200), new ItemStack(ItemRegistry.machinePart, 1, PartList.MOTOR.getFloor() + 2)));
		
		TEMPERER.add(new TempererRecipe(new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.getFloor()), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.getFloor() + 2), 73));
		TEMPERER.add(new TempererRecipe(new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor()), new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor() + 2), 66));
		TEMPERER.add(new TempererRecipe(new ItemStack(ItemRegistry.machinePart, 1, PartList.DRILL.getFloor()), new ItemStack(ItemRegistry.machinePart, 1, PartList.DRILL.getFloor() + 1), 89));
		TEMPERER.add(new TempererRecipe(new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.getFloor()), new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.getFloor() + 1), 54));
		
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.COAL_ORE), 2.0));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.IRON_ORE), 1.0));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.GOLD_ORE), 0.25));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.REDSTONE_ORE), 0.8));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.LAPIS_ORE), 0.3));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(Blocks.DIAMOND_ORE), 0.1));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(BlockRegistry.ore, 1, 0), 1.0));
		DEEP_DRILL.add(new Pair<ItemStack, Double>(new ItemStack(BlockRegistry.ore, 1, 1), 0.8));
		
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 4), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_0.getFloor()));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 5), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_1.getFloor()));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 6), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_2.getFloor()));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 7), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_3.getFloor()));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 9), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_0.getFloor() + 1));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 10), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_1.getFloor() + 1));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 11), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_2.getFloor() + 1));
		HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 12), new ItemStack(ItemRegistry.machinePart, 1, PartList.CIRCUIT_3.getFloor() + 1));

		addDisassemblerRecipes();
	}
	
	private static void addDisassemblerRecipes()
	{
		List<ItemStack> zombie = new ArrayList<ItemStack>();
		zombie.add(new ItemStack(Items.ROTTEN_FLESH, 4));
		zombie.add(new ItemStack(Items.BONE, 1));
		zombie.add(new ItemStack(Item.getItemById(397), 1, 2));
		zombie.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntityZombie.class, zombie);
		
		List<ItemStack> skeleton = new ArrayList<ItemStack>();
		skeleton.add(new ItemStack(Items.BONE, 4));
		skeleton.add(new ItemStack(Items.ARROW, 4));
		skeleton.add(new ItemStack(Item.getItemById(397), 1, 0));
		skeleton.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntitySkeleton.class, skeleton);
		
		List<ItemStack> spider = new ArrayList<ItemStack>();
		spider.add(new ItemStack(Items.STRING, 4));
		spider.add(new ItemStack(Items.SPIDER_EYE, 3));
		spider.add(new ItemStack(Items.LEATHER, 1));
		spider.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntitySpider.class, spider);
		DISASSEMBLER.put(EntityCaveSpider.class, spider);
		
		List<ItemStack> creeper = new ArrayList<ItemStack>();
		creeper.add(new ItemStack(Items.GUNPOWDER, 4));
		creeper.add(new ItemStack(Item.getItemById(397), 1, 4));
		creeper.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntityCreeper.class, creeper);

		List<ItemStack> pigzombie = new ArrayList<ItemStack>();
		pigzombie.add(new ItemStack(Items.ROTTEN_FLESH, 3));
		pigzombie.add(new ItemStack(Items.GOLDEN_SWORD, 1));
		pigzombie.add(new ItemStack(Items.GOLD_NUGGET, 2));
		pigzombie.add(new ItemStack(Items.COOKED_PORKCHOP, 1));
		pigzombie.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntityPigZombie.class, pigzombie);
		
		List<ItemStack> enderman = new ArrayList<ItemStack>();
		enderman.add(new ItemStack(Items.ENDER_PEARL, 2));
		enderman.add(new ItemStack(Blocks.OBSIDIAN, 1));
		enderman.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		DISASSEMBLER.put(EntityEnderman.class, enderman);
		
		List<ItemStack> blaze = new ArrayList<ItemStack>();
		blaze.add(new ItemStack(Items.BLAZE_ROD, 3));
		blaze.add(new ItemStack(Items.FIRE_CHARGE, 2));
		blaze.add(new ItemStack(Items.GUNPOWDER, 1));
		DISASSEMBLER.put(EntityBlaze.class, blaze);
	}
	// Because typing is hard
	private static boolean hasOre(String str)
	{
		return OreDictionary.doesOreNameExist(str);
	}
	
	private static void addOreDictRecipe(Map<ItemStack, ItemStack> recipeList, String input, ItemStack output)
	{
		for (ItemStack in : OreDictionary.getOres(input))
		{
			recipeList.put(in, output);
		}
	}
	
	private static void addOreDictRecipe(List<Pair<ItemStack, ItemStack>> recipeList, String input, ItemStack output)
	{
		for (ItemStack in : OreDictionary.getOres(input))
		{
			recipeList.add(new Pair<>(in, output));
		}
	}
	
	private static void addDrillRecipe(String type)
	{
		type = FacMiscHelper.capitalizeFirstLetter(type);
		
		if (hasOre("ore" + type) && hasOre("dust" + type))
		{
			for (ItemStack is : OreDictionary.getOres("ore" + type))
			{
				if (!type.equals("Copper") && !type.equals("Nickel") &&
					!is.getItem().getRegistryName().getResourceDomain().equals("minecraft") &&
					!OreDictionary.getOres("dust" + type).isEmpty()) // Avoid duplicate recipes
				{
					ItemStack result = OreDictionary.getOres("dust" + type).get(0).copy();
					result.setCount(2);
					Logger.info(String.format("Adding Grinder recipe %s -> %s", is, result));
					OREDRILL.put(is.copy(), result);
				}
				else if (type.equals("Copper") || type.equals("Nickel"))
				{
					OREDRILL.put(is.copy(), new ItemStack(ItemRegistry.oreProduct, 2, type.equals("Copper") ? 2 : 3));
				}
			}
		}
		
		if(hasOre("ingot" + type) && hasOre("dust" + type))
		{
			for (ItemStack is : OreDictionary.getOres("ingot" + type))
			{
				if (!OreDictionary.getOres("dust" + type).isEmpty())
				{
					ItemStack result = OreDictionary.getOres("dust" + type).get(0).copy();
					result.setCount(1);
					Logger.info(String.format("Adding Grinder recipe %s -> %s", is, result));
					OREDRILL.put(is.copy(), result);
				}
			}
		}
	}
	
	public static void addOreDictRecipes()
	{
		for (String str : OreDictionary.getOreNames())
		{
			if (str.startsWith("ore"))
			{
				addDrillRecipe(str.substring(3));
			}
		}
		addOreDictRecipe(GRINDSTONE, "ingotNickel", new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.getFloor()));
		addOreDictRecipe(ELECTROPLATER, "dustCopper", new ItemStack(ItemRegistry.ingot, 1, 0));
		addOreDictRecipe(ELECTROPLATER, "dustNickel", new ItemStack(ItemRegistry.ingot, 1, 1));
		addOreDictRecipe(METALCUTTER, "ingotCopper", new ItemStack(ItemRegistry.machinePart, 2, PartList.WIRE.getFloor()));
		addOreDictRecipe(METALCUTTER, "ingotGold", new ItemStack(ItemRegistry.machinePart, 2, PartList.WIRE.getFloor() + 1));
		addOreDictRecipe(METALCUTTER, "ingotNickel", new ItemStack(ItemRegistry.salvagePart, 1, 4));
		addOreDictRecipe(METALCUTTER, "ingotInvar", new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.getFloor() + 1));
		
		for (ItemStack is : OreDictionary.getOres("dustInvar"))
		{
			ItemStack copy = is.copy();
			copy.setCount(3);
			CENTRIFUGE.put(copy, new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 2, 6), new ItemStack(ItemRegistry.oreProduct, 1, 9)});
		}
	}
	
	public static void importFurnaceRecipes()
	{
		HTFURNACE.putAll(FurnaceRecipes.instance().getSmeltingList());
	}
}
