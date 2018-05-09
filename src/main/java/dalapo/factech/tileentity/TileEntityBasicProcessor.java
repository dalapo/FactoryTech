package dalapo.factech.tileentity;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.PacketFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class TileEntityBasicProcessor extends TileEntityMachine {
	
	protected boolean hasWork = false;
	
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
		if (!getInput().isEmpty() && !is.isEmpty() && FacStackHelper.canCombineStacks(is, getOutput())) hasWork = true;
		else hasWork = false;
		Logger.info(String.format("hasWork: %s", hasWork));
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
	}
	
	protected ItemStack getOutput(ItemStack is)
	{
		for (MachineRecipe<ItemStack, ItemStack> entry : getRecipeList())
		{
			ItemStack in = entry.input();
			ItemStack out = entry.output();
			if ((FacStackHelper.matchStacksWithWildcard(in, is, false) && in.getCount() <= is.getCount()))
			{
				return out.copy();
			}
		}
		return ItemStack.EMPTY;
	}
	
	protected abstract List<MachineRecipe<ItemStack, ItemStack>> getRecipeList();
}