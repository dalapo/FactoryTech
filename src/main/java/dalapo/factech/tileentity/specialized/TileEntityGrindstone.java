package dalapo.factech.tileentity.specialized;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import dalapo.factech.FacTechConfigManager;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicProcessor;

public class TileEntityGrindstone extends TileEntityBasicProcessor {

	public TileEntityGrindstone() {
		super("grindstone", 1, RelativeSide.BACK);
		setDisplayName("Grindstone");
	}
	
	static {
		useOreDict = true;
	}

	@Override
	protected Map<ItemStack, ItemStack> getRecipeList() {
		// TODO Auto-generated method stub
		return MachineRecipes.GRINDSTONE;
	}
	
	@Override
	public void getHasWork()
	{
		super.getHasWork();
		ItemStack input = getInput(0);
		if (input.getItem() instanceof ItemSword || input.getItem() instanceof ItemTool) hasWork = true;
	}
	
	private boolean processEquipment(int enchID)
	{
		ItemStack toolOut = getInput(0).copy();
		NBTTagList enchantments = toolOut.getEnchantmentTagList();
		toolOut.setTagInfo("ench", enchantments);
		for (int i=0; i<enchantments.tagCount(); i++)
		{
			NBTTagCompound ench = enchantments.getCompoundTagAt(i);
			if (ench.getInteger("id") == enchID)
			{
				if (ench.getInteger("lvl") < 3)
				{
					ench.setInteger("lvl", ench.getInteger("lvl") + 1);
					toolOut.setItemDamage(toolOut.getMaxDamage() - (int)((FacStackHelper.getRemainingDurability(toolOut) * 0.75)));
					setOutput(toolOut);
					getInput(0).shrink(1);
					return true;
				}
				else return false; // Do nothing if it already has Sharpness III or above
			}
		}
		NBTTagCompound ench = new NBTTagCompound();
		ench.setInteger("id", enchID);
		ench.setInteger("lvl", 1);
		toolOut.getEnchantmentTagList().appendTag(ench);
		toolOut.setItemDamage(toolOut.getMaxDamage() - (int)((FacStackHelper.getRemainingDurability(toolOut) * 0.75)));
		setOutput(toolOut);
		getInput(0).shrink(1);
		return true;
	}
	@Override
	public boolean performAction()
	{
		if (FacTechConfigManager.allowMachineEnchanting && (getInput(0).getItem()) instanceof ItemSword)
		{
			return processEquipment(16);
		}
		else if (FacTechConfigManager.allowMachineEnchanting && (getInput(0)).getItem() instanceof ItemTool)
		{
			return processEquipment(32);
		}
		else
		{
			return super.performAction();
		}
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.GEAR, 0.1F, 1.1F, 0.85F, 10);
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 80;
	}

}
