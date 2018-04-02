package dalapo.factech.reference;

import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PartList {
	SAW("sawblade", "Saw Blade", 0, 1.0, 1.5, 1.75),
	GEAR("gear", "Gear", 1, 1.0, 1.5, 1.75),
	WIRE("wire", "Wire", 2, 1.0, 1.5),
	BLADE("blade", "Cutting Blade", 3, 1.0, 1.5),
	MIXER("mixer", "Mixing Blades"),
	SHAFT("shaft", "Shaft", 11, 1.0),
	MOTOR("motor", "Motor", 10, 1.0, 1.67, 2),
	DRILL("drill", "Drillbit", 4, 1.0, 1.75),
	HEATELEM("heat_element", "Heating Element", 5, 1.0, 1.67),
	CIRCUIT_0("circuit_1", "Circuit (1)", 6, 1.0, 1.67),
	CIRCUIT_1("circuit_2", "Circuit (2)", 6, 1.0, 1.67),
	CIRCUIT_2("circuit_3", "Circuit (3)", 6, 1.0, 1.67),
	CIRCUIT_3("circuit_4", "Circuit (4)", 6, 1.0, 1.67),
	MAGNET("magnet", "Magnet", Items.IRON_INGOT, 1, 0),
	BATTERY("battery", "Battery", 7, 1.0, 2.0),
	LENS("lens", "Focusing Lens"),
	PISTON("piston", "Integrated Piston", 8, 1.0),
	CORE("core", "Energy Core", 9, 1.0),
	MESH("mesh", "Wooden Mesh", Items.STICK, 4, 0),
	NOT_A_PART("DNE", "DNE", -1, new double[] {});
	
	String name;
	String displayName;
	Item salvage;
	int salvageAmount;
	int salvageMeta;
	int numVariants;
	boolean hasCustomSalvage = false;
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
		int acc = 0;
		for (int i=0; PartList.values()[i] != this; i++)
		{
			acc+=PartList.values()[i].numVariants;
		}
		return acc;
	}
	
	public static PartList getPartFromDamage(int dmg)
	{
		int acc = 0;
		for (int i=0; i<values().length; i++)
		{
			acc += values()[i].getNumVariants();
			if (acc > dmg)
			{
//				Logger.info(PartList.values()[i]);
				return PartList.values()[i];
			}
		}
		return NOT_A_PART;
	}
	
	public static int getQualityFromDamage(int dmg)
	{
		for (int i=0; i<values().length; i++)
		{
			if (values()[i].getFloor() > dmg)
			{
				return dmg - PartList.values()[i-1].getFloor();
			}
		}
		return 1;
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
	
	private PartList(String str, String displayName, int salvageId, double... lifetimes)
	{
		name = str;
		salvage = ItemRegistry.salvagePart;
		salvageAmount = 1;
		salvageMeta = salvageId;
		numVariants = lifetimes.length;
		this.lifetimes = lifetimes;
		this.displayName = displayName;
	}
}