package dalapo.factech.item;

import dalapo.factech.entity.EntityHoverScooter;
import dalapo.factech.helper.FacMathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemHoverScooter extends ItemBase
{
	public ItemHoverScooter(String name)
	{
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer ep, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		BlockPos bp = FacMathHelper.withOffset(pos, facing);
		EntityHoverScooter hoverscooter = new EntityHoverScooter(world, bp);
		if (!world.isRemote) world.spawnEntity(hoverscooter);
		return EnumActionResult.SUCCESS;
	}
}