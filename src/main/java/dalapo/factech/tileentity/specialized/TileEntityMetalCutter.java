package dalapo.factech.tileentity.specialized;

import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import net.minecraft.item.ItemStack;

public class TileEntityMetalCutter extends TileEntityBasicProcessor {

	public TileEntityMetalCutter() {
		super("metalcutter", 2, RelativeSide.BOTTOM);
		setDisplayName("Metal Cutter");
	}

	@Override
	protected Map<ItemStack, ItemStack> getRecipeList() {
		// TODO Auto-generated method stub
		return MachineRecipes.METALCUTTER;
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.BLADE, 0.1F, 1.1F);
		partsNeeded[1] = new MachinePart(PartList.GEAR, 0.05F, 1.01F);
	}

	@Override
	public int getOpTime() {
		return 100;
	}
}