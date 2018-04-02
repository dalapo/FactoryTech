package dalapo.factech.block;

import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockConveyor extends BlockDirectional {

	private static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
	public BlockConveyor(Material materialIn, String name, boolean locked) {
		super(materialIn, name, true);
		setHardness(2F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return boundingBox;
    }
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return boundingBox.offset(pos);
    }

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		return boundingBox;
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
	
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (entity instanceof EntityItem)
        {
          	EnumFacing facing = world.getBlockState(pos).getValue(StateList.directions);
	    	((EntityItem)entity).setNoDespawn();
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
	    	entity.posY = pos.getY() + 0.125;
        }
    }
}
