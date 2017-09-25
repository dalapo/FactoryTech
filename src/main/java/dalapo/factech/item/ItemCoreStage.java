package dalapo.factech.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.init.TabRegistry;

public class ItemCoreStage extends ItemBase {

	public ItemCoreStage(String name) {
		super(name);
		maxStackSize = 1;
		setMaxDamage(100);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		if (tab == TabRegistry.FACTECH)
		{
			subItems.add(new ItemStack(this, 1, 99));
			subItems.add(new ItemStack(this, 1, 20));
		}
    }
}