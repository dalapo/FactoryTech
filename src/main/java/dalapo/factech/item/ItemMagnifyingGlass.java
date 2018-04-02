package dalapo.factech.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.MachineInfoRequestPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityMagnet;
import dalapo.factech.tileentity.specialized.TileEntityStabilizer;

public class ItemMagnifyingGlass extends ItemBase {

	public ItemMagnifyingGlass(String name) {
		super(name);
		setCreativeTab(CreativeTabs.TOOLS);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			TileEntity te = world.getTileEntity(pos);
//			PacketHandler.sendToServer(new MachineInfoRequestPacket(pos));
			if (te instanceof TileEntityMachine)
			{
				((TileEntityMachine)te).showChatInfo(player);
				return EnumActionResult.SUCCESS;
			}
			else if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
			{
				IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
				for (int i=0; i<handler.getTankProperties().length; i++)
				{
					FluidStack fluid = handler.getTankProperties()[i].getContents();
					String fluidInfo;
					if (fluid == null) fluidInfo = "Empty";
					else fluidInfo = String.format("%s, %s", fluid.getLocalizedName(), fluid.amount);
					FacChatHelper.sendChatToPlayer(player, fluidInfo);
				}
				return EnumActionResult.SUCCESS;
			}
		}
		else
		{
			FacBlockHelper.updateBlock(world, pos);
		}
		return EnumActionResult.PASS;
	}

}
