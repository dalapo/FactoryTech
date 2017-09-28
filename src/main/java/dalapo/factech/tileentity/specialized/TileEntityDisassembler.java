package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
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
	}
	// Zombie: 5 Rotten Flesh, 2 Bones, 1 Zombie Head, 1 Monster Essence
	// Skeleton: 5 Bones, 5 Arrows, 1 Skeleton Skull, 1 Monster Essence
	// Spider: 4 String, 2 Spider Eyes, 1 Leather
	// Creeper: 5 Gunpowder, 1 Creeper Spore (?)
	// Zombie Pigman: 5 Rotten Flesh, 1 Gold Sword, 3 Gold Nuggets, 2 Cooked Porkchops
	// Enderman: 3 Ender Pearls, 1 Obsidian

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.SAW, 0.2F, 1.1F, 0.4F, 6);
		partsNeeded[1] = new MachinePart(PartList.BATTERY, 0.25F, 1.01F, 0.9F, 4);
		partsNeeded[2] = new MachinePart(PartList.CIRCUIT_2, 0.1F, 1.2F, 0.6F, 5);
	}

	private List<ItemStack> getSpecialDrops(Class<? extends EntityMob> mobType)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		// Trigger warning: Terrible code
		if (mobType.equals(EntityZombie.class))
		{
			drops.add(new ItemStack(Items.ROTTEN_FLESH, 5));
			drops.add(new ItemStack(Items.BONE, 2));
			drops.add(new ItemStack(Item.getItemById(397), 1, 2));
			drops.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		}
		else if (mobType.equals(EntitySkeleton.class))
		{
			drops.add(new ItemStack(Items.BONE, 6));
			drops.add(new ItemStack(Items.ARROW, 5));
			drops.add(new ItemStack(Item.getItemById(397), 1, 1));
			drops.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		}
		else if (mobType.equals(EntitySpider.class))
		{
			drops.add(new ItemStack(Items.STRING, 5));
			drops.add(new ItemStack(Items.SPIDER_EYE, 3));
			drops.add(new ItemStack(Items.LEATHER, 1));
			drops.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		}
		else if (mobType.equals(EntityCreeper.class))
		{
			drops.add(new ItemStack(Items.GUNPOWDER, 5));
			drops.add(new ItemStack(Item.getItemById(397), 1, 4));
			drops.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		}
		else if (mobType.equals(EntityPigZombie.class))
		{
			drops.add(new ItemStack(Items.ROTTEN_FLESH, 4));
			drops.add(new ItemStack(Items.GOLDEN_SWORD, 1));
			drops.add(new ItemStack(Items.GOLD_NUGGET, 4));
			drops.add(new ItemStack(Items.COOKED_PORKCHOP, 2));
		}
		else if (mobType.equals(EntityEnderman.class))
		{
			drops.add(new ItemStack(Items.ENDER_PEARL, 1));
			drops.add(new ItemStack(Blocks.OBSIDIAN, 1));
			drops.add(new ItemStack(ItemRegistry.intermediate, 1, 3));
		}
		
		return drops;
	}
	
	@Override
	protected boolean performAction() {
		List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(FacMathHelper.withOffset(getPos(), EnumFacing.UP)));
		if (!entities.isEmpty())
		{
			EntityMob target = entities.get(0);
			List<ItemStack> drops = getSpecialDrops(target.getClass());
			target.attackEntityFrom(new DamageSource("machine"), 10);
			if (target.getHealth() == 0)
			{
//				Logger.info("Target is dead. Drops: " + drops);
				for (ItemStack is : drops)
				{
//					Logger.info("Spawning item " + is);
					EntityItem ei = new EntityItem(world, target.posX, target.posY, target.posZ, is.copy());
					world.spawnEntity(ei);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 40;
	}
}
