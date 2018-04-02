package dalapo.factech.reference;

import java.util.Map.Entry;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

public class PredicateList
{
	public static Predicate<Entry<ItemStack, ItemStack>> matchesForRemoval = new Predicate<Entry<ItemStack, ItemStack>>()
			{
				@Override
				public boolean test(Entry<ItemStack, ItemStack> t) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public Predicate<Entry<ItemStack, ItemStack>> and(
						Predicate<? super Entry<ItemStack, ItemStack>> other) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Predicate<Entry<ItemStack, ItemStack>> negate() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Predicate<Entry<ItemStack, ItemStack>> or(
						Predicate<? super Entry<ItemStack, ItemStack>> other) {
					// TODO Auto-generated method stub
					return null;
				}
			};
}
