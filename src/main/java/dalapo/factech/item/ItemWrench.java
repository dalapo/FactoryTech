package dalapo.factech.item;

import dalapo.factech.auxiliary.Linkable;
import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase {

	public ItemWrench(String name) {
		super(name);
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack is = player.getHeldItem(hand);
		if (world.getBlockState(pos).getBlock() instanceof Wrenchable)
		{
			((Wrenchable)world.getBlockState(pos).getBlock()).onWrenched(player, player.isSneaking(), world, pos, side);
			return EnumActionResult.SUCCESS;
		}
		
		if (world.getBlockState(pos).getBlock() instanceof Linkable)
		{
			if (!is.hasTagCompound())
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setLong("pos", pos.toLong());
				is.setTagCompound(nbt);
			}
			else
			{
				((Linkable)world.getBlockState(pos).getBlock()).onLinked(world, player, BlockPos.fromLong(is.getTagCompound().getLong("pos")), pos, is);
				is.setTagCompound(null);
			}
		}
		return EnumActionResult.PASS;
	}
}