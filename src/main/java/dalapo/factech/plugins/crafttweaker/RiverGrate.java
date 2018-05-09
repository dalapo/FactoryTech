package dalapo.factech.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import dalapo.factech.helper.Pair;
import dalapo.factech.tileentity.specialized.TileEntitySluice;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.rivergrate")
@ZenRegister
public class RiverGrate {
	@ZenMethod
	public static void addRecipe(IItemStack output, double chance)
	{
		CraftTweakerAPI.apply(new Add(output, chance));
	}
	
	private static class Add implements IAction
	{
		private IItemStack output;
		private double chance;
		
		public Add(IItemStack output, double chance)
		{
			this.output = output;
			this.chance = chance;
		}
		
		@Override
		public void apply()
		{
			TileEntitySluice.addValidOutput((ItemStack)output.getInternal(), chance);
		}

		@Override
		public String describe()
		{
			return "Adding River Grate recipe for " + output.getInternal();
		}
	}
	
	private static class Remove implements IAction
	{
		private IItemStack output;
		
		public Remove(IItemStack output)
		{
			this.output = output;
		}
		
		@Override
		public void apply()
		{
			for(int i=TileEntitySluice.outputs.size()-1; i>0; i--)
			{
				Pair<ItemStack, Double> p = TileEntitySluice.outputs.get(i);
				if (p.a.isItemEqual((ItemStack)output.getInternal()))
				{
					TileEntitySluice.outputs.remove(i);
				}
			}
		}

		@Override
		public String describe()
		{
			return "Removing River Grate recipe for " + output.getInternal();
		}
	}
}