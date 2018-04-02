package dalapo.factech.block;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockBase extends Block {

	private boolean showInCreative = true;
	protected final String name;
	
	public BlockBase(Material materialIn, String name, boolean showInCreative) {
		super(materialIn);
		this.name = name;
		this.showInCreative = showInCreative;
		setCreativeTab(TabRegistry.FACTECH);
		setUnlocalizedName(NameList.MODID + "." + name);
		setRegistryName(name);
		setHardness(4F);
	}
	
	public BlockBase(Material materialIn, String name)
	{
		this(materialIn, name, true);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof ActionOnRedstone)
		{
			((ActionOnRedstone)te).onRedstoneSignal(world.isBlockPowered(pos));
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te != null) te.invalidate();
		if (te instanceof TileEntityBasicInventory)
		{
			for (int i=0; i<((TileEntityBasicInventory)te).getSizeInventory(); i++)
			{
				EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ((TileEntityBasicInventory)te).getStackInSlot(i));
				world.spawnEntity(ei);
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", NameList.MODID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
}