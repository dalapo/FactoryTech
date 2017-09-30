package dalapo.factech.auxiliary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityAgitator;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;
import dalapo.factech.tileentity.specialized.TileEntityCompressionChamber.CompressorRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
	// Array denotes multiple inputs/outputs.
	public static Map<ItemStack, ItemStack[]> CENTRIFUGE = new HashMap<ItemStack, ItemStack[]>();
	public static Map<ItemStack, ItemStack> SAW = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> ROLLER = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> METALCUTTER = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> GRINDSTONE = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, FluidStack> CRUCIBLE = new HashMap<ItemStack, FluidStack>();
	public static Map<ItemStack, ItemStack> ELECTROPLATER = new HashMap<>();
	public static Map<ItemStack, ItemStack> ELECTROLYZER = new HashMap<>();
	public static Map<ItemStack, ItemStack> HTFURNACE = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> OREDRILL = new HashMap<ItemStack, ItemStack>();
	public static Map<ItemStack, ItemStack> CIRCUIT_SCRIBE = new HashMap<ItemStack, ItemStack>();
	public static Map<FluidStack, ItemStack> REFRIGERATOR = new HashMap<FluidStack, ItemStack>();
	
	public static List<CompressorRecipe> COMPRESSOR = new ArrayList<CompressorRecipe>();
	public static List<AgitatorRecipe> AGITATOR = new ArrayList<AgitatorRecipe>();
	
	public static void initRecipes()
	{
		Logger.info("Entered initRecipes()");
//		TileEntityAgitator.fillRecipes();
		
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 1), new ItemStack(Items.IRON_NUGGET, 6), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 2), new ItemStack(ItemRegistry.oreProduct, 3, 4), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 5), new ItemStack(ItemRegistry.ingot, 2, 2), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 6), new ItemStack(ItemRegistry.circuitIntermediate, 1, 8), 1);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.salvagePart, 1, 11), new ItemStack(Items.IRON_NUGGET, 6), 1);
		
		for (int i=0; i<4; i++)
		{
			SAW.put(new ItemStack(Blocks.LOG, 1, i), new ItemStack(Blocks.PLANKS, 6, i));
		}
		for (int i=0; i<2; i++)
		{
			SAW.put(new ItemStack(Blocks.LOG2, 1, i), new ItemStack(Blocks.PLANKS, 6, i+4));
		}
		
		SAW.put(new ItemStack(Blocks.PLANKS, 1, 32767), new ItemStack(Items.STICK, 3));
		SAW.put(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 32767), new ItemStack(ItemRegistry.circuitIntermediate, 4, 8));
		
		GRINDSTONE.put(new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.ordinal()), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.ordinal()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 4), new ItemStack(ItemRegistry.machinePart, 1, PartList.DRILL.ordinal()));
		GRINDSTONE.put(new ItemStack(Blocks.GLASS), new ItemStack(ItemRegistry.machinePart, 1, PartList.LENS.ordinal()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 3), new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.ordinal()));
		GRINDSTONE.put(new ItemStack(ItemRegistry.salvagePart, 1, 0), new ItemStack(ItemRegistry.machinePart, 1, PartList.SAW.ordinal()));
		
		CRUCIBLE.put(new ItemStack(Blocks.ICE, 1), new FluidStack(FluidRegistry.WATER, 1000));
		CRUCIBLE.put(new ItemStack(Blocks.COBBLESTONE, 1), new FluidStack(FluidRegistry.LAVA, 50));
		CRUCIBLE.put(new ItemStack(Blocks.NETHERRACK, 1), new FluidStack(FluidRegistry.LAVA, 500));
		CRUCIBLE.put(new ItemStack(Items.GLOWSTONE_DUST, 1), new FluidStack(ModFluidRegistry.glowstone, 100));
		
		METALCUTTER.put(new ItemStack(Items.IRON_INGOT), new ItemStack(ItemRegistry.machinePart, 1, PartList.GEAR.ordinal()));
		
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 0), new ItemStack(Items.IRON_INGOT, 2));
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 1), new ItemStack(Items.GOLD_INGOT, 2));
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 2), new ItemStack(ItemRegistry.ingot, 2, 0));
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 3), new ItemStack(ItemRegistry.ingot, 2, 1));
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(Items.IRON_INGOT));
		ELECTROPLATER.put(new ItemStack(ItemRegistry.oreProduct, 1, 7), new ItemStack(Items.GOLD_INGOT));
		ELECTROPLATER.put(new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST));
		ELECTROPLATER.put(new ItemStack(Blocks.SAND), new ItemStack(Items.QUARTZ));
		
		OREDRILL.put(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		OREDRILL.put(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));
		OREDRILL.put(new ItemStack(Blocks.IRON_ORE), new ItemStack(ItemRegistry.oreProduct, 2, 0));
		OREDRILL.put(new ItemStack(Blocks.GOLD_ORE), new ItemStack(ItemRegistry.oreProduct, 2, 1));
		OREDRILL.put(new ItemStack(Blocks.REDSTONE_ORE), new ItemStack(Items.REDSTONE, 10));
		OREDRILL.put(new ItemStack(BlockRegistry.ore, 1, 0), new ItemStack(ItemRegistry.oreProduct, 2, 2));
		OREDRILL.put(new ItemStack(BlockRegistry.ore, 1, 1), new ItemStack(ItemRegistry.oreProduct, 2, 3));
		OREDRILL.put(new ItemStack(Items.IRON_INGOT), new ItemStack(ItemRegistry.oreProduct, 1, 6));
		OREDRILL.put(new ItemStack(Items.GOLD_INGOT), new ItemStack(ItemRegistry.oreProduct, 1, 7));
		OREDRILL.put(new ItemStack(ItemRegistry.ingot, 1, 0), new ItemStack(ItemRegistry.oreProduct, 1, 8));
		OREDRILL.put(new ItemStack(ItemRegistry.ingot, 1, 1), new ItemStack(ItemRegistry.oreProduct, 1, 9));
		OREDRILL.put(new ItemStack(Items.DYE, 1, 4), new ItemStack(ItemRegistry.oreProduct, 1, 12));
		OREDRILL.put(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ItemRegistry.intermediate, 1, 0));
		
		// Only the input matters here; the recipe is overridden in performAction()
		CIRCUIT_SCRIBE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, 8), ItemStack.EMPTY);
		
		CENTRIFUGE.put(new ItemStack(Blocks.GRAVEL), new ItemStack[] {new ItemStack(Items.FLINT), new ItemStack(Items.IRON_NUGGET)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 0), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(ItemRegistry.oreProduct, 1, 5)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 1), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 7), new ItemStack(ItemRegistry.oreProduct, 1, 4)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 2), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 8), new ItemStack(Items.GOLD_NUGGET)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 3), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 1, 9), new ItemStack(Items.IRON_NUGGET)});
		CENTRIFUGE.put(new ItemStack(ItemRegistry.oreProduct, 1, 10), new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 3, 8), new ItemStack(ItemRegistry.oreProduct, 1, 9)});
		CENTRIFUGE.put(new ItemStack(Blocks.SAND, 4, 1), new ItemStack[] {new ItemStack(Blocks.SAND, 4, 0), new ItemStack(Items.REDSTONE, 1)});
		
		REFRIGERATOR.put(new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(Blocks.ICE));
		REFRIGERATOR.put(new FluidStack(FluidRegistry.LAVA, 1000), new ItemStack(Blocks.OBSIDIAN));
		REFRIGERATOR.put(new FluidStack(ModFluidRegistry.propane, 1000), new ItemStack(Items.COAL));
		REFRIGERATOR.put(new FluidStack(ModFluidRegistry.glowstone, 400), new ItemStack(Blocks.GLOWSTONE));
		
		AGITATOR.add(new AgitatorRecipe(ItemStack.EMPTY, ItemStack.EMPTY, new FluidStack(ModFluidRegistry.h2so4, 1000), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(ModFluidRegistry.sulphur, 1000)));
		AGITATOR.add(new AgitatorRecipe(ItemStack.EMPTY, new ItemStack(Blocks.STONE), null, new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.LAVA, 1)));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.SAND), new ItemStack(Items.CLAY_BALL), null, new FluidStack(FluidRegistry.WATER, 1000), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(Items.REDSTONE), new ItemStack(Items.BLAZE_POWDER), null, new FluidStack(ModFluidRegistry.sulphur, 250), null));
		AGITATOR.add(new AgitatorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 3), ItemStack.EMPTY, new FluidStack(ModFluidRegistry.energite, 200), new FluidStack(ModFluidRegistry.glowstone, 100), null));
		for (int i=0; i<16; i++)
		{
			AGITATOR.add(new AgitatorRecipe(new ItemStack(Blocks.CONCRETE_POWDER, 1, i), new ItemStack(Blocks.CONCRETE, 1, i), null, new FluidStack(FluidRegistry.WATER, 100), null));
		}
		
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), null, new ItemStack(ItemRegistry.tank, 1, 1)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ItemRegistry.tank, 1, 2)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.propane, 1000), new ItemStack(ItemRegistry.tank, 1, 3)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.tank, 1, 0), new FluidStack(ModFluidRegistry.h2so4, 1000), new ItemStack(ItemRegistry.tank, 1, 4)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 1), new FluidStack(ModFluidRegistry.energite, 250), new ItemStack(ItemRegistry.coreUnfinished, 1, 99)));
		COMPRESSOR.add(new CompressorRecipe(new ItemStack(ItemRegistry.intermediate, 1, 2), new FluidStack(ModFluidRegistry.h2so4, 250), new ItemStack(ItemRegistry.machinePart, 1, PartList.BATTERY.ordinal())));
		
		for (int i=0; i<4; i++)
		{
			HTFURNACE.put(new ItemStack(ItemRegistry.circuitIntermediate, 1, i+4), new ItemStack(ItemRegistry.machinePart, 1, i+9));
		}
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
		addOreDictRecipe(GRINDSTONE, "ingotNickel", new ItemStack(ItemRegistry.machinePart, 1, PartList.BLADE.ordinal()));
		addOreDictRecipe(ELECTROPLATER, "dustCopper", new ItemStack(ItemRegistry.ingot, 1, 0));
		addOreDictRecipe(ELECTROPLATER, "dustNickel", new ItemStack(ItemRegistry.ingot, 1, 1));
		addOreDictRecipe(METALCUTTER, "ingotCopper", new ItemStack(ItemRegistry.machinePart, 2, PartList.WIRE.ordinal()));
		addOreDictRecipe(METALCUTTER, "ingotNickel", new ItemStack(ItemRegistry.salvagePart, 1, 4));
		
		for (ItemStack is : OreDictionary.getOres("dustInvar"))
		{
			CENTRIFUGE.put(is, new ItemStack[] {new ItemStack(ItemRegistry.oreProduct, 2, 6), new ItemStack(ItemRegistry.oreProduct, 1, 9)});
		}
	}
	
	public static void importFurnaceRecipes()
	{
		HTFURNACE.putAll(FurnaceRecipes.instance().getSmeltingList());
	}
}
