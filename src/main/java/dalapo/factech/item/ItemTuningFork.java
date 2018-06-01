package dalapo.factech.item;

import java.util.List;

import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.tileentity.automation.TileEntityPlaneShifter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTuningFork extends ItemBase
{
	public ItemTuningFork(String name)
	{
		super(name);
		setMaxStackSize(1);
	}
	
	@Override
	public void actuallyAddInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		if (is.hasTagCompound()) list.add("Attuned to dimension " + is.getTagCompound().getInteger("id"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer ep, EnumHand hand)
	{
		ItemStack is = ep.getHeldItem(hand);
		if (!world.isRemote && !ep.isSneaking())
		{
			if (!is.hasTagCompound())
			{
				is.setTagCompound(new NBTTagCompound());
			}
			is.getTagCompound().setInteger("id", ep.dimension);
			
			FacChatHelper.sendChatToPlayer(ep, String.format("Attuned to dimension ID " + ep.dimension));
		}
		return new ActionResult(EnumActionResult.PASS, is);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer ep, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack fork = ep.getHeldItem(hand);
		if (!world.isRemote && fork.hasTagCompound() && world.getTileEntity(pos) instanceof TileEntityPlaneShifter)
		{
			TileEntityPlaneShifter te = (TileEntityPlaneShifter)world.getTileEntity(pos);
			te.changeDimension(fork.getTagCompound().getInteger("id"));
			FacChatHelper.sendChatToPlayer(ep, "Attuned Plane Shifter at " + pos + " to dimension " + te.getDimension());
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}