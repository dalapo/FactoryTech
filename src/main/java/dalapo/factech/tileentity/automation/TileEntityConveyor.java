package dalapo.factech.tileentity.automation;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityItemQueue;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityConveyor extends TileEntityItemQueue
{
	private EnumFacing direction;
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		direction = world.getBlockState(pos).getValue(StateList.directions);
	}
	
	@Override
	protected void cacheTileEntity()
	{
		TileEntity te = world.getTileEntity(FacMathHelper.withOffset(pos, direction));
		if (te instanceof TileEntityItemQueue) cachedTarget = (TileEntityItemQueue)te;
	}

	@Override
	protected void ejectItem(ItemStack toEject)
	{
		FacStackHelper.spawnEntityItem(world, toEject, FacMathHelper.withOffset(pos, direction), true);
	}

}