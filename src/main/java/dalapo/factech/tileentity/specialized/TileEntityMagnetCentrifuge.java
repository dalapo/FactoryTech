package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.Map;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.TileEntityProcessorMultiOutput;
import net.minecraft.item.ItemStack;

public class TileEntityMagnetCentrifuge extends TileEntityProcessorMultiOutput
{

	public TileEntityMagnetCentrifuge()
	{
		super("magcent", 3, 3, RelativeSide.BACK);
		setDisplayName("Magnetic Centrifuge");
	}

	@Override
	public int getOpTime()
	{
		return 80;
	}

	@Override
	public List<MachineRecipe<ItemStack, ItemStack[]>> getRecipeList()
	{
		return MachineRecipes.MAGNET_CENTRIFUGE;
	}

}