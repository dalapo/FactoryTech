package dalapo.factech.tileentity.specialized;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityEnergizer extends TileEntityBasicInventory implements ITickable
{
	private boolean hasCore;
	private int age;
	public TileEntityEnergizer()
	{
		super("energizer", 1);
		setDisplayName("External Overclock");
	}

	public boolean isCharging()
	{
		return hasCore;
	}
	
	@Override
	public void onInventoryChanged(int slot)
	{
		if (!hasCore && getStackInSlot(slot).isItemEqual(new ItemStack(ItemRegistry.machinePart, 1, PartList.CORE.ordinal())))
		{
			getStackInSlot(slot).shrink(1);
			hasCore = true;
		}
	}
	
	@Override
	public void update()
	{
		if (hasCore)
		{
			if (++age >= 600)
			{
				age = 0;
				if (getStackInSlot(0).isEmpty())
				{
					hasCore = false;
				}
				else
				{
					decrStackSize(0, 1);
				}
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
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

	@Override
	public void sendInfoPacket(EntityPlayer ep) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("core"))
		{
			hasCore = nbt.getBoolean("core");
		}
		if (nbt.hasKey("age"))
		{
			age = nbt.getInteger("age");
		}
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("core", hasCore);
		nbt.setInteger("age", age);
		return nbt;
	}
}