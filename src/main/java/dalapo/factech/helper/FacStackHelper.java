package dalapo.factech.helper;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class FacStackHelper {
	private FacStackHelper() {}
	
	public static boolean isItemDamageNonZero(ItemStack is)
	{
		return (is.getItemDamage() != 0);
	}
	
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
			if (!areItemsEqualAllowEmpty(test, arr[i], false)) return false;
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
	
	public static boolean areItemsEqualAllowEmpty(@Nonnull ItemStack a, @Nonnull ItemStack b, boolean wildcard)
	{
		if (a.isEmpty() && b.isEmpty()) return true;
		return wildcard ? matchStacksWithWildcard(a, b) : a.isItemEqual(b);
	}
	
	public static boolean areItemStacksIdentical(@Nonnull ItemStack a, @Nonnull ItemStack b)
	{
		return areItemsEqualAllowEmpty(a, b, false) && a.getCount() == b.getCount();
	}
	
	public static boolean areFluidStacksIdentical(FluidStack a, FluidStack b)
	{
		if (a == null || b == null)
		{
			return (a == null && b == null);
		}
		
		return a.getFluid() == b.getFluid() && a.amount == b.amount;
	}
	
	public static boolean canCombineStacks(ItemStack a, ItemStack b)
	{
		if (a.isEmpty() || b.isEmpty()) return true;
		return a.isItemEqual(b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
	}
	
	@Nonnull
	public static ItemStack findStack(IInventory inv, Predicate<ItemStack> p, boolean consume)
	{
		boolean found = false;
		for (int i=0; i<inv.getSizeInventory(); i++)
		{
			if (p.test(inv.getStackInSlot(i)))
			{
				ItemStack temp = inv.getStackInSlot(i).copy();
				if (consume) inv.decrStackSize(i, 1);
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Nonnull
	public static ItemStack findStack(IItemHandler inv, Predicate<ItemStack> p, boolean consume)
	{
		boolean found = false;
		for (int i=0; i<inv.getSlots(); i++)
		{
			if (p.test(inv.getStackInSlot(i)))
			{
				ItemStack temp = inv.extractItem(i, 1, !consume);
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
	
	public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z, boolean freeze)
	{
		EntityItem ei = new EntityItem(world, x, y, z, stack);
		if (freeze)
		{
			ei.motionX = 0;
			ei.motionY = 0;
			ei.motionZ = 0;
		}
		world.spawnEntity(ei);
	}
	
	public static void spawnEntityItem(World world, ItemStack stack, BlockPos pos, boolean freeze)
	{
		spawnEntityItem(world, stack, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, freeze);
	}
}