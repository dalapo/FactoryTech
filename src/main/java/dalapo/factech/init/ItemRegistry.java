package dalapo.factech.init;

import java.util.List;

import dalapo.factech.item.ItemBase;
import dalapo.factech.item.ItemCoreStage;
import dalapo.factech.item.ItemElementSword;
import dalapo.factech.item.ItemHandPump;
import dalapo.factech.item.ItemModelProvider;
import dalapo.factech.item.ItemMagnifyingGlass;
import dalapo.factech.item.ItemHandbook;
import dalapo.factech.item.ItemHoverScooter;
import dalapo.factech.item.ItemMachinePart;
import dalapo.factech.item.ItemModArmor;
import dalapo.factech.item.ItemPressureGun;
import dalapo.factech.item.ItemRedWatcher;
import dalapo.factech.item.ItemWrench;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.PartList;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistry {
	public static List<Item> items = new ArrayList<Item>();
	
	public static ItemBase handbook;
	public static ItemBase wrench;
	public static ItemMachinePart machinePart;
	public static ItemBase salvagePart;
	public static ItemBase circuitIntermediate;
	public static ItemMagnifyingGlass magnifyingGlass;
	public static ItemBase minedOre;
	public static ItemBase oreProduct;
	public static ItemBase ingot;
	public static ItemBase tank;
	public static ItemBase intermediate;
	public static ItemBase craftStopper;
	public static ItemBase upgrade;
	public static ItemCoreStage coreUnfinished;
	public static ItemBase redWatcher;
	public static ItemBase pressureGun;
	public static ItemElementSword elementSword;
	public static ItemModArmor hardHat;
	public static ItemModArmor safetyVest;
	public static ItemModArmor workPants;
	public static ItemModArmor steelToeBoots;
	public static ItemBase handPump;
//	public static ItemHoverScooter hoverScooter;
	
	public static ItemArmor.ArmorMaterial safetyArmor;
	
	public static Item getItem(String name)
	{
		for (Item i : items)
		{
			if (i.getUnlocalizedName().equals(NameList.MODID + "." + name)) return i;
		}
		return null;
	}
	
	public static void init()
	{
		safetyArmor = EnumHelper.addArmorMaterial("SAFETY", NameList.MODID + ":safety", 15, new int[] {2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
		
		items.add(handbook = new ItemHandbook("handbook").setHasInformation());
		items.add(wrench = new ItemWrench("wrench"));
		items.add(machinePart = new ItemMachinePart("machinepart"));
		items.add(salvagePart = new ItemBase("salvage", 12));
		items.add(circuitIntermediate = new ItemBase("circuit_intermediate", 13));
		items.add(magnifyingGlass = new ItemMagnifyingGlass("fluidreader"));
		/**
		 * 0: Metallic Chunk
		 * 1: Sparkling Chunk
		 * 2: Crushed Metallic Chunks
		 * 3: Crushed Sparkling Chunks
		 * 4: Ferrous Mixture
		 */
		items.add(minedOre = (ItemBase)new ItemBase("mined_ore", 5));
		/**
		 * 0-3: Impure dusts (iron, gold, copper, nickel)
		 * 4-5: Nuggets (copper, nickel)
		 * 6-9: Pure dusts (iron, gold, copper, nickel)
		 * 10+: Misc.
		 */
		items.add(oreProduct = (ItemBase)new ItemBase("ore_dust", 15));
		items.add(ingot = (ItemBase)new ItemBase("ingot", 4));
		items.add(tank = (ItemBase)new ItemBase("tank", 8).setHasInformation());
		items.add(intermediate = (ItemBase)new ItemBase("intermediate", 5));
		items.add(craftStopper = (ItemBase)new ItemBase("craftstopper").setMaxStackSize(8));
		items.add(coreUnfinished = (ItemCoreStage)new ItemCoreStage("core_unfinished").setHasInformation());
		items.add(upgrade = new ItemBase("upgrade", 5));
		items.add(redWatcher = new ItemRedWatcher("redwatcher"));
		items.add(pressureGun = new ItemPressureGun("pressuregun").setHasInformation());
		items.add(elementSword = new ItemElementSword(ToolMaterial.IRON, "elementsword"));
		items.add(hardHat = (ItemModArmor) new ItemModArmor("hardhat", safetyArmor, EntityEquipmentSlot.HEAD).setCreativeTab(TabRegistry.FACTECH));
		items.add(safetyVest = (ItemModArmor) new ItemModArmor("safetyvest", safetyArmor, EntityEquipmentSlot.CHEST).setCreativeTab(TabRegistry.FACTECH));
		items.add(workPants = (ItemModArmor) new ItemModArmor("workpants", safetyArmor, EntityEquipmentSlot.LEGS).setCreativeTab(TabRegistry.FACTECH));
		items.add(steelToeBoots = (ItemModArmor) new ItemModArmor("steeltoeboots", safetyArmor, EntityEquipmentSlot.FEET).setCreativeTab(TabRegistry.FACTECH));
		items.add(handPump = new ItemHandPump("handpump").setHasInformation());
//		items.add(hoverScooter = new ItemHoverScooter("hoverscooter"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		for (Item item : items)
		{
			if (item instanceof ItemModelProvider) ((ItemModelProvider)item).initModel();
		}
	}
}