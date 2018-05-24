package dalapo.factech.tileentity.automation;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityItemQueue;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityConveyor extends TileEntityItemQueue
{
	@Override
	public int getCapacity()
	{
		return 10;
	}
	
	private EnumFacing getDirection()
	{
		return world.getBlockState(pos).getValue(StateList.directions).getOpposite();
	}
	
	public TileEntityConveyor()
	{	
	}
	
	@Override
	public void onLoad()
	{
		if (pos != BlockPos.ORIGIN) super.onLoad();
	}
	
	@Override
	public BlockPos getTarget()
	{
		return FacMathHelper.withOffset(pos, getDirection());
	}

	@Override
	protected void ejectItem(ItemStack toEject)
	{
		FacStackHelper.spawnEntityItem(world, toEject, getTarget(), true);
	}
}