package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.AABBList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityAerolyzer extends TileEntityAreaMachine
{
	List<PotionEffect> effects = new ArrayList<PotionEffect>();
	int tankAmount = 0;
	
	public TileEntityAerolyzer() {
		super("aerolyzer", 1, 2, 1, RelativeSide.BOTTOM, 8);
		setDisplayName("Aerolyzer");
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	@Override
	public void getHasWork()
	{
		hasWork = getInput().getItem() == Items.POTIONITEM || tankAmount > 0;
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
	}

	@Override
	protected boolean performAction()
	{
		if (tankAmount-- <= 0 && getInput().getItem().equals(Items.POTIONITEM))
		{
			effects = PotionUtils.getEffectsFromStack(getInput());
			if (effects.isEmpty()) return false;
			tankAmount += effects.get(0).getDuration() / 100;
			doOutput(new ItemStack(Items.GLASS_BOTTLE));
			getInput().shrink(1);
		}
		Logger.info(effects);
		List<EntityLivingBase> creatures = world.getEntitiesWithinAABB(EntityLivingBase.class, AABBList.getCube(pos, getAdjustedRange()));
		for (EntityLivingBase elb : creatures)
		{
			for (PotionEffect effect : effects)
			{
				PotionEffect modified = new PotionEffect(effect.getPotion(), 200, effect.getAmplifier());
				elb.addPotionEffect(modified);
			}
		}
		return true;
	}

	@Override
	public int getOpTime() {
		return 150;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList effectsList = new NBTTagList();
		for (PotionEffect pe : effects)
		{
			effectsList.appendTag(new NBTTagInt(Potion.getIdFromPotion(pe.getPotion())));
		}
		nbt.setTag("effects", effectsList);
		nbt.setInteger("charges", tankAmount);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("effects"))
		{
			NBTTagList effectsList = nbt.getTagList("effects", 3);
			for (int i=0; i<effectsList.tagCount(); i++)
			{
				effects.add(new PotionEffect(Potion.getPotionById(effectsList.getIntAt(i)), 200));
			}
		}
		if (nbt.hasKey("charges"))
		{
			tankAmount = nbt.getInteger("charges");
		}
	}
}