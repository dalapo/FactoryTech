package dalapo.factech.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import dalapo.factech.init.TabRegistry;

public class ItemCoreStage extends ItemBase {

	public ItemCoreStage(String name) {
		super(name);
		maxStackSize = 1;
		setMaxDamage(100);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void actuallyAddInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		list.add("Charge: " + (100 - is.getItemDamage()) + "%");
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