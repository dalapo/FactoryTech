package dalapo.factech.tileentity.specialized;

import java.util.List;

import dalapo.factech.auxiliary.PotionLockdown;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.AABBList;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachineNoOutput;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityDisruptor extends TileEntityAreaMachine {
	
	public TileEntityDisruptor() {
		super("disruptor", 0, 4, 0, RelativeSide.ANY, 8);
		setDisplayName("Mob Disruptor");
		isDisabledByRedstone = true;
	}
	
	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.CORE, 0.1F, 1.2F, 0.8F*kValue[0][1], (int)(6*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.MAGNET, new ItemStack(Items.IRON_INGOT), 0.2F, 1.25F, 1F, (int)(8*kValue[1][0]));
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_3, 0.2F, 1.25F, 0.6F*kValue[2][1], (int)(4*kValue[2][0]));
		partsNeeded[3] = new MachinePart(PartList.PISTON, 0.15F, 1.3F, 0.8F*kValue[3][1], (int)(6*kValue[3][0]));
	}
	
	@Override
	protected boolean performAction() {
		AxisAlignedBB aabb = AABBList.getCube(pos, getAdjustedRange());
		List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, aabb);
		for (EntityMob entity : entities)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 10));
			entity.addPotionEffect(new PotionEffect(PotionLockdown.INSTANCE, 200, 1));
		}
		return true;
	}

	@Override
	public int getOpTime() {
		return 100;
	}
}
