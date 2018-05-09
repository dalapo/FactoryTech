package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;
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

	protected List<MachineRecipe<ItemStack, ItemStack>> getRecipeList()
	{
		return MachineRecipes.SAW;
	}

	@Override
	public int getOpTime() {
		return 60;
	}
}