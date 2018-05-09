package dalapo.factech.plugins.crafttweaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.ChopSaw")
@ZenRegister
public class ChopSaw
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, boolean worksWithBad)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), (ItemStack)output.getInternal(), worksWithBad));
	}
	
	@ZenMethod
	public static void addOreDictRecipe(IItemStack output, String input, int amount, boolean worksWithBad)
	{
		for (ItemStack is : OreDictionary.getOres(input))
		{
			ItemStack temp = is.copy();
			temp.setCount(amount);
			CraftTweakerAPI.apply(new Add(temp, ((ItemStack)output.getInternal()), worksWithBad));
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove((ItemStack)output.getInternal()));
	}
	
	private static class Add implements IAction
	{
		private ItemStack in;
		private ItemStack out;
		private boolean worksWithBad;
		
		public Add(ItemStack in, ItemStack out, boolean worksWithBad)
		{
			this.in = in;
			this.out = out;
			this.worksWithBad = worksWithBad;
		}
		
		@Override
		public void apply() {
			MachineRecipes.SAW.add(new MachineRecipe<>(in, out, worksWithBad));
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Chop Saw recipe for " + in + " -> " + out;
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
			Logger.info(Thread.activeCount());
//			Set<Entry<ItemStack, ItemStack>> entries = MachineRecipes.SAW.entrySet();
//			MachineRecipes.SAW.remove(output); // Doesn't do anything because ItemStacks are stupid and don't override equals
			for (MachineRecipe<ItemStack, ItemStack> entry : MachineRecipes.SAW)
			{
				if (FacStackHelper.areItemStacksIdentical(entry.output(), output))
				{
					Logger.info("CRAFTTWEAKER: " + describe());
					MachineRecipes.SAW.remove(entry);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Chop Saw recipe for " + output;
		}
		
	}
}