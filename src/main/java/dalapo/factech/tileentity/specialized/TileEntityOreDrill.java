package dalapo.factech.tileentity.specialized;

import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityOreDrill extends TileEntityBasicProcessor {

	public TileEntityOreDrill() {
		super("drill", 3, RelativeSide.TOP);
		setDisplayName("Ore Drill");
	}
	
	@Override
	protected Map<ItemStack, ItemStack> getRecipeList() {
		return MachineRecipes.OREDRILL;
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.MOTOR, 0.1F, 1.1F, 0.7F, 5);
		partsNeeded[1] = new MachinePart(PartList.DRILL, 0.15F, 1.15F, 0.4F, 6);
		partsNeeded[2] = new MachinePart(PartList.WIRE, 0.2F, 1.1F, 0.8F, 4);
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 80;
	}

}
