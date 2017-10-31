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
import dalapo.factech.tileentity.specialized.TileEntityCompressionChamber.CompressorRecipe;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorytech.CompressionChamber")
public class CompressionChamber
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient itemIn, IIngredient fluidIn)
	{
		CraftTweakerAPI.apply(new Add((ItemStack)itemIn.getInternal(), (FluidStack)fluidIn.getInternal(), (ItemStack)output.getInternal()));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove((ItemStack)output.getInternal()));
	}
	
	private static class Add implements IAction
	{
		private ItemStack itemIn;
		private FluidStack fluidIn;
		private ItemStack out;
		
		public Add(ItemStack itemIn, FluidStack fluidIn, ItemStack out)
		{
			this.itemIn = itemIn;
			this.fluidIn = fluidIn;
			this.out = out;
		}
		
		@Override
		public void apply() {
			MachineRecipes.COMPRESSOR.add(new CompressorRecipe(itemIn, fluidIn, out));
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Adding Compressor recipe for " + itemIn + " + " + fluidIn + " -> " + out;
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
			for (int i=MachineRecipes.COMPRESSOR.size(); i>=0; i--)
			{
				if (FacStackHelper.areItemStacksIdentical(MachineRecipes.COMPRESSOR.get(i).getItemOut(), output))
				{
					MachineRecipes.COMPRESSOR.remove(i);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Refrigerator recipe for " + output;
		}
		
	}
}