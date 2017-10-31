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
		partsNeeded[0] = new MachinePart(PartList.BLADE, 0.1F, 1.1F, 0.5F*kValue[0][1], (int)(8*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.GEAR, 0.2F, 1.2F, 0.7F*kValue[1][1], (int)(6*kValue[1][0]));
	}

	@Override
	public int getOpTime() {
		return 100;
	}
}