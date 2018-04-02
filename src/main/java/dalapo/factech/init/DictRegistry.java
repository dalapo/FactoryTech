package dalapo.factech.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DictRegistry
{
	private DictRegistry() {}
	
	public static void registerOreDictEntries()
	{
		OreDictionary.registerOre("oreCopper", new ItemStack(BlockRegistry.ore, 1, 0));
		OreDictionary.registerOre("oreNickel", new ItemStack(BlockRegistry.ore, 1, 1));
		OreDictionary.registerOre("ingotCopper", new ItemStack(ItemRegistry.ingot, 1, 0));
		OreDictionary.registerOre("ingotNickel", new ItemStack(ItemRegistry.ingot, 1, 1));
		OreDictionary.registerOre("ingotCupronickel", new ItemStack(ItemRegistry.ingot, 1, 2));
		OreDictionary.registerOre("ingotInvar", new ItemStack(ItemRegistry.ingot, 1, 3));
		OreDictionary.registerOre("blockCopper", new ItemStack(BlockRegistry.oreblock, 1, 0));
		OreDictionary.registerOre("blockNickel", new ItemStack(BlockRegistry.oreblock, 1, 1));
		OreDictionary.registerOre("blockCupronickel", new ItemStack(BlockRegistry.oreblock, 1, 2));
		OreDictionary.registerOre("blockInvar", new ItemStack(BlockRegistry.oreblock, 1, 3));
		OreDictionary.registerOre("nuggetCopper", new ItemStack(ItemRegistry.oreProduct, 1, 4));
		OreDictionary.registerOre("nuggetNickel", new ItemStack(ItemRegistry.oreProduct, 1, 5));
		OreDictionary.registerOre("dustIron", new ItemStack(ItemRegistry.oreProduct, 1,  6));
		OreDictionary.registerOre("dustGold", new ItemStack(ItemRegistry.oreProduct, 1, 7));
		OreDictionary.registerOre("dustCopper", new ItemStack(ItemRegistry.oreProduct, 1, 8));
		OreDictionary.registerOre("dustNickel", new ItemStack(ItemRegistry.oreProduct, 1, 9));
		OreDictionary.registerOre("dustCuproNickel", new ItemStack(ItemRegistry.oreProduct, 1, 10));
		OreDictionary.registerOre("dustInvar", new ItemStack(ItemRegistry.oreProduct, 1, 11));
	}
}