package dalapo.factech.tileentity.specialized;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityBasicInventory;

public class TileEntityBatteryGenerator extends TileEntityBasicInventory implements ITickable
{
	private static final int RF_PER_TICK = 30;
	private static final int BATTERY_LIFE = 200;
	
	private int lifeRemaining = 0;
	
	@CapabilityInject(CapabilityEnergy.class)
	private IEnergyStorage buffer = new EnergyStorage(20000, 100);
	
	public TileEntityBatteryGenerator(String name, int slots) {
		super(name, slots);
	}

	
	@Override
	public void update()
	{
		if (getStackInSlot(0).getItem() == ItemRegistry.machinePart)
		{
			buffer.receiveEnergy(RF_PER_TICK, false);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return (stack.getItem() == ItemRegistry.machinePart && PartList.getPartFromDamage(stack.getItemDamage()) == PartList.BATTERY);
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