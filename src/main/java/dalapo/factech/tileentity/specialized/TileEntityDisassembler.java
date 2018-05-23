package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.config.FacTechConfigManager;
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
	
	// obfuscation is stupid
	private String getMobName(Class<? extends EntityLivingBase> c)
	{
		if (c == EntityZombie.class) return "EntityZombie";
		if (c == EntitySkeleton.class) return "EntitySkeleton";
		if (c == EntitySpider.class) return "EntitySpider";
		if (c == EntityCaveSpider.class) return "EntityCaveSpider";
		if (c == EntitySlime.class) return "EntitySlime";
		if (c == EntityCreeper.class) return "EntityCreeper";
		if (c == EntityPigZombie.class) return "EntityPigZombie";
		if (c == EntityEnderman.class) return "EntityEnderman";
		if (c == EntityBlaze.class) return "EntityBlaze";
		
		return c.getName();
	}
	// Zombie: 5 Rotten Flesh, 2 Bones, 1 Zombie Head, 1 Monster Essence
	// Skeleton: 5 Bones, 5 Arrows, 1 Skeleton Skull, 1 Monster Essence
	// Spider: 4 String, 2 Spider Eyes, 1 Leather
	// Creeper: 5 Gunpowder, 1 Creeper Spore (?)
	// Zombie Pigman: 5 Rotten Flesh, 1 Gold Sword, 3 Gold Nuggets, 2 Cooked Porkchops
	// Enderman: 3 Ender Pearls, 1 Obsidian
	
	private List<ItemStack> getSpecialDrops(String mobType)
	{
		return MachineRecipes.DISASSEMBLER.get(mobType);
	}
	
	@Override
	protected boolean performAction() {
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(FacMathHelper.withOffset(getPos(), EnumFacing.UP)));
		if (!entities.isEmpty())
		{
			EntityLivingBase target = entities.get(0);
			List<ItemStack> drops = getSpecialDrops(getMobName(target.getClass()));
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
		return 30;
	}
}
