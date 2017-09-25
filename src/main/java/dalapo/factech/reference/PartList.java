package dalapo.factech.reference;

import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PartList {
	SAW("sawblade", 0),
	GEAR("gear", 1),
	WIRE("wire", 2),
	BLADE("blade", 3),
	MIXER("mixer"),
	SHAFT("shaft", 11),
	MOTOR("motor", 10),
	DRILL("drill", 4),
	HEATELEM("heat_element", 5),
	CIRCUIT_0("circuit_1", 6),
	CIRCUIT_1("circuit_2", 6),
	CIRCUIT_2("circuit_3", 6),
	CIRCUIT_3("circuit_4", 6),
	MAGNET("magnet"),
	BATTERY("battery", 7),
	LENS("lens"),
	PISTON("piston", 8),
	CORE("core", 9),
	MESH("mesh"),
	NOT_A_PART("DNE");
	String name;
	Item salvage;
	int salvageMeta;
	
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
	
	private PartList(String str)
	{
		name = str;
		salvage = Items.AIR;
		salvageMeta = 0;
	}
	
	private PartList(String str, int salvageId)
	{
		name = str;
		salvage = ItemRegistry.salvagePart;
		salvageMeta = salvageId;
	}
	
	private PartList(String str, Item item, int dmg)
	{
		name = str;
		salvage = item;
		salvageMeta = dmg;
	}
}