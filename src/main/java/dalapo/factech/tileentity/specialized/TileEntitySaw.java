package dalapo.factech.tileentity.specialized;

import java.util.Map;
import java.util.Map.Entry;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.TileEntityMachine.MachinePart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

// Fun fact: The Chop Saw was the first Tile Entity coded in Factory Tech.
public class TileEntitySaw extends TileEntityBasicProcessor {
	
	public TileEntitySaw() {
		super("saw", 2);
		setDisplayName("Chop Saw");
	}
	
	protected void fillMachineParts()
	{
		partsNeeded[0] = new MachinePart(PartList.SAW, 0.15F, 1.1F, 0.7F, 10);
		partsNeeded[1] = new MachinePart(PartList.GEAR, 0.1F, 1.05F, 0.8F, 8);
	}
	
	protected Map<ItemStack, ItemStack> getRecipeList()
	{
		return MachineRecipes.SAW;
	}

	@Override
	public int getOpTime() {
		return 80;
	}
}