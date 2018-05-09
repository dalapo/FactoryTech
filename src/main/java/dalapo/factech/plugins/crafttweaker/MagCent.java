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

@ZenClass("mods.factorytech.MagCent")
@ZenRegister
public class MagCent
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
	
	private static class Add extends MasterAdd<ItemStack, ItemStack[]>
	{
		public Add(ItemStack in, ItemStack[] out, boolean worksWithBad)
		{
			super(in, out, worksWithBad, MachineRecipes.MAGNET_CENTRIFUGE);
		}
		
		@Override
		public void apply() {
			if (output.length <= 3)
			{
				MachineRecipes.MAGNET_CENTRIFUGE.add(new MachineRecipe<>(input, output, worksWithBad));
			}
		}

		@Override
		public String describe()
		{
			return "Adding Centrifuge recipe for " + input + " -> " + output;
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
			for (int i=MachineRecipes.MAGNET_CENTRIFUGE.size()-1; i>=0; i--)
			{
				MachineRecipe<ItemStack, ItemStack[]> entry = MachineRecipes.MAGNET_CENTRIFUGE.get(i);
				boolean flag = true;
				try {
					for (int j=0; j<entry.output().length; j++)
					{
						if (!entry.output()[j].isItemEqual(output[j])) flag = false;
					}
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					flag = false;
				}
				if (flag)
				{
					MachineRecipes.CENTRIFUGE.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Chop Saw recipe for " + output;
		}
		
	}
}