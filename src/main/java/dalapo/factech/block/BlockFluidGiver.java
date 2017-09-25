package dalapo.factech.block;

import dalapo.factech.helper.FacMathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

// DEBUG; should be removed completely for release
public class BlockFluidGiver extends BlockBase {

	public BlockFluidGiver(Material materialIn, String name) {
		super(materialIn, name);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
//		if (!worldIn.isRemote)
//		{
			super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			for (EnumFacing f : EnumFacing.VALUES)
			{
				TileEntity te = worldIn.getTileEntity(FacMathHelper.withOffset(pos, f));
				if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
				{
					IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite());
					fluidHandler.fill(new FluidStack(FluidRegistry.LAVA, 200), true);
					te.markDirty();
				}
			}
//		}
		return true;
	}

}