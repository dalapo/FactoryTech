package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import net.minecraft.item.ItemStack;

public class TileEntityMetalCutter extends TileEntityBasicProcessor {

	public TileEntityMetalCutter() {
		super("metalcutter", 2, RelativeSide.BOTTOM);
		setDisplayName("Metal Cutter");
	}

	@Override
	protected List<MachineRecipe<ItemStack, ItemStack>> getRecipeList() {
		// TODO Auto-generated method stub
		return MachineRecipes.METALCUTTER;
	}

	@Override
	public int getOpTime() {
		return 75;
	}
}