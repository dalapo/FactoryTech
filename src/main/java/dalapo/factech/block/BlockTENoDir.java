package dalapo.factech.block;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacTileHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTENoDir extends BlockBase implements ITileEntityProvider {

	protected String tile;
	private int guiID;
	
	public BlockTENoDir(Material materialIn, String name) {
		super(materialIn, name);
		tile = name;
		guiID = -1;
	}
	
	public BlockTENoDir(Material materialIn, String name, int id)
	{
		super(materialIn, name);
		tile = name;
		guiID = id;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		world.addTileEntity(createTileEntity(world, state));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (guiID == -1 || ep.isSneaking()) return false;
		
		ep.openGui(FactoryTech.instance, guiID, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return FacTileHelper.getTileFromID(tile);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return FacTileHelper.getTileFromID(tile);
	}
}
