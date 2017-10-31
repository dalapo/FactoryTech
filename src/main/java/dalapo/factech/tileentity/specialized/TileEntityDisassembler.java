package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import dalapo.factech.FacTechConfigManager;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.TileEntityMachineNoOutput;

public class TileEntityDisassembler extends TileEntityMachineNoOutput {

	public TileEntityDisassembler() {
		super("disassembler", 0, 3, RelativeSide.BOTTOM);
		setDisplayName("Mob Disassembler");
		isDisabledByRedstone = true;
	}
	// Zombie: 5 Rotten Flesh, 2 Bones, 1 Zombie Head, 1 Monster Essence
	// Skeleton: 5 Bones, 5 Arrows, 1 Skeleton Skull, 1 Monster Essence
	// Spider: 4 String, 2 Spider Eyes, 1 Leather
	// Creeper: 5 Gunpowder, 1 Creeper Spore (?)
	// Zombie Pigman: 5 Rotten Flesh, 1 Gold Sword, 3 Gold Nuggets, 2 Cooked Porkchops
	// Enderman: 3 Ender Pearls, 1 Obsidian

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.SAW, 0.2F, 1.1F, 0.4F*kValue[0][1], (int)(6*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.BATTERY, 0.25F, 1.01F, 0.9F*kValue[1][1], (int)(5*kValue[1][0]));
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_2, 0.1F, 1.2F, 0.6F*kValue[2][1], (int)(5*kValue[2][0]));
	}

	private List<ItemStack> getSpecialDrops(Class<? extends EntityLivingBase> mobType)
	{
		return MachineRecipes.DISASSEMBLER.get(mobType);
	}
	
	@Override
	protected boolean performAction() {
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(FacMathHelper.withOffset(getPos(), EnumFacing.UP)));
		if (!entities.isEmpty())
		{
			EntityLivingBase target = entities.get(0);
			List<ItemStack> drops = getSpecialDrops(target.getClass());
			if (target instanceof EntityPlayer && !FacTechConfigManager.disassemblePlayers) return false; 
			target.attackEntityFrom(new DamageSource("machine"), 10);
			if (target.getHealth() == 0 && drops != null)
			{
//				Logger.info("Target is dead. Drops: " + drops);
				for (ItemStack is : drops)
				{
//					Logger.info("Spawning item " + is);
					EntityItem ei = new EntityItem(world, target.posX, target.posY, target.posZ, is.copy());
					world.spawnEntity(ei);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 40;
	}
}
