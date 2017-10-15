package dalapo.factech.block;

import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.init.ItemRegistry;
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

public class BlockInventoryDirectional extends BlockDirectional implements ITileEntityProvider {

	protected boolean canBeRotated = false;
	protected String tile;
	private int gui;
	
	public BlockInventoryDirectional(Material material, String name, String teid, boolean locked, int gui)
	{
		super(material, name, locked);
		tile = teid;
		this.gui = gui;
	}
	public BlockInventoryDirectional(Material materialIn, String name, String teid, boolean locked) {
		super(materialIn, name, locked);
		tile = teid;
		gui = -1;
	}
	
	public BlockInventoryDirectional enableRotating()
	{
		canBeRotated = true;
		return this;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (gui == -1 || ep.isSneaking()) return false;
		
		ep.openGui(FactoryTech.instance, gui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		world.addTileEntity(createTileEntity(world, state));
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
	
	@Override
	public void onWrenched(boolean isSneaking, World world, BlockPos pos, EnumFacing side)
	{
		if (canBeRotated)
		{
			super.onWrenched(isSneaking, world, pos, side);
		}
	}
}