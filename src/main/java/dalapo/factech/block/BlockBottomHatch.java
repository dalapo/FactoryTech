package dalapo.factech.block;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.reference.AABBList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBottomHatch extends BlockDirectional
{

	public BlockBottomHatch(Material materialIn, String name, boolean locked)
	{
		super(materialIn, name, locked);
		setHardness(3.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return AABBList.BOTTOM_IN;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!world.isRemote && entity instanceof EntityItem)
		{
			EnumFacing dir = world.getBlockState(pos).getValue(StateList.directions);
			BlockPos target = FacMathHelper.withOffset(pos, dir);
			entity.motionY = 0;
			entity.setPosition(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5);
			switch (dir)
			{
			case SOUTH:
				entity.motionZ = entity.motionY;
				break;
			case NORTH:
				entity.motionZ = -entity.motionY;
				break;
			case WEST:
				entity.motionX = entity.motionY;
				break;
			case EAST:
				entity.motionX = -entity.motionY;
				break;
				default:
					break;
			}
		}
	}
}
