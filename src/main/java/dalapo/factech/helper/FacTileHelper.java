package dalapo.factech.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
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

import dalapo.factech.tileentity.*;
import dalapo.factech.tileentity.automation.TileEntityAutoPuller;
import dalapo.factech.tileentity.automation.TileEntityCrate;
import dalapo.factech.tileentity.automation.TileEntityFluidPuller;
import dalapo.factech.tileentity.automation.TileEntityItemPusher;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import dalapo.factech.tileentity.automation.TileEntityLiftFan;
import dalapo.factech.tileentity.automation.TileEntityMechArm;
import dalapo.factech.tileentity.automation.TileEntityPipe;
import dalapo.factech.tileentity.automation.TileEntitySequencePlacer;
import dalapo.factech.tileentity.automation.TileEntityStackMover;
import dalapo.factech.tileentity.automation.TileEntityTank;
import dalapo.factech.tileentity.automation.TileEntityWaterCollector;
import dalapo.factech.tileentity.specialized.TileEntityAgitator;
import dalapo.factech.tileentity.specialized.TileEntityAutoCrafter;
import dalapo.factech.tileentity.specialized.TileEntityAutoMiner;
import dalapo.factech.tileentity.specialized.TileEntityCentrifuge;
import dalapo.factech.tileentity.specialized.TileEntityCircuitScribe;
import dalapo.factech.tileentity.specialized.TileEntityCompressionChamber;
import dalapo.factech.tileentity.specialized.TileEntityCoreCharger;
import dalapo.factech.tileentity.specialized.TileEntityCrucible;
import dalapo.factech.tileentity.specialized.TileEntityDecoCoil;
import dalapo.factech.tileentity.specialized.TileEntityDisassembler;
import dalapo.factech.tileentity.specialized.TileEntityDisruptor;
import dalapo.factech.tileentity.specialized.TileEntityElectroplater;
import dalapo.factech.tileentity.specialized.TileEntityEnergizer;
import dalapo.factech.tileentity.specialized.TileEntityFluidDrill;
import dalapo.factech.tileentity.specialized.TileEntityGrindstone;
import dalapo.factech.tileentity.specialized.TileEntityHTFurnace;
import dalapo.factech.tileentity.specialized.TileEntityIonDisperser;
import dalapo.factech.tileentity.specialized.TileEntityMagnet;
import dalapo.factech.tileentity.specialized.TileEntityMagnetizer;
import dalapo.factech.tileentity.specialized.TileEntityMetalCutter;
import dalapo.factech.tileentity.specialized.TileEntityOreDrill;
import dalapo.factech.tileentity.specialized.TileEntityPotionMixer;
import dalapo.factech.tileentity.specialized.TileEntityPropaneFurnace;
import dalapo.factech.tileentity.specialized.TileEntityRefrigerator;
import dalapo.factech.tileentity.specialized.TileEntitySaw;
import dalapo.factech.tileentity.specialized.TileEntitySluice;
import dalapo.factech.tileentity.specialized.TileEntitySpawner;
import dalapo.factech.tileentity.specialized.TileEntityStabilizer;
import dalapo.factech.tileentity.specialized.TileEntityTemperer;
import dalapo.factech.tileentity.specialized.TileEntityTeslaCoil;
import dalapo.factech.tileentity.specialized.TileEntityWoodcutter;

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
		case "mecharm":
			return new TileEntityMechArm();
		case "sequenceplacer":
			return new TileEntitySequencePlacer();
		case "tankblock":
			return new TileEntityTank();
		case "temperer":
			return new TileEntityTemperer();
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
		if (!(inv instanceof SidedInvWrapper)) return true;
//		ItemStack stack = inv.getStackInSlot(slot);
		if (extract)
		{
			
		}
		return false;
	}
	public static boolean isValidSlotForSide(IItemHandler inv, int side, int slot, boolean extract)
	{
		return !inv.extractItem(slot, 1, true).isEmpty();
	}
	
	public static boolean hasSpaceForItem(IItemHandler inv, ItemStack is, int side, boolean def)
	{
		if (inv == null) return def;
		for (int i=0; i<inv.getSlots(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty() || (is.isItemEqual(stack) && stack.getCount() < stack.getMaxStackSize())) return true;
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
		if (itemstack == null) return null;
		if (inv == null) return itemstack;
		Logger.info(inv.getSlots());
		for (int i=0; i<inv.getSlots(); i++)
		{
			Logger.info(i);
			itemstack = inv.insertItem(i, itemstack, false);
			Logger.info(itemstack);
		}
		return itemstack;
	}
}