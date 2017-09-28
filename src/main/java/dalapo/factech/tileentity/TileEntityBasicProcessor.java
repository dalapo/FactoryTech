package dalapo.factech.tileentity;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.PacketFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class TileEntityBasicProcessor extends TileEntityMachine {
	
	protected boolean hasWork = false;
	protected static boolean useOreDict = false;
	
	// Convenience class - 1 input, 1 output
	public TileEntityBasicProcessor(String name, int partSlots, RelativeSide partHatch) {
		super(name, 1, partSlots, 1, partHatch);
	}
	
	public TileEntityBasicProcessor(String name, int partSlots)
	{
		this(name, partSlots, RelativeSide.BACK);
	}

	@Override
	protected boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	public void getHasWork()
	{
		@Nonnull ItemStack is = getOutput(getInput(0));
		if (!is.isEmpty() &&
				(FacStackHelper.matchOreDict(is, getOutput()) || getOutput().isEmpty()) &&
				is.getCount() + getOutput().getCount() <= 64) hasWork = true;
		else hasWork = false;
//		Logger.info(String.format("hasWork: %s", hasWork));
	}
	
	@Override
	protected boolean performAction() {
		ItemStack is = getOutput(getInput(0));
		getInput(0).shrink(1);
		if (getOutput().isEmpty())
		{
			setOutput(is);
		}
		else if (getOutput().getCount() + is.getCount() <= 64) getOutput().grow(is.getCount());
		getHasWork();
		return true;
//		markDirty();
	}
	
	protected ItemStack getOutput(ItemStack is)
	{
		for (Entry<ItemStack, ItemStack> entry : getRecipeList().entrySet())
		{
			ItemStack in = entry.getKey().copy();
			ItemStack out = entry.getValue().copy();
			if ((FacStackHelper.matchStacksWithWildcard(in, is, false) && in.getCount() <= is.getCount()))
			{
				return out;
			}
		}
		return ItemStack.EMPTY;
	}
	
	protected abstract Map<ItemStack, ItemStack> getRecipeList();
}