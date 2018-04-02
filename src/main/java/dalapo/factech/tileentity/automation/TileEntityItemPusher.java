package dalapo.factech.tileentity.automation;

import static dalapo.factech.FactoryTech.DEBUG_PACKETS;

import java.util.List;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityItemPusher extends TileEntityBasicInventory implements ITickable {

	public TileEntityItemPusher() {
		super("Pulse Piston", 9);
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = super.getUpdateTag();
		return writeToNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	private boolean isFilterEmpty()
	{
		for (int i=0; i<9; i++)
		{
			if (!getStackInSlot(i).isEmpty()) return false;
		}
		return true;
	}
	
	private boolean isItemInFilter(ItemStack is)
	{
		for (int i=0; i<9; i++)
		{
			if (getStackInSlot(i).isItemEqual(is)) return true;
		}
		return isFilterEmpty();
	}
	
	@Override
	public void update() {
		if (world.isBlockIndirectlyGettingPowered(pos) > 0 || !world.getBlockState(getPos()).getBlock().equals(BlockRegistry.itemPusher)) return;
		// Look at EntityItem instances in front of TE
		// Any that match filter get pushed away from it; inserted into inventory if one exists
		EnumFacing direction = world.getBlockState(getPos()).getValue(StateList.directions);
		AxisAlignedBB testBox = new AxisAlignedBB(FacMathHelper.withOffset(getPos(), direction));
		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, testBox);
		BlockPos targetSpace = FacMathHelper.withOffset(FacMathHelper.withOffset(getPos(), direction), direction);
		TileEntity te = world.getTileEntity(targetSpace);
		for (EntityItem ei : entities)
		{
			ItemStack is = ei.getItem();
			if (!isItemInFilter(is)) continue;
			if (!world.isRemote && te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite()))
			{
				IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite());
				ItemStack remaining = FacTileHelper.tryInsertItem(inventory, is, direction.getOpposite().ordinal());
				if (remaining.isEmpty()) ei.setDead();
				else ei.setItem(remaining);
			}
			else
			{
				switch(direction)
				{
				case SOUTH:
					ei.move(MoverType.PISTON, 0, 0, 1);
					break;
				case WEST:
					ei.move(MoverType.PISTON, -1, 0, 0);
					break;
				case NORTH:
					ei.move(MoverType.PISTON, 0, 0, -1);
					break;
				case EAST:
					ei.move(MoverType.PISTON, 1, 0, 0);
					break;
					default:
						// No-op
				}
			}
		}
		
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}