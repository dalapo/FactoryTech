package dalapo.factech.tileentity.automation;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityAutoPuller extends TileEntity implements ITickable {
	int age = 0;
	TileEntity adj;
	
	public TileEntityAutoPuller()
	{
		super();
	}
	
	public void updateInv()
	{
		if (!world.getBlockState(getPos()).getBlock().equals(BlockRegistry.autopuller)) return;
		EnumFacing dir = world.getBlockState(getPos()).getValue(StateList.directions);
		adj = world.getTileEntity(FacMathHelper.withOffset(getPos(), dir.getOpposite()));
		if (adj == null || !(adj instanceof IInventory || adj.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite())))
		{
			adj = null;
		}
	}
	
	@Override
	public void update()
	{
		if (world.isRemote) return;
		updateInv();
		if (adj == null) return;
		IItemHandler inv = adj.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, world.getBlockState(getPos()).getValue(StateList.directions).getOpposite());
		if (inv == null) return;
		if (++age % 4 == 0 && world.isBlockIndirectlyGettingPowered(getPos()) == 0)
		{
			for (int i=0; i<inv.getSlots(); i++)
			{
				if (!inv.extractItem(i, 1, true).isEmpty())
				{
					ItemStack is = inv.extractItem(i, 1, false);
					EnumFacing dir = world.getBlockState(getPos()).getValue(StateList.directions);
					BlockPos pos = FacMathHelper.withOffset(getPos(), dir);
					TileEntity te = world.getTileEntity(FacMathHelper.withOffset(getPos(), dir));
					if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()))
					{
						is = FacTileHelper.tryInsertItem(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()), is, dir.getOpposite().ordinal());
						if (is.isEmpty()) return;
					}
					EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5, is);
					ei.motionY = 0;
					switch (dir)
					{
					case WEST:
						ei.motionX = -0.1;
						ei.motionZ = 0;
						break;
					case EAST:
						ei.motionX = 0.1;
						ei.motionZ = 0;
						break;
					case SOUTH:
						ei.motionX = 0;
						ei.motionZ = 0.1;
						break;
					case NORTH:
						ei.motionX = 0;
						ei.motionZ = -0.1;
						break;
					case UP:
						ei.motionX = 0;
						ei.motionY = 0.2;
						ei.motionZ = 0;
						break;
					case DOWN:
						ei.motionX = 0;
						ei.motionY = -0.2;
						ei.motionZ = 0;
						break;
						default:
							ei.motionX = 0;
							ei.motionZ = 0;
					}
					world.spawnEntity(ei);
					break;
				}
			}
		}
	}
}