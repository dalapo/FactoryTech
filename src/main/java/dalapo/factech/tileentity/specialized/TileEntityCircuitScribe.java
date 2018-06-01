package dalapo.factech.tileentity.specialized;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityCircuitScribe extends TileEntityBasicProcessor {

	private byte pattern;
	public TileEntityCircuitScribe() {
		super("circuitscribe", 3, RelativeSide.BOTTOM);
		setDisplayName("Circuit Scribe");
		pattern = -1;
	}
	
	// Converts Quartz Plates to Empty Circuit Boards

	public int getPattern()
	{
		return pattern;
	}
	
	public void setPattern(byte p)
	{
		if (FacMathHelper.isInRange(p, -1, 4)) pattern = p;
	}
	
	@Override
	public void getHasWork()
	{
		if (!hasBadParts() &&
			getInput(0).getItem().equals(ItemRegistry.circuitIntermediate) &&
			getInput(0).getItemDamage() == 8 &&
			pattern != -1 && 
			(getOutput().isEmpty() || (getOutput().getItem() == ItemRegistry.circuitIntermediate && getOutput().getItemDamage() == pattern && getOutput().getCount() <= 64))) hasWork = true;
		else hasWork = false;
	}

	@Override
	protected boolean performAction()
	{
		getInput(0).shrink(1);
		doOutput(new ItemStack(ItemRegistry.circuitIntermediate, 1, pattern));
		getHasWork();
		return true;
	}

	@Override
	public int getOpTime() {
		return 200;
	}

	@Override
	protected List<MachineRecipe<ItemStack, ItemStack>> getRecipeList() {
		return MachineRecipes.CIRCUIT_SCRIBE;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setByte("pattern", pattern);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("pattern"))
		{
			pattern = nbt.getByte("pattern");
		}
	}
}
