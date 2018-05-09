package dalapo.factech.plugins.crafttweaker;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Pair;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.ElectricFurnace")
@ZenRegister
public class ElectricFurnace
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, boolean worksWithBad)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), (ItemStack)output.getInternal(), worksWithBad));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove((ItemStack)output.getInternal()));
	}
	
	private static class Add extends MasterAdd<ItemStack, ItemStack>
	{
		
		public Add(ItemStack in, ItemStack out, boolean worksWithBad)
		{
			super(in, out, worksWithBad, MachineRecipes.HTFURNACE);
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Electric Furnace recipe for " + input + " -> " + output;
		}
	}
	
	private static class Remove implements IAction
	{
		ItemStack output;
		
		public Remove(ItemStack o)
		{
			this.output = o;
		}
		@Override
		public void apply()
		{
			for (int i=MachineRecipes.HTFURNACE.size()-1; i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(MachineRecipes.HTFURNACE.get(i).output(), output))
				{
					MachineRecipes.HTFURNACE.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Electric Furnace recipe for " + output;
		}
		
	}
}