package dalapo.factech.block;

import dalapo.factech.tileentity.automation.TileEntityBlockBreaker;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlockBreaker extends BlockDirectionalTile
{

	public BlockBlockBreaker(Material materialIn, String name, String teid, boolean locked)
	{
		super(materialIn, name, teid, locked);
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public void onWrenched(EntityPlayer ep, boolean isSneaking, World world, BlockPos pos, EnumFacing side)
	{
		if (!isSneaking) super.onWrenched(ep, isSneaking, world, pos, side);
		else
		{
			TileEntityBlockBreaker te = (TileEntityBlockBreaker)world.getTileEntity(pos);
			te.invertMode(ep);
		}
	}
}