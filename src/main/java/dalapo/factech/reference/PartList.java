package dalapo.factech.reference;

import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PartList {
	SAW("sawblade", "Saw Blade", 0, true, 0.5, 1.0, 1.5, 1.75),
	GEAR("gear", "Gear", 1, true, 0.5, 1.0, 1.5, 1.75),
	WIRE("wire", "Wire", 2, false, 1.0, 1.5),
	BLADE("blade", "Cutting Blade", 3, true, 0.5, 1.0, 1.5),
	MIXER("mixer", "Mixing Blades"),
	SHAFT("shaft", "Shaft", 11, false, 1.0),
	MOTOR("motor", "Motor", 10, false, 1.0, 1.67, 2),
	DRILL("drill", "Drillbit", 4, true, 0.5, 1.0, 1.75),
	HEATELEM("heat_element", "Heating Element", 5, false, 1.0, 1.67),
	CIRCUIT_0("circuit_1", "Circuit (1)", 6, false, 1.0, 1.67),
	CIRCUIT_1("circuit_2", "Circuit (2)", 6, false, 1.0, 1.67),
	CIRCUIT_2("circuit_3", "Circuit (3)", 6, false, 1.0, 1.67),
	CIRCUIT_3("circuit_4", "Circuit (4)", 6, false, 1.0, 1.67),
	MAGNET("magnet", "Magnet", Items.IRON_INGOT, 1, 0),
	BATTERY("battery", "Battery", 7, false, 1.0, 2.0),
	LENS("lens", "Focusing Lens"),
	PISTON("piston", "Integrated Piston", 8, false, 1.0),
	CORE("core", "Energy Core", 9, false, 1.0),
	MESH("mesh", "Wooden Mesh", Items.STICK, 4, 0),
	NOT_A_PART("DNE", "DNE", -1, false, new double[] {});
	
	String name;
	String displayName;
	Item salvage;
	int salvageAmount;
	int salvageMeta;
	int numVariants;
	boolean hasCustomSalvage = false;
	boolean hasBadVariant = false;
	double[] lifetimes; // lifetimes[0] should always be 1
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasCustomSalvage()
	{
		return hasCustomSalvage;
	}
	
	public Item getSalvage()
	{
//		Logger.info("getSalvage(): " + salvage);
		return salvage;
	}
	
	public int getSalvageAmount()
	{
		return salvageAmount;
	}
	
	public int getSalvageMeta()
	{
		return salvageMeta;
	}
	
	public int getNumVariants()
	{
		return numVariants;
	}
	
	public int getFloor()
	{
		return ordinal()*10;
	}
	
	public boolean hasBadVariant()
	{
		return hasBadVariant;
	}
	
	public static PartList getPartFromDamage(int dmg)
	{
		try {
			return values()[dmg / 10];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return NOT_A_PART;
		}
	}
	
	public static int getQualityFromDamage(int dmg)
	{
		return dmg % 10;
	}
	
	public static PartList getPartFromString(String part)
	{
		for (int i=0; i<values().length; i++)
		{
			if (values()[i].name.equalsIgnoreCase(part)) return values()[i];
		}
		return NOT_A_PART;
	}
	
	public double getLifetimeModifier(int quality)
	{
		return lifetimes[quality];
	}
	
	public static int getItemDamage(PartList id, int quality)
	{
		return id.getFloor() + quality;
	}
	
	public static int getTotalVariants()
	{
		int acc = 0;
		for (PartList p : PartList.values())
		{
			acc += p.numVariants;
		}
		return acc;
	}
	
	private PartList(String str, String displayName)
	{
		name = str;
		salvage = Items.AIR;
		salvageMeta = 0;
		numVariants = 1;
		lifetimes = new double[] {1};
		this.displayName = displayName;
	}
	
	private PartList(String str, String displayName, Item salvageId, int salvageCount, int salvageDmg)
	{
		name = str;
		salvage = salvageId;
		salvageMeta = salvageDmg;
		salvageAmount = salvageCount;
		numVariants = 1;
		lifetimes = new double[] {1};
		this.displayName = displayName;
		hasCustomSalvage = true;
	}
	
	private PartList(String str, String displayName, int salvageId, boolean hasBadVariant, double... lifetimes)
	{
		name = str;
		salvage = ItemRegistry.salvagePart;
		salvageAmount = 1;
		salvageMeta = salvageId;
		numVariants = lifetimes.length;
		this.lifetimes = lifetimes;
		this.displayName = displayName;
		this.hasBadVariant = hasBadVariant;
	}
}