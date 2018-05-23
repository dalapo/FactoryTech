package dalapo.factech.tileentity;

import dalapo.factech.block.BlockBase;
import dalapo.factech.render.tesr.IAnimatedModel;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAnimatedModel extends TileEntity
{
	@Override
	public Block getBlockType()
	{
		return world.getBlockState(pos).getBlock();
	}
}