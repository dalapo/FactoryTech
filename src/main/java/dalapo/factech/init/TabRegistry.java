package dalapo.factech.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabRegistry {
	public static CreativeTabs FACTECH;
	
	public static void init()
	{
		 FACTECH = new CreativeTabs("factorytech") {
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(BlockRegistry.saw);
			}
		};
	}
}