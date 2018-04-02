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
	}

	@Override
	protected Map<ItemStack, ItemStack> getRecipeList() {
		return MachineRecipes.HTFURNACE;
	}

	@Override
	public int getOpTime() {
		return 100;
	}

}