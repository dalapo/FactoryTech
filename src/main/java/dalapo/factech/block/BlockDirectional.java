package dalapo.factech.block;

import javax.annotation.Nonnull;

import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDirectional extends BlockBase implements Wrenchable {
	protected boolean planeLocked;
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockDirectional(Material materialIn, String name, boolean locked) {
		super(materialIn, name);
		setDefaultState(blockState.getBaseState().withProperty(StateList.directions, EnumFacing.NORTH));
		planeLocked = locked;
	}
	
	public void printInfo(EntityPlayer ep) {}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		EnumFacing direction = (planeLocked ? FacMathHelper.getDirectionFromEntityXZ(pos, placer) : FacMathHelper.getDirectionFromEntity(pos, placer));
//		Logger.info(direction);
		if (placer.isSneaking()) direction = direction.getOpposite();
		world.setBlockState(pos, state.withProperty(StateList.directions, direction));
	}
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, StateList.directions);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.directions).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return blockState.getBaseState().withProperty(StateList.directions, EnumFacing.getFront(meta));
	}

	@Override
	public void onWrenched(EntityPlayer ep, boolean isSneaking, World world, BlockPos pos, EnumFacing side) 
	{
		EnumFacing newFacing;
		if (planeLocked) newFacing = world.getBlockState(pos).getValue(StateList.directions).rotateY();
		else newFacing = FacBlockHelper.nextRotation(world, pos, world.getBlockState(pos).getValue(StateList.directions), false);
		world.setBlockState(pos, world.getBlockState(pos).withProperty(StateList.directions, newFacing));
	}
}