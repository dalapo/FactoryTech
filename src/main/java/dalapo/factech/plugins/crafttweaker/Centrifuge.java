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
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacCraftTweakerHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.Centrifuge")
@ZenRegister
public class Centrifuge
{
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output1, @Optional IItemStack output2, @Optional IItemStack output3, boolean worksWithBad)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), FacCraftTweakerHelper.toStacks(output1, output2, output3), worksWithBad));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack... output)
	{
		CraftTweakerAPI.apply(new Remove(FacCraftTweakerHelper.toStacks(output)));
	}
	
	private static class Add implements IAction
	{
		private ItemStack in;
		private ItemStack[] out;
		private boolean worksWithBad;
		
		public Add(ItemStack in, ItemStack[] out, boolean worksWithBad)
		{
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply() {
			if (out.length <= 3) MachineRecipes.CENTRIFUGE.add(new MachineRecipe<>(in, out, worksWithBad));
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Centrifuge recipe for " + in + " -> " + out;
		}
	}
	
	private static class Remove implements IAction
	{
		ItemStack[] output;
		
		public Remove(ItemStack[] o)
		{
			this.output = o;
		}
		@Override
		public void apply()
		{
			for (MachineRecipe<ItemStack, ItemStack[]> entry : MachineRecipes.CENTRIFUGE)
			{
				boolean flag = true;
				try {
					for (int i=0; i<entry.output().length; i++)
					{
						if (!entry.output()[i].isItemEqual(output[i])) flag = false;
					}
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					flag = false;
				}
				if (flag)
				{
					MachineRecipes.CENTRIFUGE.remove(entry);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Centrifuge recipe for " + output;
		}
		
	}
}