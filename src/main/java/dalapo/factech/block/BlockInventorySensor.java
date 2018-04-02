package dalapo.factech.block;

import javax.annotation.Nonnull;

import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.automation.TileEntityInventorySensor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInventorySensor extends BlockInventoryDirectional
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockInventorySensor(Material materialIn, String name, String teid, boolean locked, int guiid)
	{
		super(materialIn, name, teid, locked, guiid);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos thisPos, BlockPos neighbour)
	{
		super.onNeighborChange(world, thisPos, neighbour);
		TileEntityInventorySensor te = (TileEntityInventorySensor)world.getTileEntity(thisPos);
		te.updateState();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos thisPos, Block otherBlock, BlockPos otherPos)
	{
		super.neighborChanged(state, world, thisPos, otherBlock, otherPos);
		TileEntityInventorySensor te = (TileEntityInventorySensor)world.getTileEntity(thisPos);
		te.updateState();
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {StateList.directions, POWERED});
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		if (side != state.getValue(StateList.directions))
		{
			return state.getValue(POWERED) ? 15 : 0;
		}
		return 0;
	}
}