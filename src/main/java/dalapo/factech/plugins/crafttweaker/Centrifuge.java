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
import dalapo.factech.helper.FacCraftTweakerHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.Centrifuge")
@ZenRegister
public class Centrifuge
{
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack... output)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)input.getInternal(), FacCraftTweakerHelper.toStacks(output)));
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
		
		public Add(ItemStack in, ItemStack[] out)
		{
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply() {
			if (out.length <= 3) MachineRecipes.CENTRIFUGE.put(in, out);
			else Logger.error("ERROR adding Centrifuge recipe to process " + in + ": too many outputs (max 3)");
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
			for (Entry<ItemStack, ItemStack[]> entry : MachineRecipes.CENTRIFUGE.entrySet())
			{
				boolean flag = true;
				try {
					for (int i=0; i<entry.getValue().length; i++)
					{
						if (!entry.getValue()[i].isItemEqual(output[i])) flag = false;
					}
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					flag = false;
				}
				if (flag)
				{
					MachineRecipes.CENTRIFUGE.remove(entry.getKey());
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Chop Saw recipe for " + output;
		}
		
	}
}