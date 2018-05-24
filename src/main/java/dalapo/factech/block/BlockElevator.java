package dalapo.factech.block;

import dalapo.factech.reference.AABBList;
import dalapo.factech.tileentity.automation.TileEntityElevator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElevator extends BlockTENoDir
{
	public BlockElevator(Material materialIn, String name)
	{
		super(materialIn, name);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		TileEntityElevator elevator = (TileEntityElevator)world.getTileEntity(pos);
		elevator.onLoad();
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		
		if (entity instanceof EntityItem && !entity.isDead)
		{
			entity.setDead();
			TileEntityElevator te = (TileEntityElevator)world.getTileEntity(pos);
			te.scheduleItemStack(((EntityItem)entity).getItem());
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABBList.COLUMN;
    }
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
}