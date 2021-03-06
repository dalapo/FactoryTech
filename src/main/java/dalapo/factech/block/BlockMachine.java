package dalapo.factech.block;

import java.util.List;

import javax.annotation.Nonnull;

import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.PlayerChatPacket;
import dalapo.factech.reference.MachineInfoList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachine extends BlockDirectionalTile
{

	private int GuiId;
	private boolean hasObjModel;
	private String desc;
	public BlockMachine(Material materialIn, String name, String teid, int gui) {
		this(materialIn, name, teid, gui, true);
	}
	public BlockMachine(Material materialIn, String name, String teid, int gui, boolean hasObjModel)
	{
		super(materialIn, name, teid, true);
		GuiId = gui;
		setHardness(4F);
		setResistance(4F);
		this.hasObjModel = hasObjModel;
	}
	
	public BlockMachine(Material material, String name, String teid)
	{
		this(material, name, teid, 0);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (ep.isSneaking() || ep.getHeldItemMainhand().getItem().equals(ItemRegistry.magnifyingGlass)) return false;
		if (ep.getHeldItemMainhand().getItem().equals(ItemRegistry.upgrade))
		{
			if (!world.isRemote)
			{
				int upgrade = ep.getHeldItemMainhand().getItemDamage() + 1;
				if (!ep.isCreative()) ep.getHeldItemMainhand().shrink(1);
				TileEntityMachine te = (TileEntityMachine)world.getTileEntity(pos);
				te.installUpgrade(upgrade);
				world.notifyBlockUpdate(pos, state, state, 3);
			}
			return true;
		}
		ep.openGui(FactoryTech.instance, GuiId, world, pos.getX(), pos.getY(), pos.getZ());
		ep.openContainer.detectAndSendChanges();
		return true;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return !hasObjModel;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		TileEntityMachine te = (TileEntityMachine)world.getTileEntity(pos);
		for (int i=0; i<te.countPartSlots(); i++)
		{
			if (!te.hasPart(i)) return 15;
		}
		return 0;
	}

	@Override
	public void onWrenched(EntityPlayer ep, boolean isSneaking, World world, BlockPos pos, EnumFacing side)
	{
		if (isSneaking)
		{
			TileEntityMachine te = (TileEntityMachine)world.getTileEntity(pos);
			te.setActualPartSide(side);
			if (world.isRemote) FacChatHelper.sendChatToPlayer(ep, String.format("Set part input side to %s", side.toString()));
		}
		else
		{
			super.onWrenched(ep, isSneaking, world, pos, side);
		}
	}
}