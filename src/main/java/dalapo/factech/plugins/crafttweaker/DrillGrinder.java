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

@ZenClass("mods.factorytech.DrillGrinder")
@ZenRegister
public class DrillGrinder
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
		private ItemStack in;
		private ItemStack out;
		
		public Add(ItemStack in, ItemStack out, boolean worksWithBad)
		{
			super(in, out, worksWithBad, MachineRecipes.OREDRILL);
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Drill Grinder recipe for " + in + " -> " + out;
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
			for (int i=MachineRecipes.OREDRILL.size()-1; i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(MachineRecipes.OREDRILL.get(i).output(), output))
				{
					MachineRecipes.OREDRILL.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Drill Grinder recipe for " + output;
		}
		
	}
}