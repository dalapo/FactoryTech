package dalapo.factech.tileentity.specialized;

import static dalapo.factech.FactoryTech.random;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityTemperer extends TileEntityMachine
{
	private static final int deltaT = 5;
	
	// Can test for positive or negative Redstone edge if hasRedstone != hadRedstone
	private boolean hasRedstone = false;
	private boolean hadRedstone = false;
	
	private int activeRecipe = -1;
	private int activeTime = 0;
	
	public TileEntityTemperer()
	{
		super("temperer", 1, 3, 1, RelativeSide.BACK);
	}

	@Override
	public void getHasWork()
	{
		ItemStack input = getInput();
		
		if (FacTechConfigManager.allowMachineEnchanting)
		{
			if (input.getItem() == Items.IRON_SWORD || (input.getItem() instanceof ItemTool && ((ItemTool)input.getItem()).getToolMaterialName() == "IRON"))
			{
				activeRecipe = -2;
				activeTime = 83;
				return;
			}
			if (input.getItem() instanceof ItemArmor && ((ItemArmor)input.getItem()).getArmorMaterial() == ArmorMaterial.IRON)
			{
				activeRecipe = -2;
				activeTime = 116;
				return;
			}
		}
		
		for (int i=0; i<MachineRecipes.TEMPERER.size(); i++)
		{
			if (FacStackHelper.matchStacksWithWildcard(input, MachineRecipes.TEMPERER.get(i).input))
			{
				activeRecipe = i;
				activeTime = MachineRecipes.TEMPERER.get(i).time;
				return;
			}
		}
		activeRecipe = -1;
	}
	
	public int getActiveTime()
	{
		return activeTime;
	}
	
	public int getProgressScaled(int pixelSize)
	{
		return (int)(((double)age / 120) * pixelSize);
	}
	
	public boolean successfulWindow()
	{
		return age >= activeTime - deltaT && age <= activeTime + deltaT;
	}
	
	/**
	 * DO NOT MODIFY THE ITEMSTACK RETURNED BY THIS METHOD.
	 * SERIOUSLY.
	 * @return
	 */
	public ItemStack getRecipeOutput()
	{
		if (activeRecipe == -1) return ItemStack.EMPTY;
		if (activeRecipe == -2) return getInput();
		return MachineRecipes.TEMPERER.get(activeRecipe).output;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && (hasRedstone || hadRedstone) && activeRecipe != -1;
	}
	
	@Override
	public void update()
	{
		hasRedstone = world.isBlockIndirectlyGettingPowered(pos) > 0; // First line of update()
		if (canRun())
		{
			isRunning = true;
			
			// Temperer has run for too long - destroy input without outputting anything
			if (age++ > activeTime + deltaT)
			{
				if (!world.isRemote)
				{
					world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
				}
				else
				{
					for (int i=0; i<8; i++)
					{
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()+random.nextFloat(), pos.getY()+random.nextFloat()+0.5, pos.getZ()+random.nextFloat(), 0, 0, 0);
					}
				}
				getInput(0).shrink(1);
				age = 0;
			}
			
			if (hadRedstone && !hasRedstone)
			{
				// Only complete the process if the machine has run for the right amount of time
				if (age > activeTime - deltaT && !world.isRemote)
				{
//					Logger.info("Successful tempering");
					if (performAction()) consumeParts();
					getHasWork();
					markDirty();
					syncToAll();
				}
				age = 0;
			}
		}
		else
		{
			age = 0;
			isRunning = false;
		}
		replenishParts();
		
		hadRedstone = hasRedstone; // Last line of update()
	}
	
	@Override
	protected boolean performAction() {
		if (activeRecipe == -2)
		{
			return processEquipment();
		}
		else if (activeRecipe != -1)
		{
			ItemStack out = MachineRecipes.TEMPERER.get(activeRecipe).output;
			if (doOutput(out))
			{
				getInput().shrink(1);
				return true;
			}
		}
		return false;
	}

	private boolean processEquipment()
	{
		ItemStack toolOut = getInput(0).copy();
		NBTTagList enchantments = toolOut.getEnchantmentTagList();
		toolOut.setTagInfo("ench", enchantments);
		for (int i=0; i<enchantments.tagCount(); i++)
		{
			NBTTagCompound ench = enchantments.getCompoundTagAt(i);
			if (ench.getInteger("id") == 34)
			{
				return false; // Do nothing if it already has Unbreaking I or above
			}
		}
		NBTTagCompound ench = new NBTTagCompound();
		ench.setInteger("id", 34);
		ench.setInteger("lvl", 1);
		toolOut.getEnchantmentTagList().appendTag(ench);
		setOutput(toolOut);
		getInput(0).shrink(1);
		return true;
	}

	// Irrelevant for this TE but helps for readability?
	@Override
	public int getOpTime() {
		return activeTime;
	}
	
	public static class TempererRecipe
	{
		final ItemStack input;
		final ItemStack output;
		final int time;
		
		public TempererRecipe(ItemStack in, ItemStack out, int t)
		{
			input = in;
			output = out;
			if (t < 5) time = 5; else time = t;
		}
		
		public ItemStack getInput()
		{
			return input;
		}
		
		public ItemStack getOutput()
		{
			return output;
		}
		
		public int getTime()
		{
			return time;
		}
	}
}