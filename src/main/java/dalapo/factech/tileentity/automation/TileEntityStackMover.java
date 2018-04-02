package dalapo.factech.tileentity.automation;

import dalapo.factech.reference.StateList;
import dalapo.factech.block.BlockStackMover;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBase;
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
 * @author dalapo
 */
public class TileEntityStackMover extends TileEntityBase implements ActionOnRedstone {
//	ItemStack[] filter;
	private boolean isPowered = false;
	public static final String id = "StackMover";
	public TileEntityStackMover()
	{
		super();
	}
	
	public void onRedstoneSignal(boolean isSignal) // Called when a block adjacent to the TE updates.
	{
		if (world.isBlockPowered(pos))
		{
			if (!isPowered && isSignal)
			{
				transferStack();
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
	
	private int getSlotToExtract(IItemHandler te, IItemHandler dest, int side)
	{
		if (te == null) return -1;
		for (int i=0; i<te.getSlots(); i++)
		{
			if (te.getStackInSlot(i).isEmpty()) continue;
			if (FacTileHelper.isValidSlotForSide(te, side, i, true) && !te.getStackInSlot(i).isEmpty())
			{
				ItemStack wouldMove = te.getStackInSlot(i);
				if (FacTileHelper.hasSpaceForItem(dest, wouldMove, EnumFacing.getFront(side).getOpposite().ordinal(), true)) return i;
			}
		}
		return -1;
	}
	
	private void transferStack()
	{
		EnumFacing front = world.getBlockState(getPos()).getValue(StateList.directions);
		TileEntity pullLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front));
		TileEntity pushLoc = world.getTileEntity(FacMathHelper.withOffset(getPos(), front.getOpposite()));
		IItemHandler pull = pullLoc == null ? null : pullLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front.getOpposite());
		IItemHandler push = pushLoc == null ? null : pushLoc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front);
		if (pull == null) return; // Nowhere to pull from
		int slot = getSlotToExtract(pull, push, front.getOpposite().ordinal());
		if (slot == -1) return; // Nothing to pull
		
		// Condition for reaching here: The TE has found an ItemStack to move
		// The slot of the ItemStack is stored in pair[1]
		ItemStack toMove = pull.extractItem(slot, 64, true);
		if (push == null)
		{
			BlockPos pos = FacMathHelper.withOffset(getPos(), front.getOpposite());
			pull.extractItem(slot, 64, false);
			EntityItem drop = new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, toMove);
			drop.motionX = 0;
			drop.motionY = 0;
			drop.motionZ = 0;
			world.spawnEntity(drop);
		}
		else
		{
			int initCount = toMove.getCount();
			ItemStack remaining = FacTileHelper.tryInsertItem(push, toMove, front.ordinal());
			pull.extractItem(slot, initCount - remaining.getCount(), false);
		}
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
}