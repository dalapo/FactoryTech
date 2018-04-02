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
import net.minecraftforge.fluids.FluidStack;
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

@ZenClass("mods.factorytech.Refrigerator")
@ZenRegister
public class Refrigerator
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input)
	{
		CraftTweakerAPI.apply(new Add((FluidStack)input.getInternal(), (ItemStack)output.getInternal()));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove((ItemStack)output.getInternal()));
	}
	
	private static class Add implements IAction
	{
		private FluidStack in;
		private ItemStack out;
		
		public Add(FluidStack in, ItemStack out)
		{
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply() {
			MachineRecipes.REFRIGERATOR.put(in, out);
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Refrigerator recipe for " + in + " -> " + out;
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
			for (Entry<FluidStack, ItemStack> entry : MachineRecipes.REFRIGERATOR.entrySet())
			{
				if (FacStackHelper.areItemStacksIdentical(entry.getValue(), output))
				{
					MachineRecipes.REFRIGERATOR.remove(entry.getKey());
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Refrigerator recipe for " + output;
		}
		
	}
}