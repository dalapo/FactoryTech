package dalapo.factech.tileentity.specialized;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import dalapo.factech.auxiliary.MachineRecipes;
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
	protected Map<ItemStack, ItemStack> getRecipeList() {
		return MachineRecipes.MAGNETIZER;
	}

	@Override
	public boolean canRun()
	{
		return super.canRun() && FacMiscHelper.hasACPower(world, pos, prevTicks);
	}
	
	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.MOTOR, 0.1F, 1.05F, 0.6F*kValue[0][1], (int)(5*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.WIRE, 0.4F, 1.25F, 0.65F*kValue[1][1], (int)(3*kValue[1][0]));
	}

	@Override
	public int getOpTime() {
		return 160;
	}

}
