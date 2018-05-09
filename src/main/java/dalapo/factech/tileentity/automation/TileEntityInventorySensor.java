package dalapo.factech.tileentity.automation;

import dalapo.factech.FactoryTech;
import dalapo.factech.block.BlockInventorySensor;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityInventorySensor extends TileEntityBasicInventory
{
	private boolean matchAll; // Alternative: Match Any
	private boolean exactly; // Alternative: At Least
	private boolean useDamage; // Alternative: Ignore Damage
	
	private IItemHandler attachedInventory;
	
	public TileEntityInventorySensor()
	{
		super("inventorySensor", 9);
		setDisplayName("Criteria");
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
	}
	
	@Override
	public void onInventoryChanged(int slot)
	{
		updateState();
	}
	
	// updateState: Called whenever a neighbouring block or inventory updates.
	public void updateState()
	{
		EnumFacing dir = world.getBlockState(pos).getValue(StateList.directions);
		TileEntity adj = world.getTileEntity(FacMathHelper.withOffset(pos, dir.getOpposite()));
		if (adj == null) attachedInventory = null;
		else attachedInventory = adj.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir);
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInventorySensor.POWERED, meetsCriteria()));
		FacBlockHelper.updateBlock(world, pos);
	}

	public boolean meetsCriteria()
	{
		if (attachedInventory == null) return false;
		boolean matchesAny = false;
		boolean matchesAll = true;
		
		for (int i=0; i<9; i++)
		{
			ItemStack current = getStackInSlot(i);
			if (current.isEmpty()) continue;
			int count = FacTileHelper.countItems(attachedInventory, current.getItem(), useDamage ? current.getItemDamage() : 32767);
			if (exactly ? count == current.getCount() : count >= current.getCount()) matchesAny = true;
			else matchesAll = false;
		}
		return matchAll ? matchesAll : matchesAny;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("matchAll", matchAll);
		nbt.setBoolean("exactly", exactly);
		nbt.setBoolean("useDamage", useDamage);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		matchAll = nbt.getBoolean("matchAll");
		exactly = nbt.getBoolean("exactly");
		useDamage = nbt.getBoolean("useDamage");
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return matchAll ? 1 : 0;
		case 1:
			return exactly ? 1 : 0;
		case 2:
			return useDamage ? 1 : 0;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		boolean val = value != 0;
		switch (id)
		{
		case 0:
			matchAll = val;
			break;
		case 1:
			exactly = val;
			break;
		case 2:
			useDamage = val;
			break;
		}
		updateState();
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}