package dalapo.factech.block;

import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockHatch extends BlockBase {

	private static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);

	public BlockHatch(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	public void printInfo(EntityPlayer ep) {}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return boundingBox;
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!world.isRemote)
		{
			if (!(entity instanceof EntityItem)) return;
			Pair<EnumFacing, TileEntity> pair = FacTileHelper.getFirstAdjacentTile(pos, world, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
//			Logger.info(pair == null ? "null" : pair);
			if (pair == null) return;
			IItemHandler inv = pair.b.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, pair.a.getOpposite());
			ItemStack is = ((EntityItem)entity).getItem();
			is = FacTileHelper.tryInsertItem(inv, is, pair.a.getOpposite().ordinal());
			if (is.isEmpty()) entity.setDead();
		}
	}
}