package dalapo.factech.tileentity.specialized;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityCoreCharger extends TileEntityMachine {

	public TileEntityCoreCharger() {
		super("corecharger", 1, 4, 0, RelativeSide.BACK);
		setDisplayName("Core Charger");
		isDisabledByRedstone = true;
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.BATTERY, 0.05F, 1.2F, 0.8F, 4);
		partsNeeded[1] = new MachinePart(PartList.CIRCUIT_0, 0.02F, 1.05F, 0.75F, 8);
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_1, 0.02F, 1.05F, 0.75F, 8);
		partsNeeded[3] = new MachinePart(PartList.MAGNET, 0.05F, 1.05F, 0.75F, 6);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return (index == 0);
	}
	
	@Override
	protected boolean performAction() {
		if (!getInput().getItem().equals(ItemRegistry.coreUnfinished)) return false;
		
		// Explode if the core is overcharged
		if (getInput().getItemDamage() <= 1)
		{
			for (int i=0; i<7; i++)
			{
				this.setInventorySlotContents(i, ItemStack.EMPTY);
			}
			world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
		}
		else
		{
			getInput().setItemDamage(getInput().getItemDamage() - 2);
		}
		return true;
	}

	@Override
	public int getOpTime() {
		return 10;
	}
}
