package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityOreDrill extends TileEntityBasicProcessor {
	
	public TileEntityOreDrill() {
		super("drill", 2, RelativeSide.TOP);
		setDisplayName("Drill Grinder");
	}
	
	@Override
	protected List<MachineRecipe<ItemStack, ItemStack>> getRecipeList() {
		return MachineRecipes.OREDRILL;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 50;
	}

}
