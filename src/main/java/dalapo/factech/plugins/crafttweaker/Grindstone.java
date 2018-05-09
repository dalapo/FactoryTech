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

@ZenClass("mods.factorytech.Grindstone")
@ZenRegister
public class Grindstone
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
	
	private static class Add extends MasterAdd
	{
		
		public Add(ItemStack in, ItemStack out, boolean worksWithBad)
		{
			super(in, out, worksWithBad, MachineRecipes.GRINDSTONE);
		}
		
		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Grindstone recipe for " + input + " -> " + output;
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
			for (int i=MachineRecipes.GRINDSTONE.size()-1; i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(MachineRecipes.GRINDSTONE.get(i).output(), output))
				{
					MachineRecipes.GRINDSTONE.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Grindstone recipe for " + output;
		}
		
	}
}