package dalapo.factech.block;

import dalapo.factech.FactoryTech;
import dalapo.factech.reference.AABBList;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockItemRedis extends BlockTENoDir {

	public BlockItemRedis(Material materialIn, String name) {
		super(materialIn, name);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, side, hitX, hitY, hitZ);
		ep.openGui(FactoryTech.instance, 4, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return AABBList.TOP_IN;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!world.isRemote)
		{
//			Logger.info(String.format("Entity: %s, %s, %s", (int)entity.posX, (int)entity.posY, (int)entity.posZ));
//			Logger.info(String.format("Block: %s, %s, %s", pos.getX(), pos.getY(), pos.getZ()));
//			Logger.info(entity instanceof EntityItem);
			if (!(entity instanceof EntityItem)) return;
//			if ((int)entity.posX == pos.getX() && (int)(entity.posY + 0.1) > pos.getY() && (int)entity.posZ == pos.getZ())
			if (entity.isCollidedVertically && (int)(entity.posY + 0.1) > pos.getY())
			{
				ItemStack is = ((EntityItem)entity).getItem();
				entity.setDead();
				((TileEntityItemRedis)(world.getTileEntity(pos))).redistributeItems(is);
			}
		}
	}
}