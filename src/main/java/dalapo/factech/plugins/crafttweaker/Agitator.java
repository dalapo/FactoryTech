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
import crafttweaker.api.liquid.ILiquidStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacCraftTweakerHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Pair;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.Agitator")
@ZenRegister
public class Agitator
{
	@ZenMethod
	public static void addRecipe(ILiquidStack fluid1, @Optional ILiquidStack fluid2, @Optional IItemStack itemIn, boolean worksWithBad, @Optional ILiquidStack fluidOut, @Optional IItemStack itemOut)
	{
		CraftTweakerAPI.apply(new Add(FacCraftTweakerHelper.toStack(fluid1), FacCraftTweakerHelper.toStack(fluid2), FacCraftTweakerHelper.toStack(itemIn), FacCraftTweakerHelper.toStack(fluidOut), FacCraftTweakerHelper.toStack(itemOut)));
//		CraftTweakerAPI.apply(new Add(toAdd));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack item, ILiquidStack fluid)
	{
		CraftTweakerAPI.apply(new Remove(FacCraftTweakerHelper.toStack(fluid), FacCraftTweakerHelper.toStack(item)));
	}
	
	private static class Add implements IAction
	{
		private AgitatorRecipe recipe;
		
		public Add(FluidStack fluid1, FluidStack fluid2, ItemStack item1, FluidStack fluidOut, ItemStack itemOut)
		{
			recipe = new AgitatorRecipe(itemOut, item1, fluidOut, fluid1, fluid2);
		}
		
		@Override
		public void apply() {
			MachineRecipes.AGITATOR.add(recipe);
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Agitator recipe";
		}
	}
	
	private static class Remove implements IAction
	{
		FluidStack fluidOut;
		ItemStack itemOut;
		
		public Remove(FluidStack fs, ItemStack is)
		{
			fluidOut = fs;
			itemOut = is;
		}
		@Override
		public void apply()
		{
			for (int i=MachineRecipes.AGITATOR.size()-1; i>=0; i--)
			{
				AgitatorRecipe r = MachineRecipes.AGITATOR.get(i);
				if (r.getOutputFluid().isFluidEqual(fluidOut) &&
					r.getOutputFluid().amount == fluidOut.amount &&
					FacStackHelper.areItemStacksIdentical(r.getOutputItem(), itemOut))
				{
					MachineRecipes.AGITATOR.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Agitator recipe";
		}
	}
}