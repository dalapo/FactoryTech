package dalapo.factech.item;

import javax.annotation.Nullable;

import dalapo.factech.auxiliary.Linkable;
import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase {
	
	public static final String LINK_TAG = "link_pos"; // NBT tag for linked block

	public ItemWrench(String name)
	{
		super(name);
		setMaxStackSize(1);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        target.attackEntityFrom(new EntityDamageSource("melee", attacker), 2);
        return true;
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
			if (!hasLink(is))
			{
				setLink(is, pos);
			}
			else
			{
				((Linkable)world.getBlockState(pos).getBlock()).onLinked(world, player, pos, getLink(is), is);
				clearLink(is);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	/**
	 * Returns true if the given ItemStack has an NBT tag with key LINK_TAG.
	 */
	public boolean hasLink(ItemStack wrench)
	{
		return wrench.hasTagCompound() && wrench.getTagCompound().hasKey(LINK_TAG);
	}
	
	/**
	 * Returns the BlockPos of the linked block, or null if no block is linked.
	 */
	@Nullable
	public BlockPos getLink(ItemStack wrench)
	{
		return hasLink(wrench) ? BlockPos.fromLong(wrench.getTagCompound().getLong(LINK_TAG)) : null;
	}

	/**
	 * Sets LINK_TAG of the given ItemStack's NBT tag to the given BlockPos.
	 */
	public void setLink(ItemStack wrench, BlockPos link)
	{
		NBTTagCompound tag;
		if (!wrench.hasTagCompound())
		{
			wrench.setTagCompound(new NBTTagCompound());
		}
		wrench.getTagCompound().setLong(LINK_TAG, link.toLong());
	}
	
	/**
	 * Removes LINK_TAG from the given ItemStack's NBT tag, if it exists.
	 */
	public void clearLink(ItemStack wrench)
	{
		if (hasLink(wrench))
		{
			wrench.getTagCompound().removeTag(LINK_TAG);
		}
	}
	
}