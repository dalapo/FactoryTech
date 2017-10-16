package dalapo.factech.tileentity.automation;

import dalapo.factech.reference.StateList;
import dalapo.factech.block.BlockStackMover;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.helper.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * NOTE: This is legacy code from 1.7.10, which is why it's a little derpy. It was written
 * before Capabilities and ItemStackHandlers existed, so it manipulates inventory slots
 * and ItemStacks directly. However, performance overhead should not be too bad since
 * it only runs upon receiving a redstone signal.
 * @author davidpowell
 */
public class TileEntityStackMover extends TileEntityBasicInventory implements ISidedInventory, ActionOnRedstone {
//	ItemStack[] filter;
	private boolean isPowered = false;
	public static final String id = "StackMover";
	private int filterSlot = 0;
	public TileEntityStackMover()
	{
		super("stackmover", 9);
	}
	
	public void onRedstoneSignal(boolean isSignal) // Called when a block adjacent to the TE updates.
	{
		if (world.isBlockPowered(pos))
		{
			if (!isPowered && isSignal)
			{
				if (((BlockStackMover)world.getBlockState(getPos()).getBlock()).tier == 2) transferStacks();
				else transferStack();
				isPowered = true;
			}
		}
		else if (!isSignal) isPowered = false;
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Filter");
	}
	
	private boolean shouldFilter()
	{
		for (int i=0; i<9; i++)
		{
			if (!getStackInSlot(i).isEmpty()) return true;
		}
		return false;
	}
	
	private int checkFilter(ItemStack is)
	{
		Logger.info(shouldFilter());
		if (!shouldFilter()) return 9;
		for (int i=0; i<9; i++)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).isItemEqual(is)) return i;
		}
		return -1;
	}
	
	private int[] getSlot(IItemHandler te, IItemHandler dest, int side, boolean insert)
	{
		// pair[0] = filter slot, pair[1] = inv slot
		if (te == null) return null;
		for (int i=0; i<te.getSlots(); i++)
		{
			if (!insert && te.getStackInSlot(i).isEmpty()) continue;
			int filterSlot = checkFilter(te.getStackInSlot(i));
			if (FacTileHelper.isValidSlotForSide(te, side, i, !insert) && !te.getStackInSlot(i).isEmpty() && filterSlot != -1)
			{
				ItemStack wouldMove = te.getStackInSlot(i);
				Logger.info(FacTileHelper.hasSpaceForItem(dest, wouldMove, EnumFacing.getFront(side).getOpposite().ordinal(), true));
				if (FacTileHelper.hasSpaceForItem(dest, wouldMove, EnumFacing.getFront(side).getOpposite().ordinal(), true)) return new int[] {filterSlot, i};
			}
		}
		return null;
	}
	
	private void transferStacks()
	{
		EnumFacing front = world.getBlockState(getPos()).getValue(StateList.directions);
		TileEntity pullLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front));
		TileEntity pushLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front.getOpposite()));
		IItemHandler pull = pullLoc == null ? null : pullLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front.getOpposite());
		IItemHandler push = pushLoc == null ? null : pushLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front);
		for (int i=0; i<getSizeInventory(); i++)
		{
			ItemStack is = getStackInSlot(i);
			if (!is.isEmpty())
			{
				for (int j=0; j<pull.getSlots(); j++)
				{
					ItemStack toMove = pull.extractItem(j, is.getCount(), true);
					if (toMove.isItemEqual(is))
					{
						toMove = pull.extractItem(j, is.getCount(), false);
						if (push != null && FacTileHelper.hasSpaceForItem(push, toMove, front, true))
						{
							ItemStack remaining = FacTileHelper.tryInsertItem(push, toMove, front.getOpposite().ordinal());
							pull.insertItem(j, remaining, false);
							break;
						}
						else if (push == null)
						{
							BlockPos pos = FacMathHelper.withOffset(getPos(), front.getOpposite());
							EntityItem drop = new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, toMove);
							drop.motionX = 0;
							drop.motionY = 0;
							drop.motionZ = 0;
							world.spawnEntity(drop);
							break;
						}
					}
				}
			}
		}
	}
	private void transferStack()
	{
		EnumFacing front = world.getBlockState(getPos()).getValue(StateList.directions);
		TileEntity pullLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front));
		TileEntity pushLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front.getOpposite()));
		IItemHandler pull = pullLoc == null ? null : pullLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front.getOpposite());
		IItemHandler push = pushLoc == null ? null : pushLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front);
		if (pull == null) return; // Nowhere to pull from
		int[] pair = getSlot(pull, push, front.getOpposite().ordinal(), false);
		if (pair == null) return; // Nothing to pull
		
		// Condition for reaching here: The TE has found an ItemStack to move
		// The slot of the ItemStack is stored in pair[1]
		int moveSize = pair[0] == 9 ? 64 : getStackInSlot(pair[0]).getCount();
		ItemStack toMove = pull.extractItem(pair[1], moveSize, true);
		Logger.info(pair[1]);
		Logger.info(toMove);
		if (push == null)
		{
			BlockPos pos = FacMathHelper.withOffset(getPos(), front.getOpposite());
			EntityItem drop = new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, toMove);
			drop.motionX = 0;
			drop.motionY = 0;
			drop.motionZ = 0;
			world.spawnEntity(drop);
		}
		else
		{
			int initCount = toMove.getCount();
			ItemStack remaining = FacTileHelper.tryInsertItem((IItemHandler)push, toMove, front.ordinal());
			pull.extractItem(pair[1], initCount - remaining.getCount(), false);
//			((IItemHandler)pull).insertItem(pair[1], remaining, false);
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
	}

	// Returns an empty array
	// The Stack Mover's inventory (filter) does not interact with automation in any way
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public String getName() {
		return "Filter";
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
}