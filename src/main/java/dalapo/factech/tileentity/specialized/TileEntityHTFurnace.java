package dalapo.factech.tileentity.specialized;

import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import net.minecraft.item.ItemStack;

public class TileEntityHTFurnace extends TileEntityBasicProcessor {

	public TileEntityHTFurnace() {
		super("elecfurnace", 2, RelativeSide.BACK);
		setDisplayName("Electric Furnace");
	}
	
	@Override
	public void onInventoryChanged(int slot)
	{
		super.onInventoryChanged(slot);
//		Logger.info(getRecipeList());
	}

	@Override
	protected Map<ItemStack, ItemStack> getRecipeList() {
		return MachineRecipes.HTFURNACE;
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.HEATELEM, 0.1F, 1.3F);
		partsNeeded[1] = new MachinePart(PartList.WIRE, 0.25F, 1.0F);
	}

	@Override
	public int getOpTime() {
		return 120;
	}

}