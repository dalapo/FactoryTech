package dalapo.factech.auxiliary;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;

public class MachinePart
{
	private boolean isCrappy;
	public PartList id;
	private ItemStack depleted;
	private double quality = 1.0;
	
	private TileEntityMachine parent;
	private int numOperations = 0;
	private int minOperations;
	private float curBreakChance; // 1 = 100%
	private float baseBreakChance;
	public float salvageChance;
	private float increase;
	
	public MachinePart(PartList id, ItemStack salvage, float base, float inc, float chance, int minOperations)
	{
		if (base < 0 || inc < 0) throw new IllegalArgumentException ("Base and increase degrade chances must both be positive!");
//		if (inc < 1) Logger.warn("Detected increase value < 1, meaning the break chance will decrease every operation. Is this intentional?");
		this.id = id;
		this.minOperations = minOperations;
		numOperations = 0;
		baseBreakChance = base;
		curBreakChance = base;
		increase = inc;
		depleted = salvage.copy();
		salvageChance = chance;
		
		if (FactoryTech.DEBUG_GENERAL)
		{
			Logger.info("Creating MachinePart: " + id.getName());
		}
	}
	
	// May be negative
	public int getRemainingOperations()
	{
//		Logger.info(numOperations);
		return getActualMin() - numOperations;
	}
	
	public int getMinOperations()
	{
		return minOperations;
	}
	
	public float getBaseChance()
	{
		return baseBreakChance;
	}
	
	public float getIncrease()
	{
		return increase;
	}
	
	public float getSalvageChance()
	{
		return salvageChance;
	}
	
	public MachinePart(TileEntityMachine parent, ItemStack is, PartList part, float base, float inc, float chance, int numOperations)
	{
		this(part, is.copy(), base, inc, chance, numOperations);
		if (!part.hasCustomSalvage()) depleted = new ItemStack(ItemRegistry.salvagePart, 1, part.getSalvageMeta());
		this.parent = parent;
	}
	
	public MachinePart(TileEntityMachine parent, MachinePart toCopy)
	{
		this(parent, new ItemStack(toCopy.id.getSalvage(), toCopy.id.getSalvageAmount(), toCopy.id.getSalvageMeta()), toCopy.id, toCopy.baseBreakChance, toCopy.increase, toCopy.salvageChance, toCopy.minOperations);
	}
	public MachinePart(PartList part, float base, float inc, float chance, int numOperations)
	{
		this(part, new ItemStack(part.getSalvage(), 1, part.getSalvageMeta()), base, inc, chance, numOperations);
		if (!part.hasCustomSalvage()) depleted = new ItemStack(ItemRegistry.salvagePart, 1, part.getSalvageMeta());
	}
	
	public MachinePart(PartList part, float base, float inc)
	{
		this(part, ItemStack.EMPTY, base, inc, 0, 0);
	}
	
	public MachinePart(PartList part, float base, float inc, int min)
	{
		this(part, ItemStack.EMPTY, base, inc, 0, min);
	}

	public PartList getPartID()
	{
		return id;
	}
	
	public void reset(double quality)
	{
//		Logger.info("Resetting part " + id + " to quality " + quality);
		numOperations = 0;
		this.quality = quality;
		curBreakChance = baseBreakChance;
		if (id.hasBadVariant() && quality < 1.0) isCrappy = true;
		else isCrappy = false;
	}
	
	public boolean isBad()
	{
		return isCrappy;
	}
	
	public ItemStack getSalvage()
	{
		return depleted.copy();
	}
	
	private int getActualMin()
	{
		int actualMin = minOperations;
		switch (parent.getInstalledUpgrade())
		{
		case 1:
			actualMin *= 0.67;
			break;
		case 2:
			actualMin *= 1.5;
			break;
		case 3:
			// No effect
			break;
		case 4:
			actualMin *= 2;
			break;
		case 5:
			// No effect
		}
		return (int)(actualMin * quality);
	}

	public void increaseChance()
	{
		// TODO: Don't increase curBreakChance if machine has the Consistency upgrade
		numOperations++;
		if (numOperations >= getActualMin()) curBreakChance *= increase;
	}
	
	public boolean shouldBreak()
	{
		if (numOperations < getActualMin()) return false;
		
		switch (parent.getInstalledUpgrade())
		{
		case 1:
			return Math.random() / 1.5 < curBreakChance;
		case 2:
			return Math.random() * 1.5 < curBreakChance;
		case 4:
			return true;
			default:
			return Math.random() < curBreakChance;
		}
	}
	
	public String serializeNBT() // Well, for some value of "serialize", anyway
	{
		return String.format("%s:%s:%s:%s:%s", numOperations, curBreakChance, increase, quality, isCrappy);
	}
	
	public void deserializeNBT(String str)
	{
		String[] arr = str.split(":");
		numOperations = Integer.parseInt(arr[0]);
		curBreakChance = Float.parseFloat(arr[1]);
		increase = Float.parseFloat(arr[2]);
		quality = Double.parseDouble(arr[3]);
		isCrappy = Boolean.parseBoolean(arr[4]);
	}
}	