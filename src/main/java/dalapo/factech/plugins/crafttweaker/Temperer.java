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
import dalapo.factech.tileentity.specialized.TileEntityTemperer.TempererRecipe;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.Temperer")
@ZenRegister
public class Temperer
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, int time)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), (ItemStack)output.getInternal(), time));
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
		private int time;
		
		public Add(ItemStack in, ItemStack out, int time)
		{
			this.in = in;
			this.out = out;
			this.time = time;
		}
		
		@Override
		public void apply() {
			MachineRecipes.TEMPERER.add(new TempererRecipe(in, out, time));
		}

		@Override
		public String describe()
		{
			return "Adding Temperer recipe for " + in + " -> " + out + "(" + time + " ticks)";
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
			for (int i=MachineRecipes.TEMPERER.size()-1; i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(MachineRecipes.TEMPERER.get(i).getOutput(), output))
				{
					MachineRecipes.TEMPERER.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Temperer recipe for " + output;
		}
		
	}
}