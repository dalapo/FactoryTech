package dalapo.factech.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.List;

import dalapo.factech.FactoryTech;
import dalapo.factech.tileentity.*;
import dalapo.factech.tileentity.automation.*;
import dalapo.factech.tileentity.specialized.*;

public class FacTileHelper {
	
	private FacTileHelper() {}
	
	// Surely there's a better way to do this?
	public static TileEntity getTileFromID(String id)
	{
		switch (id)
		{
		case "potionmixer":
			return new TileEntityPotionMixer();
		case "saw":
			return new TileEntitySaw();
		case "disruptor":
			return new TileEntityDisruptor();
		case "stackmover":
			return new TileEntityStackMover();
		case "filtermover":
			return new TileEntityFilterMover();
		case "bulkmover":
			return new TileEntityBulkMover();
		case "autopuller":
			return new TileEntityAutoPuller();
		case "htfurnace":
			return new TileEntityHTFurnace();
		case "oredrill":
			return new TileEntityOreDrill();
		case "autocrafter":
			return new TileEntityAutoCrafter();
		case "pipe":
			return new TileEntityPipe(50, 200);
		case "crucible":
			return new TileEntityCrucible();
		case "fluidpuller":
			return new TileEntityFluidPuller();
		case "grindstone":
			return new TileEntityGrindstone();
		case "metalcutter":
			return new TileEntityMetalCutter();
		case "itempusher":
			return new TileEntityItemPusher();
		case "itemredis":
			return new TileEntityItemRedis();
		case "circuitscribe":
			return new TileEntityCircuitScribe();
		case "centrifuge":
			return new TileEntityCentrifuge();
		case "fluiddrill":
			return new TileEntityFluidDrill();
		case "agitator":
			return new TileEntityAgitator();
		case "sluice":
			return new TileEntitySluice();
		case "miner":
			return new TileEntityAutoMiner();
		case "electroplater":
			return new TileEntityElectroplater();
		case "stabilizer":
			return new TileEntityStabilizer();
		case "magnetblock":
			return new TileEntityMagnet();
		case "elevator":
			return new TileEntityLiftFan();
		case "magnetizer":
			return new TileEntityMagnetizer();
		case "charger":
			return new TileEntityCoreCharger();
		case "compressor":
			return new TileEntityCompressionChamber();
		case "spawner":
			return new TileEntitySpawner();
		case "disassembler":
			return new TileEntityDisassembler();
		case "decocoil":
			return new TileEntityDecoCoil();
		case "watercollector":
			return new TileEntityWaterCollector();
		case "crate":
			return new TileEntityCrate();
		case "energizer":
			return new TileEntityEnergizer();
		case "fridge":
			return new TileEntityRefrigerator();
		case "propfurnace":
			return new TileEntityPropaneFurnace();
		case "woodcutter":
			return new TileEntityWoodcutter();
		case "teslacoil":
			return new TileEntityTeslaCoil();
		case "iondisperser":
			return new TileEntityIonDisperser();
		case "sequenceplacer":
			return new TileEntitySequencePlacer();
		case "tankblock":
			return new TileEntityTank();
		case "temperer":
			return new TileEntityTemperer();
		case "blockbreaker":
			return new TileEntityBlockBreaker();
		case "deepdrill":
			return new TileEntityDeepDrill();
		case "planter":
			return new TileEntityPlanter();
		case "buffercrate":
			return new TileEntityBufferCrate();
		case "inventorysensor":
			return new TileEntityInventorySensor();
		case "interceptor":
			return new TileEntityItemInterceptor();
		case "aerolyzer":
			return new TileEntityAerolyzer();
		case "magcent":
			return new TileEntityMagnetCentrifuge();
		case "planeshifter":
			return new TileEntityPlaneShifter();
		case "realelevator":
			return new TileEntityElevator();
		default:
			return null;
		}
	}
	
	public static Pair<EnumFacing, TileEntity> getFirstAdjacentTile(BlockPos pos, World world, Capability capability)
	{
		for (int i=0; i<6; i++)
		{
			BlockPos offset = FacMathHelper.withOffset(pos, EnumFacing.getFront(i));
			TileEntity te = world.getTileEntity(offset);
			if (te != null && (capability == null || te.hasCapability(capability, EnumFacing.getFront(i).getOpposite()))) return new Pair<EnumFacing, TileEntity>(EnumFacing.getFront(i), te);
		}
		return null;
	}
	
	public static Pair<EnumFacing, TileEntity> getFirstAdjacentTile(BlockPos pos, World world, Class<? extends TileEntity> type)
	{
		for (int i=0; i<6; i++)
		{
			BlockPos offset = FacMathHelper.withOffset(pos, EnumFacing.getFront(i));
			TileEntity te = world.getTileEntity(offset);
			if (te != null && type.isAssignableFrom(te.getClass()))
			{
				return new Pair<EnumFacing, TileEntity>(EnumFacing.getFront(i), te);
			}
		}
		return null;
	}
	
	public static boolean isValidSlotForSide(IItemHandler inv, EnumFacing side, int slot, boolean extract)
	{
		return isValidSlotForSide(inv, side.ordinal(), slot, extract);
	}
	
	public static boolean isValidSlotForSide(IItemHandler inv, int side, int slot, boolean extract)
	{
		return !inv.extractItem(slot, 1, true).isEmpty();
	}
	
	/**
	 * Note: This method is currently flawed as it does not account for the ability to spread an input stack over several slots.
	 */
	public static boolean hasSpaceForItem(IItemHandler inv, ItemStack is, int side, boolean def)
	{
		if (inv == null) return def;
		for (int i=0; i<inv.getSlots(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty() || (is.isItemEqual(stack) && stack.getCount() < FacMathHelper.getMin(stack.getMaxStackSize(), inv.getSlotLimit(i)))) return true;
		}
		return false;
	}
	
	public static boolean hasSpaceForItem(IItemHandler inv, ItemStack is, EnumFacing side, boolean def)
	{
		return hasSpaceForItem(inv, is, side.ordinal(), def);
	}
	
	public static ItemStack getFirstItem(IItemHandler inv, int side, boolean extract)
	{
		for (int i=0; i<inv.getSlots(); i++)
		{
			if (isValidSlotForSide(inv, side, i, extract) && inv.getStackInSlot(i) != null)
			{
				return inv.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	
	public static ItemStack tryInsertItem(IItemHandler inv, ItemStack itemstack, int side)
	{
		if (itemstack.isEmpty()) return ItemStack.EMPTY;
		if (inv == null) return itemstack;
		for (int i=0; i<inv.getSlots(); i++)
		{
			itemstack = inv.insertItem(i, itemstack, false);
			if (itemstack.isEmpty()) break;
		}
		return itemstack;
	}
	
	/**
	 * Returns the number of a certain item in an inventory. Pass 32767 to dmg to ignore item damage.
	 * @return
	 */
	public static int countItems(IItemHandler inventory, Item item, int dmg)
	{
		int acc = 0;
		for (int i=0; i<inventory.getSlots(); i++)
		{
			ItemStack is = inventory.getStackInSlot(i);
			if (is.getItem() == item && (dmg == 32767 || is.getItemDamage() == dmg))
			{
				acc += is.getCount();
			}
		}
		return acc;
	}
}