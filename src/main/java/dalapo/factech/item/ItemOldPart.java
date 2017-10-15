package dalapo.factech.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.PartList;

public class ItemOldPart extends ItemBase
{

	public ItemOldPart(String name) {
		super(name);
		setHasSubtypes(true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		
	}
	
	@Override
	protected void actuallyAddInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		list.add("Put in a crafting grid to restore");
		list.add("Will be removed altogether in the next update");
	}
}
