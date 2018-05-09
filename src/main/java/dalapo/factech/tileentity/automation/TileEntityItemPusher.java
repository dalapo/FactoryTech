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

	private boolean ignoreDamage;
	
	public TileEntityItemPusher() {
		super("Pulse Piston", 9);
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
			if (ignoreDamage)
			{
				if (getStackInSlot(i).isItemEqualIgnoreDurability(is)) return true;
			}
			else if (getStackInSlot(i).isItemEqual(is)) return true;
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
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("ignoreDamage", ignoreDamage);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		ignoreDamage = nbt.getBoolean("ignoreDamage");
	}
	
	@Override
	public int getField(int id)
	{
		if (id == 0) return ignoreDamage ? 1 : 0;
		else return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			ignoreDamage = (value != 0);
		}
	}

	@Override
	public int getFieldCount()
	{
		return 1;
	}
}