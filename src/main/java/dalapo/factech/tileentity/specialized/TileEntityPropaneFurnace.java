package dalapo.factech.tileentity.specialized;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityPropaneFurnace extends TileEntityMachine {

	private int propaneRemaining;
	public TileEntityPropaneFurnace()
	{
		super("propanefurnace", 2, 0, 2, RelativeSide.BACK);
		setDisplayName("Propane Furnace");
		propaneRemaining = 0;
	}

	@Override
	protected void fillMachineParts()
	{
//		partsNeeded[0] = new MachinePart(PartList.WIRE, 0.05F, 1.1F);
	}

	public int getPropane()
	{
		return propaneRemaining;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	@Override
	public void getHasWork()
	{
		ItemStack is = findOutput();
		if (!is.isEmpty() && FacStackHelper.canCombineStacks(getOutput(0), is) && propaneRemaining > 0)
		{
			hasWork = true;
		}
		else hasWork = false;
	}
	
	@Nonnull
	private ItemStack findOutput()
	{
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		for (Entry<ItemStack, ItemStack> smelt : recipes.entrySet())
		{
			if (FacStackHelper.matchOreDict(smelt.getKey(), getInput(0)))
			{
//				Logger.info(smelt.getValue());
				return smelt.getValue().copy();
			}
		}
		return ItemStack.EMPTY;
	}
	
	private void consumeFuelTank()
	{
		if (FacStackHelper.matchStacks(getInput(1), ItemRegistry.tank, 3))
		{
			propaneRemaining += 8;
			getInput(1).shrink(1);
			doOutput(new ItemStack(ItemRegistry.tank, 1, 0), 1);
		}
	}
	
	@Override
	protected void onInventoryChanged(int slot)
	{
		super.onInventoryChanged(slot);
		if (slot == 1 && propaneRemaining <= 0) consumeFuelTank();
	}
	
	@Override
	protected boolean performAction() {
		if (doOutput(findOutput()))
		{
			if (--propaneRemaining <= 0) consumeFuelTank();
			getInput(0).shrink(1);
		}
		return true;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack is, EnumFacing side)
	{
		if (FacStackHelper.matchStacks(is, ItemRegistry.tank, 3))
		{
			return (slot == 1);
		}
		else return super.canInsertItem(slot, is, side);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack is, EnumFacing side)
	{
		if (side == EnumFacing.UP)
		{
			return (slot == 4);
		}
		else return super.canExtractItem(slot, is, side);
	}

	@Override
	public int getOpTime() {
		return 30;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("propane", propaneRemaining);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		propaneRemaining = nbt.getInteger("propane");
	}
}
