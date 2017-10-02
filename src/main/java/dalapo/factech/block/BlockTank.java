package dalapo.factech.block;

import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.automation.TileEntityTank;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class BlockTank extends BlockTENoDir {

	public BlockTank(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, side, hitX, hitY, hitZ);

		ItemStack is = ep.getHeldItem(hand);
		IFluidHandlerItem cap = is.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		
		if (cap != null)
		{
			TileEntityTank te = (TileEntityTank)world.getTileEntity(pos);
			FluidStack fs = cap.getTankProperties()[0].getContents();
			if (fs == null)
			{
				if (te.getTankContents() != null && te.getTankContents().amount >= 1000)
				{
					FluidStack toTransfer = te.getTank().drain(Fluid.BUCKET_VOLUME, true);
					cap.fill(toTransfer, true);
				}
			}
			else
			{
				if (te.getTankContents() == null || (te.getTankContents().isFluidEqual(fs) && te.getTankContents().amount + 1000 < te.getTank().getCapacity()))
				{
					FluidStack toTransfer = cap.drain(1000, true);
					te.getTank().fill(toTransfer, true);
				}
			}
			ep.setHeldItem(hand, cap.getContainer()); // Dirty hack to make it update
			return true;
		}
		return false;
	}
}