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
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Pair;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.Crucible")
@ZenRegister
public class Crucible
{
	@ZenMethod
	public static void addRecipe(ILiquidStack output, IIngredient input)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), (FluidStack)output.getInternal()));
	}
	
	@ZenMethod
	public static void removeRecipe(ILiquidStack output)
	{
		CraftTweakerAPI.apply(new Remove((FluidStack)output.getInternal()));
	}
	
	private static class Add implements IAction
	{
		private ItemStack in;
		private FluidStack out;
		
		public Add(ItemStack in, FluidStack out)
		{
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply() {
			MachineRecipes.CRUCIBLE.put(in, out);
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Chop Saw recipe for " + in + " -> " + out;
		}
	}
	
	private static class Remove implements IAction
	{
		FluidStack output;
		
		public Remove(FluidStack o)
		{
			this.output = o;
		}
		@Override
		public void apply()
		{
			for (Entry<ItemStack, FluidStack> entry : MachineRecipes.CRUCIBLE.entrySet())
			{
				if (FacStackHelper.areFluidStacksIdentical(entry.getValue(), output))
				{
					MachineRecipes.CRUCIBLE.remove(entry.getKey());
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Crucible recipe for " + output;
		}
		
	}
}