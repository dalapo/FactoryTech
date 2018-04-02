package dalapo.factech.block;

import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHeavyConveyor extends BlockConveyor
{
	public BlockHeavyConveyor(Material materialIn, String name, boolean locked)
	{
		super(materialIn, name, locked);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
		if (!entity.isAirBorne)
		{
	      	EnumFacing facing = world.getBlockState(pos).getValue(StateList.directions);
	    	switch (facing)
	    	{
	    	case WEST:
	    		entity.motionX = 0.1;
	    		entity.posZ = pos.getZ() + 0.5;
	    		break;
	    	case EAST:
	    		entity.motionX = -0.1;
	    		entity.posZ = pos.getZ() + 0.5;
	    		break;
	    	case SOUTH:
	    		entity.motionZ = -0.1;
	    		entity.posX = pos.getX() + 0.5;
	    		break;
	    	case NORTH:
	    		entity.motionZ = 0.1;
	    		entity.posX = pos.getX() + 0.5;
	    		break;
			default:
				entity.motionX = 0.0;
				entity.motionZ = 0.0;
	    	}
 //   	entity.posY = pos.getY() + 0.125;
		}
    }
}