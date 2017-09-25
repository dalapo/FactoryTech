package dalapo.factech.tileentity.automation;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBasicInventory;

// Copypasta from AutoUtils, edited to work in 1.12.2
// Known bug: Shulker Boxes are... glitchy
public class TileEntitySequencePlacer extends TileEntityBasicInventory implements ActionOnRedstone
{
	private int nextSlot;
	private boolean isPowered = false;
	
	public TileEntitySequencePlacer() {
		super("sequenceplacer", 18);
		setDisplayName("Block order");
		nextSlot = 0;
	}
	
	private void placeNextBlock()
	{
		EnumFacing front = world.getBlockState(pos).getValue(StateList.directions);
		if (!world.isAirBlock(FacMathHelper.withOffset(pos, front))) return;
		ItemStack itemstack = getStackInSlot(nextSlot).copy();
		boolean flag = false;
		for (int i=0; i<18; i++)
		{
			itemstack = getStackInSlot(nextSlot).copy();
			if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
			{
				flag = true;
				decrStackSize(nextSlot, 1);
			}
			nextSlot++;
			if (nextSlot > 17) nextSlot = 0;
			if (flag) break;
		}
		if (flag)
		{
			System.out.println(itemstack);
			Block block = ((ItemBlock)itemstack.getItem()).getBlock();
			world.setBlockState(FacMathHelper.withOffset(pos, front), block.getStateFromMeta(itemstack.getItemDamage()));
		}
	}

	@Override
	public void onRedstoneSignal(boolean signal) {
		if (world.isBlockPowered(getPos()))
		{
			if (!isPowered)
			{
				placeNextBlock();
				isPowered = true;
			}
		}
		else isPowered = false;
		
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemBlock;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("nextSlot", nextSlot);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		nextSlot = nbt.getInteger("nextSlot");
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendInfoPacket(EntityPlayer ep) {
		// TODO Auto-generated method stub
		
	}
}