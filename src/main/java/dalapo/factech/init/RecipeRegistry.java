package dalapo.factech.init;

import dalapo.factech.auxiliary.MachineRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	public static void init()
	{
		MachineRecipes.initRecipes();
		
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(BlockRegistry.ore, 1, 0), new ItemStack(ItemRegistry.ingot, 1, 0), 5);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(BlockRegistry.ore, 1, 1), new ItemStack(ItemRegistry.ingot, 1, 1), 5);
		
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 0), new ItemStack(Items.IRON_INGOT), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 1), new ItemStack(Items.GOLD_INGOT), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 2), new ItemStack(ItemRegistry.ingot, 1, 0), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 3), new ItemStack(ItemRegistry.ingot, 1, 1), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 6), new ItemStack(Items.IRON_INGOT), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 7), new ItemStack(Items.GOLD_INGOT), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 8), new ItemStack(ItemRegistry.ingot, 1, 0), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 9), new ItemStack(ItemRegistry.ingot, 1, 1), 3);
		
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 10), new ItemStack(ItemRegistry.ingot, 1, 2), 3);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ItemRegistry.oreProduct, 1, 11), new ItemStack(ItemRegistry.ingot, 1, 3), 3);
	}
}