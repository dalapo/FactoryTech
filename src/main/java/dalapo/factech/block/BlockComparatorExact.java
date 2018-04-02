package dalapo.factech.block;



import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockComparatorExact extends BlockDirectional
{
	public BlockComparatorExact(Material materialIn, String name)
	{
		super(materialIn, name, true);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor)
	{
		super.neighborChanged(state, world, pos, block, neighbor);
		EnumFacing direction = world.getBlockState(pos).getValue(StateList.directions);
		Logger.info(String.format("%s, %s", world.getRedstonePower(pos, direction.rotateY()), world.getRedstonePower(pos, direction.rotateYCCW())));
	}
	
	@Override
	public int getStrongPower(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		World world = (World)worldIn;
		EnumFacing direction = state.getValue(StateList.directions);
		if (side != direction) return 0;
		return world.getRedstonePower(pos, direction.rotateY()) == world.getRedstonePower(pos, direction.rotateYCCW()) ? 15 : 0;
	}
}