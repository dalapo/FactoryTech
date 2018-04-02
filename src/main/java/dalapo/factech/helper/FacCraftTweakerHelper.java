package dalapo.factech.helper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import dalapo.factech.plugins.crafttweaker.*;

public class FacCraftTweakerHelper
{
	public static void initTweaks()
	{
		Logger.info("Entering initTweaks");
		CraftTweakerAPI.registerClass(Agitator.class);
		CraftTweakerAPI.registerClass(Centrifuge.class);
		CraftTweakerAPI.registerClass(ChopSaw.class);
		CraftTweakerAPI.registerClass(CompressionChamber.class);
		CraftTweakerAPI.registerClass(Crucible.class);
		CraftTweakerAPI.registerClass(DrillGrinder.class);
		CraftTweakerAPI.registerClass(ElectricFurnace.class);
		CraftTweakerAPI.registerClass(Electroplater.class);
		CraftTweakerAPI.registerClass(Grindstone.class);
		CraftTweakerAPI.registerClass(Magnetizer.class);
		CraftTweakerAPI.registerClass(MetalCutter.class);
		CraftTweakerAPI.registerClass(Refrigerator.class);
		CraftTweakerAPI.registerClass(Temperer.class);
	}
	
	public static FluidStack toStack(ILiquidStack ils)
	{
		if (ils == null || ((FluidStack)ils.getInternal()).amount == 0 || ((FluidStack)ils.getInternal()).getFluid() == null) return null;
		return (FluidStack)ils.getInternal();
	}
	
	public static ItemStack toStack(IItemStack iis)
	{
		if (iis == null || ((ItemStack)iis.getInternal()).isEmpty()) return ItemStack.EMPTY;
		return (ItemStack)iis.getInternal();
	}
	
	public static ItemStack[] toStacks(IItemStack... iis)
	{
		ItemStack[] toReturn = new ItemStack[iis.length];
		
		for (int i=0; i<iis.length; i++)
		{
			toReturn[i] = toStack(iis[i]);
		}
		return toReturn;
	}
	
	public static Object toObject(IIngredient object)
	{
		if (object == null) return null;
		if (object instanceof IItemStack)
		{
			return toStack((IItemStack)object);
		}
		if (object instanceof ILiquidStack)
		{
			return toStack((ILiquidStack)object);
		}
		if (object instanceof IOreDictEntry)
		{
			return ((IOreDictEntry)object).getName();
		}
		
		return null;
	}
}