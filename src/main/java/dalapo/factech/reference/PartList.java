package dalapo.factech.reference;

import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PartList {
	SAW("sawblade", 0, 1, 1.5, 1.75),
	GEAR("gear", 1, 1, 1.5, 1.75),
	WIRE("wire", 2, 1, 1.5),
	BLADE("blade", 3, 1, 1.5),
	MIXER("mixer"),
	SHAFT("shaft", 11),
	MOTOR("motor", 10, 1, 1.67, 2),
	DRILL("drill", 4, 1, 1.75),
	HEATELEM("heat_element", 5, 1, 1.67),
	CIRCUIT_0("circuit_1", 6, 1, 1.67),
	CIRCUIT_1("circuit_2", 6, 1, 1.67),
	CIRCUIT_2("circuit_3", 6, 1, 1.67),
	CIRCUIT_3("circuit_4", 6, 1, 1.67),
	MAGNET("magnet"),
	BATTERY("battery", 7, 1, 2),
	LENS("lens"),
	PISTON("piston", 8),
	CORE("core", 9),
	MESH("mesh"),
	NOT_A_PART("DNE", -1, new double[] {});
	
	String name;
	Item salvage;
	int salvageMeta;
	int numVariants;
	double[] lifetimes; // lifetimes[0] should always be 1
	
	public String getName()
	{
		return name;
	}
	
	public Item getSalvage()
	{
//		Logger.info("getSalvage(): " + salvage);
		return salvage;
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
	
	private PartList(String str)
	{
		name = str;
		salvage = Items.AIR;
		salvageMeta = 0;
		numVariants = 1;
		lifetimes = new double[] {1};
	}
	
	private PartList(String str, int salvageId)
	{
		name = str;
		salvage = ItemRegistry.salvagePart;
		salvageMeta = salvageId;
		numVariants = 1;
		lifetimes = new double[] {1};
	}
	
	private PartList(String str, int salvageId, double... lifetimes)
	{
		name = str;
		salvage = ItemRegistry.salvagePart;
		salvageMeta = salvageId;
		numVariants = lifetimes.length;
		this.lifetimes = lifetimes;
	}
}