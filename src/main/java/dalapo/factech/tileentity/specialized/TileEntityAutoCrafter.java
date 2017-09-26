package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dalapo.factech.gui.ContainerAutoCrafter;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.item.ItemMachinePart;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityAutoCrafter extends TileEntityMachine {

	private ItemStack result;
	
	public TileEntityAutoCrafter() {
		super("autocrafter", 9, 0, 1, RelativeSide.BOTTOM);
		setDisplayName("Autocrafter");
		if (result == null) result = ItemStack.EMPTY;
	}
	
	
	public void updateValues(InventoryCrafting matrix)
	{
		for (int i=0; i<matrix.getSizeInventory(); i++)
		{
			if (!getStackInSlot(i).getItem().equals(ItemRegistry.craftStopper)) matrix.setInventorySlotContents(i, getStackInSlot(i));
			else matrix.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		if (CraftingManager.findMatchingRecipe(matrix, world) != null) Logger.info(CraftingManager.findMatchingRecipe(matrix, world).getRegistryName());
		result = CraftingManager.findMatchingResult(matrix, world);
		/*
		for (IRecipe recipe : CraftingManager.getRecipeList())
		{
			if (recipe.matches(matrix, world))
			{
				Logger.info(result);
				result = recipe.getCraftingResult(matrix);
				return;
			}
		}
		*/
		//result = ItemStack.EMPTY;
	}
	
	public void updateValues()
	{
		InventoryCrafting matrix = new InventoryCrafting(new ContainerAutoCrafter(this, world), 3, 3);
		updateValues(matrix);
	}

	@Override
	protected void fillMachineParts()
	{
		// NO-OP
	}

	private boolean hasExcess()
	{
		for (int i=0; i<9; i++)
		{
			if (getStackInSlot(i).getItem().equals(ItemRegistry.craftStopper)) continue;
			if (getStackInSlot(i).getCount() == 1 && getStackInSlot(i).getMaxStackSize() > 1) return false;
		}
		return true;
	}
	
	// Distributes the ItemStack counts evenly.
	private void redistribute()
	{
		List<Pair<Integer, Integer>> totals = new ArrayList<>();
		List<ItemStack> items = new ArrayList<>();
		
		for (int i=0; i<9; i++)
		{
			int index = -1;
			int numGroups = 0;
			for (int j=0; j<items.size(); j++)
			{
				if (items.get(j).isItemEqual(getStackInSlot(i))) index = j;
			}
			if (index == -1)
			{
				totals.add(new Pair<Integer, Integer>(getStackInSlot(i).getCount(), 1));
				items.add(getStackInSlot(i));
			}
			else
			{
				totals.set(index, new Pair<Integer, Integer>(totals.get(index).a + getStackInSlot(i).getCount(), totals.get(index).b + 1));
			}
		}
		
		for (int i=0; i<9; i++)
		{
			boolean flag = true;
			for (int j=0; j<items.size() && flag; j++)
			{
				if (items.get(j).isItemEqual(getStackInSlot(i)))
				{
					int toGive = totals.get(j).a / totals.get(j).b;
					getStackInSlot(i).setCount(toGive);
					totals.get(j).a -= toGive;
					totals.get(j).b -= 1;
					flag = false;
				}
			}
		}
	}
	
	@Override
	protected boolean performAction() {
		updateValues();
		if (!world.isRemote && !result.isEmpty() && hasExcess())
		{
			if (doOutput(result))
			{
				for (int i=0; i<9; i++)
				{
					ItemStack current = getStackInSlot(i).copy();
					if (!getStackInSlot(i).getItem().equals(ItemRegistry.craftStopper)) decrStackSize(i, 1);
					if (getStackInSlot(i).isEmpty())
					{
						ItemStack result = current.getItem().getContainerItem(current);
						if (!result.isEmpty())
						{
							EntityItem ei = new EntityItem(world, getPos().getX() + 0.5, getPos().getY() + 1.5, getPos().getZ() + 0.5, result);
							world.spawnEntity(ei);
						}
					}
				}
			}
		}
		redistribute();
		markDirty();
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		Logger.info(String.format("isItemValidForSlot, item = %s, slot = %s", is, slot));
		return slot > 8 || !getStackInSlot(slot).isEmpty();
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		Logger.info(String.format("canInsertItem, item = %s, slot = %s, direction = %s", itemStackIn, index, direction));
		if (direction.equals(EnumFacing.DOWN)) return (index == 9 || index == 10) && itemStackIn.getItem() instanceof ItemMachinePart;
		else return (index < 9);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return (index >= 9);
	}
	
	@Override
	public int getOpTime() {
		return 40;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagCompound res = new NBTTagCompound();
		res = result.writeToNBT(res);
		nbt.setTag("result", res);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("result"))
		result.deserializeNBT(nbt.getCompoundTag("result"));
	}
}