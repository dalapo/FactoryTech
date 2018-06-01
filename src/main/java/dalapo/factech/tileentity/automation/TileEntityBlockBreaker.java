package dalapo.factech.tileentity.automation;

import dalapo.factech.block.BlockStackMover;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityBlockBreaker extends TileEntityBase implements ActionOnRedstone, ITickable
{
	private boolean isPowered = false;
	private boolean continuous = false;
	private int progress = 0;
	
	private void tryBreakBlock()
	{
		BlockPos posToBreak = FacMathHelper.withOffset(pos, FacBlockHelper.getDirection(world, pos));
		Block toBreak = world.getBlockState(posToBreak).getBlock();
		if (toBreak != Blocks.OBSIDIAN && toBreak != Blocks.BEDROCK)
		{
			toBreak.dropBlockAsItem(world, posToBreak, world.getBlockState(posToBreak), 0);
			world.setBlockToAir(posToBreak);
		}
	}
	
	@Override
	public void onRedstoneSignal(boolean isSignal, EnumFacing side)
	{
		if (world.isBlockPowered(pos) && !continuous)
		{
			if (!isPowered && isSignal)
			{
				tryBreakBlock();
			}
		}
		else if (!isSignal) isPowered = false;
	}

	public void invertMode(EntityPlayer toNotify)
	{
		continuous = !continuous;
		FacChatHelper.sendMessage(continuous ? "Now operating continuously" : "Now operating on Redstone");
	}
	
	@Override
	public void update()
	{
		if (continuous && !world.isBlockPowered(pos) && ++progress >= 20)
		{
			progress = 0;
			tryBreakBlock();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("continuous", continuous);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		continuous = nbt.getBoolean("continuous");
	}
}