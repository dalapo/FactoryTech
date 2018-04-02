package dalapo.factech.item;

import dalapo.factech.FactoryTech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHandbook extends ItemBase
{
	public ItemHandbook(String name)
	{
		super(name);
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer ep, EnumHand hand)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			ep.openGui(FactoryTech.instance, 6, world, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
			return ActionResult.newResult(EnumActionResult.SUCCESS, ep.getHeldItem(hand));
		}
		return ActionResult.newResult(EnumActionResult.PASS, ep.getHeldItem(hand));
	}
}