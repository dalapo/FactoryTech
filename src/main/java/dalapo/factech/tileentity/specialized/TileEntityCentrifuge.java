package dalapo.factech.tileentity.specialized;

import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityProcessorMultiOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityCentrifuge extends TileEntityProcessorMultiOutput {

	public TileEntityCentrifuge() {
		super("centrifuge", 3, 3, RelativeSide.BACK);
		
	}
	
	@Override
	public Map<ItemStack, ItemStack[]> getRecipeList() {
		return MachineRecipes.CENTRIFUGE;
	}

	@Override
	public int getOpTime() {
		return 50;
	}

}
