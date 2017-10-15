package dalapo.factech.tileentity.specialized;

import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import dalapo.factech.FacTechConfigManager;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityTemperer extends TileEntityMachine
{
	private static final int deltaT = 5;
	
	// Can test for positive or negative Redstone edge if hasRedstone != hadRedstone
	private boolean hasRedstone = false;
	private boolean hadRedstone = false;
	
	private int activeRecipe = -1;
	private int activeTime = 0;
	
	public TileEntityTemperer()
	{
		super("temperer", 1, 3, 1, RelativeSide.BACK);
	}

	@Override
	public void getHasWork()
	{
		ItemStack input = getInput();
		
		if (FacTechConfigManager.allowMachineEnchanting)
		{
			if (input.getItem() == Items.IRON_SWORD || (input.getItem() instanceof ItemTool && ((ItemTool)input.getItem()).getToolMaterialName() == "IRON"))
			{
				activeRecipe = -2;
				activeTime = 83;
				return;
			}
			if (input.getItem() instanceof ItemArmor && ((ItemArmor)input.getItem()).getArmorMaterial() == ArmorMaterial.IRON)
			{
				activeRecipe = -2;
				activeTime = 116;
				return;
			}
		}
		
		for (int i=0; i<MachineRecipes.TEMPERER.size(); i++)
		{
			if (FacStackHelper.matchStacksWithWildcard(input, MachineRecipes.TEMPERER.get(i).input))
			{
				activeRecipe = i;
				activeTime = MachineRecipes.TEMPERER.get(i).time;
				return;
			}
		}
		activeRecipe = -1;
	}
	
	public boolean successfulWindow()
	{
		return age >= activeTime - deltaT && age <= activeTime + deltaT;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && (hasRedstone || hadRedstone) && activeRecipe != -1;
	}
	
	@Override
	public void update()
	{
		hasRedstone = world.isBlockIndirectlyGettingPowered(pos) > 0; // First line of update()
		if (canRun())
		{
			isRunning = true;
			
			// Temperer has run for too long - destroy input without outputting anything
			if (age++ > activeTime + deltaT)
			{
				getInput(0).shrink(1);
				age = 0;
			}
			
			if (hadRedstone && !hasRedstone)
			{
				// Only complete the process if the machine has run for the right amount of time
				if (age > activeTime - deltaT)
				{
//					Logger.info("Successful tempering");
					if (performAction()) consumeParts();
					getHasWork();
					markDirty();
					syncToAll();
				}
				age = 0;
			}
		}
		else
		{
			age = 0;
			isRunning = false;
		}
		replenishParts();
		
		hadRedstone = hasRedstone; // Last line of update()
	}
	
	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.HEATELEM, 0.5F, 1.2F, 0.8F, 10);
		partsNeeded[1] = new MachinePart(PartList.CIRCUIT_2, 0.2F, 1.1F, 0.75F, 8);
		partsNeeded[2] = new MachinePart(PartList.LENS, 0.5F, 1.0F, 0, 8);
	}

	@Override
	protected boolean performAction() {
		if (activeRecipe == -2)
		{
			return processEquipment();
		}
		else if (activeRecipe != -1)
		{
			ItemStack out = MachineRecipes.TEMPERER.get(activeRecipe).output;
			if (doOutput(out))
			{
				getInput().shrink(1);
				return true;
			}
		}
		return false;
	}

	private boolean processEquipment()
	{
		ItemStack toolOut = getInput(0).copy();
		NBTTagList enchantments = toolOut.getEnchantmentTagList();
		toolOut.setTagInfo("ench", enchantments);
		for (int i=0; i<enchantments.tagCount(); i++)
		{
			NBTTagCompound ench = enchantments.getCompoundTagAt(i);
			if (ench.getInteger("id") == 34)
			{
				return false; // Do nothing if it already has Unbreaking I or above
			}
		}
		NBTTagCompound ench = new NBTTagCompound();
		ench.setInteger("id", 34);
		ench.setInteger("lvl", 1);
		toolOut.getEnchantmentTagList().appendTag(ench);
		setOutput(toolOut);
		getInput(0).shrink(1);
		return true;
	}

	// Irrelevant for this TE but helps for readability?
	@Override
	public int getOpTime() {
		return activeTime;
	}
	
	public static class TempererRecipe
	{
		final ItemStack input;
		final ItemStack output;
		final int time;
		
		public TempererRecipe(ItemStack in, ItemStack out, int t)
		{
			input = in;
			output = out;
			if (t < 5) time = 5; else time = t;
		}
		
		public ItemStack getInput()
		{
			return input;
		}
		
		public ItemStack getOutput()
		{
			return output;
		}
		
		public int getTime()
		{
			return time;
		}
	}
}