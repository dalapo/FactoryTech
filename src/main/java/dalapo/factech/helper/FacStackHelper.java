package dalapo.factech.helper;

import javax.annotation.Nonnull;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class FacStackHelper {
	private FacStackHelper() {}
	
	public static boolean areAllEmpty(ItemStack[] test)
	{
		for (int i=0; i<test.length; i++)
		{
			if (!test[i].isEmpty()) return false;
		}
		return true;
	}
	
	/**
	 * Tests if two ItemStack arrays match, ignoring item count.
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean matchStackArray(ItemStack[] a, ItemStack[] b)
	{
		if (a.length != b.length) return false;
		for (int i=0; i<a.length; i++)
		{
			if (!a[i].isItemEqual(b[i])) return false;
		}
		return true;
	}
	
	public static int matchAny(boolean ignoreDamage, ItemStack test, ItemStack... arr)
	{
		for (int i=0; i<arr.length; i++)
		{
			if (ignoreDamage)
			{
				if (test.getItem().equals(arr[i].getItem())) return i;
			}
			else
			{
				if (test.isItemEqual(arr[i])) return i;
			}
		}
		return -1;
	}
	
	public static boolean matchAll(ItemStack test, ItemStack... arr)
	{
		for (int i=0; i<arr.length; i++)
		{
			if (!areItemsEqualAllowEmpty(test, arr[i])) return false;
		}
		return true;
	}
	
	public static int getRemainingDurability(ItemStack toolIn)
	{
		return toolIn.getMaxDamage() - toolIn.getItemDamage();
	}
	
	public static boolean matchStacks(ItemStack a, Item item, int damage)
	{
		return a.getItem().equals(item) && a.getItemDamage() == damage;
	}
	
	public static boolean matchStacksWithWildcard(ItemStack a, ItemStack b)
	{
		return matchStacksWithWildcard(a, b, false);
	}
	
	public static boolean matchStacksWithWildcard(ItemStack a, ItemStack b, boolean oreDict)
	{
		if (a.isItemEqual(b)) return true;
		return (a.getItem().equals(b.getItem()) && (a.getItemDamage() == 32767 || b.getItemDamage() == 32767)) || (oreDict && matchOreDict(a, b));
	}
	
	public static boolean matchOreDict(ItemStack a, ItemStack b)
	{
		if (a.isEmpty() || b.isEmpty()) return false;
		try {
			return OreDictionary.getOreIDs(a)[0] == OreDictionary.getOreIDs(b)[0];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
	public static boolean areItemsEqualAllowEmpty(@Nonnull ItemStack a, @Nonnull ItemStack b)
	{
		if (a.isEmpty() && b.isEmpty()) return true;
		return a.isItemEqual(b);
	}
	
	public static boolean canCombineStacks(ItemStack a, ItemStack b)
	{
		if (a.isEmpty() || b.isEmpty()) return true;
		return a.isItemEqual(b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
	}
	
	@Nonnull
	public static ItemStack findStack(IInventory inv, ItemStack test, boolean careAboutDamage, boolean consume)
	{
		boolean found = false;
		for (int i=0; i<inv.getSizeInventory(); i++)
		{
			if (careAboutDamage)
			{
				if (inv.getStackInSlot(i).isItemEqual(test)) found = true;
			}
			else
			{
				if (inv.getStackInSlot(i).getItem().equals(test.getItem())) found = true;
			}
			if (found)
			{
				ItemStack temp = inv.getStackInSlot(i).copy();
				inv.decrStackSize(i, 1);
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}
	
	public static boolean canCombineFluids(FluidStack a, FluidStack b)
	{
		if (a == null || b == null) return true;
		return a.getFluid().equals(b.getFluid());
	}
}