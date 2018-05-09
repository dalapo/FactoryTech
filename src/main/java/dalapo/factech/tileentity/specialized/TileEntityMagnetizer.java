package dalapo.factech.tileentity.specialized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;

public class TileEntityMagnetizer extends TileEntityBasicProcessor {

	private boolean[] prevTicks = new boolean[8];
	
	public TileEntityMagnetizer() {
		super("magnetizer", 2, RelativeSide.BOTTOM);
	}

	@Override
	protected List<MachineRecipe<ItemStack, ItemStack>> getRecipeList() {
		return MachineRecipes.MAGNETIZER;
	}

	@Override
	public boolean canRun()
	{
		return super.canRun() && FacMiscHelper.hasACPower(world, pos, prevTicks);
	}

	@Override
	public int getOpTime() {
		return 120;
	}

}
