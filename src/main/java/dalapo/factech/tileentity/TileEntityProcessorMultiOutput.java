package dalapo.factech.tileentity;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import net.minecraft.item.ItemStack;

// Could probably be combined with the TileEntityBasicProcessor but eh, whatever
public abstract class TileEntityProcessorMultiOutput extends TileEntityMachine {
	
	public TileEntityProcessorMultiOutput(String name, int partSlots, int outSlots, RelativeSide partHatch) {
		super(name, 1, partSlots, outSlots, partHatch);
	}

	public abstract Map<ItemStack, ItemStack[]> getRecipeList();
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	public void getHasWork()
	{
		ItemStack[] is = getOutput(getInput(0));
		if (is == null)
		{
			hasWork = false;
			return;
		}
		try {
			for (int i=0; i<is.length; i++)
			{
				if (!(is[i].isItemEqual(getOutput(i)) || getOutput(i).isEmpty()) ||
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
		ItemStack[] output = getOutput(getInput(0));
		getInput(0).shrink(1);
		if (output.length > outSlots)
		{
			Logger.error("Who the hell designed this thing?");
			return false;
		}
		for (int i=0; i<output.length; i++)
		{
			if (getOutput(i).isEmpty()) setOutput(i, output[i]);
			else getOutput(i).grow(output[i].getCount());
		}
		getHasWork();
		markDirty();
		return true;
	}
	
	protected ItemStack[] getOutput(ItemStack is)
	{
		for (Entry<ItemStack, ItemStack[]> entry : getRecipeList().entrySet())
		{
			ItemStack in = entry.getKey().copy();
			ItemStack[] out = new ItemStack[entry.getValue().length];
			if (FacStackHelper.matchOreDict(in, is) && in.getCount() <= is.getCount())
			{
				for (int i=0; i<out.length; i++)
				{
					out[i] = entry.getValue()[i].copy();
				}
				return out;
			}
		}
		return null;
	}
	
}