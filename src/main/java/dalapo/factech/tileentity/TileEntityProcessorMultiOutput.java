package dalapo.factech.tileentity;

import java.util.List;

import javax.annotation.Nonnull;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.reference.PartList;
import net.minecraft.item.ItemStack;

// Could probably be combined with the TileEntityBasicProcessor but eh, whatever
public abstract class TileEntityProcessorMultiOutput extends TileEntityMachine {
	
	public TileEntityProcessorMultiOutput(String name, int partSlots, int outSlots, RelativeSide partHatch) {
		super(name, 1, partSlots, outSlots, partHatch);
	}

	public abstract List<MachineRecipe<ItemStack, ItemStack[]>> getRecipeList();
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	public void getHasWork()
	{
		Pair<Integer, ItemStack[]> data = getOutput(getInput(0));
		if (data == null)
		{
			hasWork = false;
			return;
		}
		ItemStack[] is = data.b;
		try {
			for (int i=0; i<is.length; i++)
			{
				if (!(is[i].isItemEqual(getOutput(i)) || getOutput(i).isEmpty()) ||
						getInput().getCount() < data.a || 
						is[i].isEmpty() || is[i].getCount() + getOutput(i).getCount() >= 64)
				{
					hasWork = false;
					return;
				}
			}
			hasWork = true;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			Logger.error(String.format("Machine %s attempted to output to a nonexistent slot: %s", this, e.getMessage()));
		}
	}
	
	@Override
	protected boolean performAction() {
		Pair<Integer, ItemStack[]> data = getOutput(getInput(0));
		ItemStack[] output = data.b;
		getInput(0).shrink(data.a);
		if (output.length > outSlots)
		{
			Logger.error("Who the hell designed this thing?");
			return false;
		}
		for (int i=0; i<output.length; i++)
		{
			if (getOutput(i).isEmpty()) setOutput(i, output[i].copy());
			else getOutput(i).grow(output[i].getCount());
		}
		getHasWork();
		markDirty();
		return true;
	}
	
	protected Pair<Integer, ItemStack[]> getOutput(ItemStack is)
	{
		for (MachineRecipe<ItemStack, ItemStack[]> entry : getRecipeList())
		{
			if (hasBadParts() && !entry.worksWithBad()) continue;
			ItemStack in = entry.input().copy();
			ItemStack[] out = new ItemStack[entry.output().length];
			if (FacStackHelper.matchStacksWithWildcard(in, is) && in.getCount() <= is.getCount())
			{
				for (int i=0; i<out.length; i++)
				{
					out[i] = entry.output()[i].copy();
				}
				return new Pair<Integer, ItemStack[]>(entry.input().getCount(), out);
			}
		}
		return null;
	}
	
}