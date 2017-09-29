package dalapo.factech.block;

import dalapo.factech.FactoryTech;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStackMover extends BlockTiered {

	public BlockStackMover(Material materialIn, String name, String teid, boolean locked, int tier) {
		super(materialIn, name, teid, locked, tier);
		setHardness(3F);
		setResistance(1F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, side, hitX, hitY, hitZ);
		if (tier > 0) ep.openGui(FactoryTech.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}