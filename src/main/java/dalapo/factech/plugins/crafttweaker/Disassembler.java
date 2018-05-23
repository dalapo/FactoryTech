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
import net.minecraftforge.fml.relauncher.ReflectionHelper;
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

@ZenClass("mods.factorytech.Disassembler")
@ZenRegister
public class Disassembler
{
	@ZenMethod
	public static void addRecipe(String className, IItemStack output)
	{
		CraftTweakerAPI.apply(new Add(className, (ItemStack)output.getInternal()));
	}
	
	@ZenMethod
	public static void removeRecipe(String className, IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove(className, (ItemStack)output.getInternal()));
	}
	
	private static class Add implements IAction
	{
		private String in;
		private ItemStack out;
		
		public Add(String in, ItemStack out)
		{
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply()
		{
			if (MachineRecipes.DISASSEMBLER.containsKey(in))
			{
				MachineRecipes.DISASSEMBLER.get(in).add(out);
			}
			else
			{
				List<ItemStack> stacks = new ArrayList<>();
				stacks.add(out);
				MachineRecipes.DISASSEMBLER.put(in, stacks);
			}
		}

		@Override
		public String describe()
		{
			return "Adding Disassembler recipe for " + in + " -> " + out;
		}
	}
	
	private static class Remove implements IAction
	{
		String in;
		ItemStack output;
		
		public Remove(String in, ItemStack o)
		{
			this.output = o;
		}
		@Override
		public void apply()
		{
			List<ItemStack> drops = MachineRecipes.DISASSEMBLER.get(in);
			for (int i=drops.size()-1; i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(output, drops.get(i)))
				{
					drops.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Disassembler recipe for " + output + " from " + in;
		}
		
	}
}