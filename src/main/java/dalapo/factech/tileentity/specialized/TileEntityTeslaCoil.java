package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.reference.AABBList;
import dalapo.factech.reference.DamageSourceList;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityTeslaCoil extends TileEntityAreaMachine {

	Random random;
	public TileEntityTeslaCoil() {
		super("teslacoil", 0, 2, 0, RelativeSide.ANY, 6);
		setDisplayName("Tesla Coil");
		random = new Random();
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.BATTERY, 0.3F, 1.05F);
		partsNeeded[1] = new MachinePart(PartList.WIRE, 0.2F, 1.1F);
	}

	@Override
	protected boolean performAction() {
		AxisAlignedBB range = AABBList.getCube(pos, getAdjustedRange());
		List<EntityLiving> targets = world.getEntitiesWithinAABB(EntityLiving.class, range);
		EntityLiving toZap = targets.get(random.nextInt(targets.size()));
		boolean hasChain = true;
		
		// If the target is wearing full chainmail armour, it acts like a Faraday cage and nullifies the damage
		for (ItemStack is : toZap.getArmorInventoryList())
		{
			if (!is.getItem().equals(Items.CHAINMAIL_HELMET) ||
					!is.getItem().equals(Items.CHAINMAIL_CHESTPLATE) ||
					!is.getItem().equals(Items.CHAINMAIL_LEGGINGS) ||
					!is.getItem().equals(Items.CHAINMAIL_BOOTS))
			{
				hasChain = false;
			}
		}
		if (!hasChain) toZap.attackEntityFrom(DamageSourceList.tesla, (int)(toZap.getMaxHealth() / 2.5));
		return true;
	}

	@Override
	public int getOpTime() {
		return 30;
	}

}
